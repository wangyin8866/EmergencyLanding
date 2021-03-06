package com.zyjr.emergencylending.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.VersionBean;
import com.zyjr.emergencylending.model.VersionUpdateModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 检测安装更新文件的助手类
 *
 * @author wyman
 */
public class UpdateVersionService {
    private static final int DOWN = 1;// 用于区分正在下载
    private static final int DOWN_FINISH = 0;// 用于区分下载完成
    private String fileSavePath;// 下载新apk的储存地点
    private String updateVersionXMLPath;// 检测更新的xml文件
    private String urlApk;// 下载APk的地址
    private String filName = "jjt";// apK名称
    private int progress;// 获取新apk的下载数据量,更新下载滚动条
    private boolean cancelUpdate = false;// 是否取消下载
    private Context context;
    private ProgressBar progressBar;
    private TextView tv_progress;
    private Dialog downLoadDialog;
    private String display;
    private String flag;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((Integer) msg.obj) {
                case DOWN:
                    BaseActivity.getForegroundActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress);
                            tv_progress.setText(progress + "%");
                        }
                    });
                    break;

                case DOWN_FINISH:
                    ToastAlone.showLongToast(context, "文件下载完成,正在安装更新").show();
                    installAPK();
                    break;

                default:
                    break;
            }
        }

    };

    /**
     * 构造方法
     *
     * @param updateVersionXMLPath 比较版本的xml文件地址(服务器上的)
     * @param context              上下文
     */
    UpdateVersionService(String updateVersionXMLPath, Context context) {
        super();
        this.updateVersionXMLPath = updateVersionXMLPath;
        this.context = context;
    }

    /**
     * 检测是否可更新
     *
     * @param isClick
     */
    void checkUpdate(boolean isClick) {
        update(isClick);
    }

    /**
     * 更新提示框
     */

    private void showUpdateVersionDialog() {
        final CustomerDialog dialog = new CustomerDialog(context);
        dialog.versionUpdate(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cancel:
                        dialog.dismiss();
                        Constants.update = false;
                        if ("0001".equals(flag)) {
                            ActivityCollector.finishAll();
                            System.exit(0);
                        }
                        break;
                    case R.id.update:
                        dialog.dismiss();
                        // 显示下载对话框
                        showDownloadDialog();
                        break;
                }
            }
        }, display, flag).show();
    }

    /**
     * 下载的提示框
     */
    private void showDownloadDialog() {
        // App下载对话框
        Builder builder = new Builder(context);
        builder.setTitle("正在更新！");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.downloaddialog, null);
        progressBar = (ProgressBar) v.findViewById(R.id.updateProgress);
        tv_progress = (TextView) v.findViewById(R.id.tv_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
                Constants.update = false;
                if ("0001".equals(flag)) {
                    ActivityCollector.finishAll();
                    System.exit(0);
                }
            }
        });
        downLoadDialog = builder.create();
        downLoadDialog.setCancelable(false);
        downLoadDialog.setCanceledOnTouchOutside(false);
        downLoadDialog.show();
        // 下载文件
        downloadApk();
    }


    /**
     * 下载apk,不能占用主线程.所以另开的线程
     */
    private void downloadApk() {
        new downloadApkThread().start();
    }

    /**
     * 判断是否可更新
     *
     * @param isClick
     */
    private void update(final boolean isClick) {
//        showUpdateVersionDialog();
        VersionUpdateModel.getInstance().update(updateVersionXMLPath).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VersionBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError", e.getMessage());
                        ToastAlone.showShortToast(context, Config.TIP_NET_ERROR);
                    }

                    @Override
                    public void onNext(VersionBean o) {
                        if (o.getFlag().equals(Config.CODE_SUCCESS)) {
                            urlApk = o.getResult().getUrl();
                            display = o.getResult().getContent();
                            flag = o.getResult().getFlag();
                            if ("0001".equals(flag) || "0012".equals(flag)) {
                                showUpdateVersionDialog();
                            } else {
                                if (isClick) {
                                    ToastAlone.showShortToast(context, "已经是最新版本!");
                                }
                            }
                        } else {
                            ToastAlone.showShortToast(context, o.getPromptMsg());
                        }

//
//                        // 创建一个新的字符串
//                        StringReader read = new StringReader(o);
//                        // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
//                        InputSource source = new InputSource(read);
//                        SAXReader reader = new SAXReader();
//                        try {
//                            Document document = reader.read(source);
//                            Element root = document.getRootElement();
//                            List<Element> childElements = root.elements();
//                            serverCode = Integer.valueOf(childElements.get(0).getText()
//                                    .trim());
//                            display = childElements.get(1).getText().trim();
//                            urlApk = childElements.get(2).getText().trim();
//                            isMust = Integer.valueOf(childElements.get(3).getText().trim());
//                            if (serverCode > versionCode) {
//                                showUpdateVersionDialog();
//                            }
//                        } catch (DocumentException e) {
//                            e.printStackTrace();
//                        }
                    }
                });
    }

    /**
     * 获取当前版本和服务器版本.如果服务器版本高于本地安装的版本.就更新
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(
                    "com.zyjr.emergencylending", 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;

    }

    /**
     * 安装apk文件
     */
    private void installAPK() {
        File apkfile = new File(fileSavePath, filName + ".apk");
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果是安卓7.0以上
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri uri = FileProvider.getUriForFile(context, "com.zyjr.emergencylending", apkfile);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse("file://" + apkfile), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());// 如果不加上这句的话在apk安装完成之后点击单开会崩溃
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 卸载应用程序(没有用到)
     */
    public void uninstallAPK() {
        Uri packageURI = Uri.parse("package:com.zyjr.emergencylending");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);

    }

    /**
     * 下载apk的方法
     *
     * @author rongsheng
     */
    public class downloadApkThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    fileSavePath = sdpath + "download";
                    URL url = new URL(urlApk);
//                    URL url = new URL("http://192.168.9.235:8080/jjt2.0.0.apk");

                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(5 * 1000);// 设置超时时间
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Charser", "GBK,utf-8;q=0.7,*;q=0.3");
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();
                    File file = new File(fileSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(fileSavePath, filName + ".apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        Message message = new Message();
                        message.obj = DOWN;
                        handler.sendMessage(message);
                        if (numread == -1) {
                            // 下载完成
                            // 取消下载对话框显示
                            downLoadDialog.dismiss();
                            Message message2 = new Message();
                            message2.obj = DOWN_FINISH;
                            handler.sendMessage(message2);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

package com.zyjr.emergencylending.ui.my;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.ui.account.ForgetPasswordActivity;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.UpdateVersionService;
import com.zyjr.emergencylending.utils.WYUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wangyin
 * @date 2017/10/13
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.modify_password)
    TextView modifyPassword;
    @BindView(R.id.about_us)
    TextView aboutUs;
    @BindView(R.id.advice_feedback)
    TextView adviceFeedback;
    @BindView(R.id.service_call)
    RelativeLayout serviceCall;
    @BindView(R.id.exit)
    TextView exit;
    @BindView(R.id.version_name)
    TextView versionName;
    @BindView(R.id.version)
    RelativeLayout version;
    private CustomerDialog dialog;
    private CustomerDialog customerDialog;


    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter(SettingActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });

        init();
    }

    private void init() {
        versionName.setText("V" + WYUtils.getAppVersionName(this));
        dialog = new CustomerDialog(mContext);
    }

    @OnClick({R.id.modify_password, R.id.about_us, R.id.advice_feedback, R.id.service_call, R.id.exit, R.id.version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.modify_password:
                final Intent intent = new Intent(mContext, ForgetPasswordActivity.class);
                intent.putExtra("title", "修改密码");
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.about_us:
                mPresenter.getH5Url(Config.H5_URL_ABOUTUS, "关于我们");
                break;
            case R.id.advice_feedback:
                mPresenter.getH5Url(Config.H5_URL_FEEDBACK, "意见反馈");
                break;
            case R.id.service_call:
                phoneDialog();
                break;
            case R.id.exit:
                customerDialog = new CustomerDialog(mContext);
                customerDialog.operateComfirm(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.message_left:
                                customerDialog.dismiss();
                                break;
                            case R.id.message_right:
                                SPUtils.clear(BaseApplication.getContext());
                                customerDialog.dismiss();
                                Intent intent1 = new Intent(SettingActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("index", 0);
                                intent1.putExtras(bundle);
                                startActivity(intent1);
                                break;
                        }
                    }
                }, "您确定要退出么", Color.parseColor("#333333"), "取消", Color.parseColor("#6F95FF"), "确定", Color.parseColor("#6F95FF")).show();

                break;
            case R.id.version:
                dialog.versionUpdate(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.cancel:
                                dialog.dismiss();
                                break;
                            case R.id.update:
                                dialog.dismiss();
                                showDownloadDialog();
                                break;
                        }
                    }
                }, "版本更新！").show();
                break;
        }
    }

    private void phoneDialog() {

        dialog.showHotLineDialog(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.left) {
                    dialog.dismiss();
                } else if (view.getId() == R.id.right) {
                    WYUtils.callPhone(mContext, "400-077-6667");
                    dialog.dismiss();
                }
            }
        }).show();
    }

    /**
     * 下载的提示框
     */
    private void showDownloadDialog() {
        {
            // 构造软件下载对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("正在更新！");
            // 给下载对话框增加进度条
            final LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.downloaddialog, null);
            progressBar = (ProgressBar) v.findViewById(R.id.updateProgress);
            tv_progress = (TextView) v.findViewById(R.id.tv_progress);
            builder.setView(v);
            // 取消更新
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            downLoadDialog = builder.create();
            downLoadDialog.setCancelable(false);
            downLoadDialog.setCanceledOnTouchOutside(false);
            downLoadDialog.show();
            // 现在文件
            downloadApk();
        }
    }

    /**
     * 下载apk,不能占用主线程.所以另开的线程
     */
    private void downloadApk() {
        new downloadApkThread().start();
    }

    private static final int DOWN = 1;// 用于区分正在下载
    private static final int DOWN_FINISH = 0;// 用于区分下载完成
    private String fileSavePath;// 下载新apk的储存地点
    private String updateVersionXMLPath;// 检测更新的xml文件
    private String urlApk;// 下载APk的地址
    private String filName = "jjtNew";// apK名称
    private int progress;// 获取新apk的下载数据量,更新下载滚动条
    private boolean cancelUpdate = false;// 是否取消下载
    private Context context;
    private ProgressBar progressBar;
    private TextView tv_progress;
    private Dialog downLoadDialog;
    private String display;
    private String flag;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {// 跟心ui

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((Integer) msg.obj) {
                case DOWN:
                    progressBar.setProgress(progress);
                    tv_progress.setText(progress + "%");
                    break;
                case DOWN_FINISH:
                    ToastAlone.showShortToast(SettingActivity.this, "文件下载完成,正在安装更新");
                    installAPK();
                    break;

                default:
                    break;
            }
        }

    };


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
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory()
                            + "/";
                    fileSavePath = sdpath + "downloadNew";
                    URL url = new URL("http://192.168.9.235:8080/jjt2.0.0.apk");
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setReadTimeout(5 * 1000);// 设置超时时间
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Charser",
                            "GBK,utf-8;q=0.7,*;q=0.3");
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
                        if (numread <= 0) {
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

    /**
     * 安装apk文件
     */
    private void installAPK() {
//        File apkfile = new File(fileSavePath, filName + ".apk");
//        if (!apkfile.exists()) {
//            return;
//        }
//        // 通过Intent安装APK文件
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        System.out.println("filepath=" + apkfile.toString() + "  "
//                + apkfile.getPath());
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//                "application/vnd.android.package-archive");
//        startActivity(i);
//        android.os.Process.killProcess(android.os.Process.myPid());// 如果不加上这句的话在apk安装完成之后点击单开会崩溃

        File apkfile = new File(fileSavePath, filName + ".apk");
        if (!apkfile.exists()) {
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果是安卓7.0以上
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri uri = FileProvider.getUriForFile(mContext, "com.zyjr.emergencylending", apkfile);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse("file://" + apkfile), "application/vnd.android.package-archive");
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

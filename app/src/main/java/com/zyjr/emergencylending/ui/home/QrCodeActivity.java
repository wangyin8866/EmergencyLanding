package com.zyjr.emergencylending.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.QrBean;
import com.zyjr.emergencylending.ui.home.loan.WriteInfoMainActivity;
import com.zyjr.emergencylending.ui.home.presenter.QrPresenter;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.permission.ToolPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wangyin
 * @date 2017/10/11
 */

public class QrCodeActivity extends BaseActivity<QrPresenter, BaseView<QrBean>> implements BaseView<QrBean> {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.qr_save)
    ImageView qrSave;
    @BindView(R.id.qr_we_chat)
    ImageView qrWeChat;
    @BindView(R.id.circle_of_friends)
    ImageView circleOfFriends;
    @BindView(R.id.iv_qr)
    ImageView ivQr;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    private boolean flag = false;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastAlone.showLongToast(mContext, "保存成功");
                    break;
                case 2:
                    ToastAlone.showLongToast(mContext, "已保存");
                    break;
            }
        }
    };
    private String sUrl = "http://www.baidu.com";
    private String mUrl;
    private UMWeb web;
    private boolean isSuccess = false; // 是否请求成功

    @Override
    protected QrPresenter createPresenter() {
        return new QrPresenter(mContext);
    }

    protected void init() {
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
        mPresenter.getQr(NetConstantValues.QR_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        ButterKnife.bind(this);
        init();
    }


    @OnClick({R.id.qr_save, R.id.qr_we_chat, R.id.circle_of_friends})
    public void onViewClicked(View view) {
        initShare();
        switch (view.getId()) {
            case R.id.qr_save:
                ToolPermission.checkPermission(this, new ToolPermission.PermissionCallBack() {
                            @Override
                            public void callBack(int requestCode, boolean isPass) {
                                LogUtils.d("权限检测结果---" + requestCode + "," + isPass);
                                if (isPass) {
                                    if (isSuccess && StringUtil.isNotEmpty(mUrl)) {
                                        Glide.with(mContext)
                                                .load(mUrl)
                                                .asBitmap()
                                                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                                                    @Override
                                                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                                                        //得到bitmap
                                                        saveImageToGallery(mContext, bitmap);
                                                    }
                                                });
                                    }
                                } else {
                                    ToastAlone.showShortToast(QrCodeActivity.this, "手机存储权限被拒绝,请您到设置页面手动授权");
                                }
                            }
                        },
                        2000,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

                break;
            case R.id.qr_we_chat:
                if (isSuccess && StringUtil.isNotEmpty(mUrl)) {
                    if (UMShareAPI.get(mContext).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                        new ShareAction(QrCodeActivity.this).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN)
                                .setCallback(umShareListener).share();
                    } else {
                        ToastAlone.showShortToast(mContext, "微信未安装");
                    }
                }
                break;
            case R.id.circle_of_friends:
                if (isSuccess && StringUtil.isNotEmpty(mUrl)) {
                    if (UMShareAPI.get(mContext).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                        new ShareAction(QrCodeActivity.this).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                .setCallback(umShareListener).share();
                    } else {
                        ToastAlone.showShortToast(mContext, "微信未安装");
                    }
                }
                break;
        }
    }

    private void initShare() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(QrCodeActivity.this, mPermissionList, 123);
        }
        UMImage thumb = new UMImage(mContext, R.mipmap.logo);
        web = new UMWeb(mUrl);
        web.setTitle("一个用钱满足你心愿的APP");
        web.setThumb(thumb);
        web.setDescription("震惊 | 急借通提速啦，一张身份证，30分钟下款，最高30000元！");
    }

    /**
     * 保存二维码
     *
     * @param context
     * @param bmp
     */
    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        if (flag) {
            handler.sendEmptyMessageDelayed(2, 200);
            return;
        }
        String fileName = System.currentTimeMillis() + ".png";
        File qrFile = new File(Environment.getExternalStorageDirectory(), Constants.QR + fileName);
        if (!qrFile.getParentFile().exists()) {
            qrFile.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(qrFile);
            flag = bmp.compress(Bitmap.CompressFormat.PNG, 80, fos);
            fos.flush();
            fos.close();
            handler.sendEmptyMessageDelayed(1, 200);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), qrFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + qrFile.getPath())));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mContext, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void getCommonData(QrBean baseBean) {
        mUrl = baseBean.getResult().getUrl();
        Glide.with(mContext)
                .load(mUrl)
                .asBitmap()
                .placeholder(R.mipmap.qr_loading)
                .error(R.mipmap.qr_loading)
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        //得到bitmap
                        LogUtils.d("二维码图片加载完成");
                        isSuccess = true;
                        ivQr.setImageBitmap(bitmap);
                    }
                });
        String code = baseBean.getResult().getRecommendcode();
        List<TextView> textViews = new ArrayList<>();
        textViews.add(tv1);
        textViews.add(tv2);
        textViews.add(tv3);
        textViews.add(tv4);
        textViews.add(tv5);
        textViews.add(tv6);
        textViews.add(tv7);
        for (int i = 0; i < code.length(); i++) {
            textViews.get(i).setText(String.valueOf(code.charAt(i)));
        }
    }

    @Override
    public void requestError() {

    }

    @Override
    public void requestSuccess() {

    }
}

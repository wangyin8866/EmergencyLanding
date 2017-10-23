package com.zyjr.emergencylending.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wangyin
 * @date 2017/10/11
 */

public class QrCodeActivity extends BaseActivity {
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

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
        UMImage thumb = new UMImage(this, R.mipmap.ic_launcher);
        final UMWeb web = new UMWeb(sUrl);
        web.setTitle("邀您注册有惊喜");
        web.setThumb(thumb);
        web.setDescription("注册有惊喜，更多活动等你来体验，立即注册！");
        switch (view.getId()) {
            case R.id.qr_save:
                saveImageToGallery(mContext, ((BitmapDrawable) ivQr.getDrawable()).getBitmap());
                break;
            case R.id.qr_we_chat:
                if (UMShareAPI.get(mContext).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    new ShareAction(QrCodeActivity.this).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN)
                            .setCallback(umShareListener).share();
                } else {
                    ToastAlone.showShortToast(mContext, "微信未安装");
                }
                break;
            case R.id.circle_of_friends:
                new ShareAction(QrCodeActivity.this).withMedia(web).setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener).share();
                break;
        }
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
            handler.sendEmptyMessage(2);
            return;
        }
        File appDir = new File(Environment.getExternalStorageDirectory(), "jijietong");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            flag = bmp.compress(Bitmap.CompressFormat.PNG, 80, fos);
            fos.flush();
            fos.close();
            handler.sendEmptyMessage(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
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
}

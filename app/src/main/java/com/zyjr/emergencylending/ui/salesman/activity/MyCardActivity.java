package com.zyjr.emergencylending.ui.salesman.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
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
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.utils.ToastAlone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyin
 * @date 2017/11/13.
 * @description :
 */

public class MyCardActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private ImmersionBar mImmersionBar;
    private UMWeb web1;
    private UMWeb web2;
    private String mUrl;

    @Override
    protected BasePresenter createPresenter() {
        return null;

    }

    public static void skipH5WebView(Context context, String url) {
        Intent intent = new Intent(context, MyCardActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true).init();

        mUrl = getIntent().getStringExtra("url");
        WebSettings settings = mWebView.getSettings();
        /**
         * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
         * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
         * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
         * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
         * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
         * setSupportZoom 设置是否支持变焦
         * */

        //webview在安卓5.0之前默认允许其加载混合网络协议内容
        // 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setDrawingCacheEnabled(true);
        settings.setJavaScriptEnabled(true);
        // 取消滚动条
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // 触摸焦点起作用
        mWebView.requestFocus();
        // 不弹窗浏览器是否保存密码
        settings.setSavePassword(false);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 自动适应屏幕尺寸
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //打开DOM储存
        settings.setDomStorageEnabled(true);
        mWebView.loadUrl(mUrl);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        initListener();
    }

    private void initListener() {
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(MyCardActivity.this, mPermissionList, 123);
                }

                initShare();


                final CustomerDialog customerDialog = new CustomerDialog(mContext);
                customerDialog.cardShareDialog(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.qr_we_chat:
                                customerDialog.dismiss();
                                if (UMShareAPI.get(mContext).isInstall(MyCardActivity.this, SHARE_MEDIA.WEIXIN)) {
                                    new ShareAction(MyCardActivity.this).withMedia(web1).setPlatform(SHARE_MEDIA.WEIXIN)
                                            .setCallback(umShareListener).share();
                                } else {
                                    ToastAlone.showShortToast(mContext, "微信未安装");
                                }
                                break;
                            case R.id.circle_of_friends:
                                customerDialog.dismiss();
                                if (UMShareAPI.get(mContext).isInstall(MyCardActivity.this, SHARE_MEDIA.WEIXIN)) {
                                    new ShareAction(MyCardActivity.this).withMedia(web1).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .setCallback(umShareListener).share();
                                } else {
                                    ToastAlone.showShortToast(mContext, "微信未安装");
                                }
                                break;
                            case R.id.qq:
                                customerDialog.dismiss();
                                if (UMShareAPI.get(mContext).isInstall(MyCardActivity.this, SHARE_MEDIA.QQ)) {
                                    new ShareAction(MyCardActivity.this).withMedia(web1).setPlatform(SHARE_MEDIA.QQ)
                                            .setCallback(umShareListener).share();
                                } else {
                                    ToastAlone.showShortToast(mContext, "QQ未安装");
                                }
                                break;
                            case R.id.qq_zone:
                                customerDialog.dismiss();
                                new ShareAction(MyCardActivity.this).withMedia(web1).setPlatform(SHARE_MEDIA.QZONE)
                                        .setCallback(umShareListener).share();
                                break;
                            case R.id.weibo:
                                customerDialog.dismiss();
                                new ShareAction(MyCardActivity.this).withMedia(web2).setPlatform(SHARE_MEDIA.SINA)
                                        .setCallback(umShareListener).share();
//
                                break;
                            case R.id.share_close:
                                customerDialog.dismiss();
                                break;
                        }
                    }
                }).show();
            }
        });
    }

    private void initShare() {
        UMImage thumb1 = new UMImage(mContext, R.mipmap.logo);
        web1 = new UMWeb(mUrl);
        web1.setTitle("一个用钱满足你心愿的APP");
        web1.setThumb(thumb1);
        web1.setDescription("#心愿借款#一张身份证，30分钟放款，无需其他材料，最高30000元。#急借通，不看征信，不看负债#点击拿钱→→http://suo.im/27jgKm");
        UMImage thumb2 = new UMImage(mContext, R.mipmap.logo);
        web2 = new UMWeb(mUrl);
        web2.setTitle("一个用钱满足你心愿的APP");
        web2.setThumb(thumb2);
        web2.setDescription("震惊 | 急借通提速啦，一张身份证，30分钟下款，最高30000元！");
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
//            LogUtils.e("onCancel",platform.toString());
//            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar.destroy();
        UMShareAPI.get(this).release();
    }
}

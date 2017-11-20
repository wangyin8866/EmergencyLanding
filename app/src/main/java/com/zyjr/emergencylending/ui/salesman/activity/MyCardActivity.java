package com.zyjr.emergencylending.ui.salesman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
    private String sUrl = "http://www.baidu.com";

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
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {
                final CustomerDialog customerDialog = new CustomerDialog(mContext);
                customerDialog.cardShareDialog(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UMImage thumb = new UMImage(mContext, R.mipmap.ic_launcher);
                        final UMWeb web = new UMWeb(sUrl);
                        web.setTitle("邀您注册有惊喜");
                        web.setThumb(thumb);
                        web.setDescription("注册有惊喜，更多活动等你来体验，立即注册！");
                        switch (view.getId()) {
                            case R.id.qr_we_chat:
                                customerDialog.dismiss();
                                if (UMShareAPI.get(mContext).isInstall(MyCardActivity.this, SHARE_MEDIA.WEIXIN)) {
                                    new ShareAction(MyCardActivity.this).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN)
                                            .setCallback(umShareListener).share();
                                } else {
                                    ToastAlone.showShortToast(mContext, "微信未安装");
                                }
                                break;
                            case R.id.circle_of_friends:
                                customerDialog.dismiss();
                                if (UMShareAPI.get(mContext).isInstall(MyCardActivity.this, SHARE_MEDIA.WEIXIN)) {
                                    new ShareAction(MyCardActivity.this).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .setCallback(umShareListener).share();
                                } else {
                                    ToastAlone.showShortToast(mContext, "微信未安装");
                                }
                                break;
                            case R.id.qq:
                                customerDialog.dismiss();
                                if (UMShareAPI.get(mContext).isInstall(MyCardActivity.this, SHARE_MEDIA.QQ)) {
                                    new ShareAction(MyCardActivity.this).withMedia(web).setPlatform(SHARE_MEDIA.QQ)
                                            .setCallback(umShareListener).share();
                                } else {
                                    ToastAlone.showShortToast(mContext, "QQ未安装");
                                }
                                break;
//                            case R.id.qq_zone:
//                                customerDialog.dismiss();
//                                break;
//                            case R.id.weibo:
//                                customerDialog.dismiss();
//                                break;
                            case R.id.share_close:
                                customerDialog.dismiss();
                                break;
                        }
                    }
                }).show();
            }
        });
        String url = getIntent().getStringExtra("url");
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
        mWebView.loadUrl(url);
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
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar.destroy();
        UMShareAPI.get(this).release();
    }
}

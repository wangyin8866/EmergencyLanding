package com.zyjr.emergencylending.ui.home.loan.auth;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xfqz.xjd.mylibrary.CustomProgressDialog;
import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.MobileBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.entity.ZhimaAuthBean;
import com.zyjr.emergencylending.ui.home.View.AuthHelperView;
import com.zyjr.emergencylending.ui.home.presenter.AuthHelperPresenter;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/17
 * 备注: 芝麻信用认证
 */
public class ZhimaAuthActivity extends BaseActivity<AuthHelperPresenter, AuthHelperView> implements AuthHelperView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_idcard_number)
    TextView tvIdcardNumber;
    @BindView(R.id.btn_authorise)
    Button btnAuthorise;
    @BindView(R.id.layout_zhima_content)
    ScrollView layoutContentView;
    @BindView(R.id.webView)
    WebView webView;
    private String userName = "";
    private String idCardNumber = "";
    private CustomProgressDialog loadingDialog = null;
    private String isClickLeft = "1"; // 是否可以点击;1可点击;0不可点击
    private int ERROR_CODE = 0; // 加载webview 错误码

    @Override
    protected AuthHelperPresenter createPresenter() {
        return new AuthHelperPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhima_auth);
        ButterKnife.bind(this);
        init();
        initGetData();
    }

    @OnClick({R.id.btn_authorise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_authorise:
                getAuthUrl();
                break;
        }
    }


    private void init() {
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                LogUtils.d("是否可以点击---" + isClickLeft);
                if (Constants.ONE.equals(isClickLeft)) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
    }

    private void initGetData() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        idCardNumber = intent.getStringExtra("idCardNumber");
        tvName.setText(userName);
        tvIdcardNumber.setText(idCardNumber);
    }

    private void getAuthUrl() {
        Map<String, String> paramMaps = new HashMap<>();
        mPresenter.getZhimaAuthUrl(paramMaps);
    }


    private void jumpToAuthPage(String url) {
        LogUtils.d("芝麻信用授权url:" + url);
        layoutContentView.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        loadingDialog = CustomProgressDialog.createDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        loadHtmlWithDialog(url, webView, loadingDialog);
    }

    public void loadHtmlWithDialog(final String baseUrl, WebView mWebView, final Dialog dialog) {
        WebSettings settings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setDrawingCacheEnabled(true);
        settings.setJavaScriptEnabled(true);
        // 取消滚动条
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // 触摸焦点起作用
        mWebView.requestFocus();
        settings.setSavePassword(false);// 不弹窗浏览器是否保存密码
        settings.setDefaultTextEncodingName("utf-8");
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 自动适应屏幕尺寸
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.loadUrl(baseUrl);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                isClickLeft = "0";
                LogUtils.d("webViewUrl", url);
                view.loadUrl(url);
                if (url.contains("http://m.jijietong.com:8680/h5-static/moblie_web_new/compliance/openSuccess.html")) {
                    String applyId = StringUtil.getUrlValueByName(url, "applyId");
                    String state = StringUtil.getUrlValueByName(url, "state");
                    String success = StringUtil.getUrlValueByName(url, "success");
                    LogUtils.d("获取芝麻信用分:" + "applyId:" + applyId + ",state:" + state + ",success:" + success);
                    if (success.equals("true")) {
                        // 获取信用积分
                        getZhimaScore(applyId, state);
                    } else {
                        view.loadUrl(baseUrl);
                    }
                } else {
                    dialog.show();
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受所有网站的证书
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!ZhimaAuthActivity.this.isFinishing() && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (url.contains("moblie_web_new/compliance/openSuccess.html")) {
                    // 认证成功页面成功加载
                    isClickLeft = "1";
                } else if (url.contains("zmxy.com.cn/auth/index") || url.contains("zmxy.com.cn/auth/verify")) {
                    // 同意授权页面(可点击返回)
                    isClickLeft = "1";
                } else {
                    isClickLeft = "0";
                    // 连接超时或请求超时情况
                    if (ERROR_CODE >= -16 && ERROR_CODE <= -1) {
                        isClickLeft = "1";
                    }
                }
                LogUtils.d(url + "【onPageFinished ---- 加载完成】");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                ERROR_CODE = errorCode;
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    private void getZhimaScore(String applyId, String state) {
        loadingDialog = CustomProgressDialog.createDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("applyId", applyId);
        params.put("state", state);
        mPresenter.getZhimaScore(params);
    }

    @Override
    public void onSuccessSubmit(String apiCode, String returnCode, String msg) {

    }

    @Override
    public void onSuccessGetUserInfo(String apiCode, PersonalInfoBean bean) {
        tvName.setText(bean.getUsername().trim());
        tvIdcardNumber.setText(bean.getIdCard().trim());
    }

    @Override
    public void onSuccessGetZhimaAuthUrl(String apiCode, ZhimaAuthBean bean) {
        jumpToAuthPage(bean.getData());
    }

    @Override
    public void onSuccessGetZhimaScore(String apiCode, String msg) {
        loadingDialog.dismiss();
        ToastAlone.showShortToast(this, msg);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSuccessJudgeMobileValide(String apiCode, MobileBean bean) {

    }

    @Override
    public void onFail(String apiCode, String failMsg) {
        loadingDialog.dismiss();
        ToastAlone.showShortToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        loadingDialog.dismiss();
        ToastAlone.showShortToast(this, errorMsg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (Constants.ZERO.equals(isClickLeft)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}




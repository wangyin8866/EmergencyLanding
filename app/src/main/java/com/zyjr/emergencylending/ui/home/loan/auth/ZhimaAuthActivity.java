package com.zyjr.emergencylending.ui.home.loan.auth;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.ui.home.View.AuthHelperView;
import com.zyjr.emergencylending.ui.home.presenter.AuthHelperPresenter;
import com.zyjr.emergencylending.utils.ToastAlone;

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

        tvName.setText("张三");
        tvIdcardNumber.setText("123456789");

//        Dialog loadingDialog = CustomProgressDialog.createDialog(this);
//        loadingDialog.setCancelable(false);
//        WYUtils.loadHtmlWithDialog("", webView, loadingDialog);
    }

    @OnClick({R.id.btn_authorise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_authorise:
                ToastAlone.showLongToast(this, "前往授权");
                break;
        }
    }


    private void init() {
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
    public void onSuccessSubmit(String returnCode, AuthInfoBean bean) {

    }

    @Override
    public void onFail(String errorMessage) {

    }
}




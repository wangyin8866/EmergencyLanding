package com.zyjr.emergencylending.ui.repayment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.entity.RepaymentLogin;
import com.zyjr.emergencylending.entity.RepaymentSuccess;
import com.zyjr.emergencylending.entity.UserInfo;
import com.zyjr.emergencylending.ui.repayment.View.RepaymentView;
import com.zyjr.emergencylending.ui.repayment.presenter.RepaymentPresenter;
import com.zyjr.emergencylending.utils.HDes3;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * @author wangyin
 * @date 2017/8/9
 */

public class RepaymentFragment extends BaseFragment<RepaymentPresenter, RepaymentView> implements RepaymentView {
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_main, container, false);

        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        //是否有还款
        mPresenter.getData(NetConstantValues.MY_LOAN, "1", "1");
    }

    @Override
    protected RepaymentPresenter createPresenter() {
        return new RepaymentPresenter(mContext);
    }

    @Override
    public void getBorrowInfoByUserId(MyBorrow baseBean) {
        if (baseBean.getResult().getCurrent_borrow() != null) {

            if (Config.TRUE.equals(baseBean.getResult().getCurrent_borrow().getIsRepaymentFlag())) {
                //获取身份证和手机号
                mPresenter.getBasicInfo(NetConstantValues.GET_BASIC_INFO);
            } else {
                WYUtils.loadHtml(Config.NO_REPAY, webView, progressBar);
            }
        } else {
            WYUtils.loadHtml(Config.NO_REPAY, webView, progressBar);
        }
    }

    @Override
    public void getRepaymentLogin(RepaymentSuccess baseBean) {
        String token = baseBean.getToken();

        SPUtils.saveString(mContext, Config.KEY_REPAYMENT_TOKEN, token);
        // 调还款的接口
        mPresenter.getRepaymentH5Url(Config.H5_URL_REPAYMENT);
    }

    @Override
    public void getUserInfo(UserInfo userInfo) {


        try {
            RepaymentLogin repaymentLogin = new RepaymentLogin();

            RepaymentLogin.RecordBean recordBean = new RepaymentLogin.RecordBean(userInfo.getResult().getIdcard(), userInfo.getResult().getTel(), "android", "jjtapp");
            String json = new Gson().toJson(recordBean);
            String des3 = HDes3.encode(json);
            repaymentLogin.setRecord(des3);


            LogUtils.e("repaymentLogin", des3);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(repaymentLogin));

            //获取token
            mPresenter.repaymentLogin(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadH5(H5Bean h5Bean) {
        WYUtils.loadHtml(h5Bean.getResult().getH5_url() + "?login_token=" + SPUtils.getString(mContext, Config.KEY_REPAYMENT_TOKEN, "") + "&page=1", webView, progressBar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getCommonData(Object o) {

    }

    @Override
    public void requestError() {
        WYUtils.showRequestError(llMain,llRetry);
    }

    @Override
    public void requestSuccess() {
        WYUtils.showRequestSuccess(llMain,llRetry);

    }

    @OnClick(R.id.btn_retry)
    public void onViewClicked() {
        //是否有还款
        mPresenter.getData(NetConstantValues.MY_LOAN, "1", "1");
    }
}

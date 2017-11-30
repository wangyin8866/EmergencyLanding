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
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.EffectiveOrderBean;
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
import com.zyjr.emergencylending.utils.ToastAlone;
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
    RelativeLayout llMain;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry;
    private View view;
    private String contractNo = ""; // 合同编号
    private String repayUrl = ""; // 还款url


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_main, container, false);

        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mPresenter.isEffectiveOrder(NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
    }

    @Override
    protected RepaymentPresenter createPresenter() {
        return new RepaymentPresenter(mContext);
    }

    @Deprecated
    @Override
    public void getBorrowInfoByUserId(MyBorrow baseBean) {
        if (baseBean.getResult().getCurrent_borrow() != null) {
            if (Config.TRUE.equals(baseBean.getResult().getCurrent_borrow().getIsRepaymentFlag())) {
                //获取订单状态
                mPresenter.isEffectiveOrder(NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
            } else {
                WYUtils.loadHtmlNew(Config.NO_REPAY, webView, progressBar);
            }
        } else {
            WYUtils.loadHtmlNew(Config.NO_REPAY, webView, progressBar);
        }
    }

    @Deprecated
    @Override
    public void loadH5(H5Bean h5Bean) {
        WYUtils.loadHtmlNew(h5Bean.getResult().getH5_url() + "?contractNo=" + contractNo + "&page=1&time=" + System.currentTimeMillis(), webView, progressBar);
    }

    @Override
    public void isEffectiveOrder(EffectiveOrderBean baseBean) {
        if (Config.TRUE.equals(baseBean.getResult().getOrder_status())) {
            contractNo = baseBean.getResult().getContract_no();
            repayUrl = baseBean.getResult().getH5_url();
            // 加载还款html
            WYUtils.loadHtmlNew(repayUrl + "?contractNo=" + contractNo + "&page=1&time=" + System.currentTimeMillis(), webView, progressBar);
        } else {
            WYUtils.loadHtmlNew(Config.NO_REPAY, webView, progressBar);
        }

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
        showRequestError(llMain, llRetry);
    }

    @Override
    public void requestSuccess() {
        showRequestSuccess(llMain, llRetry);
    }

    @OnClick(R.id.btn_retry)
    public void onViewClicked() {
        mPresenter.isEffectiveOrder(NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
    }


    @Deprecated
    @Override
    public void getRepaymentLogin(RepaymentSuccess baseBean) {
        String token = baseBean.getToken();

        SPUtils.saveString(mContext, Config.KEY_REPAYMENT_TOKEN, token);
        // 调还款的接口
        mPresenter.getRepaymentH5Url(Config.H5_URL_REPAYMENT);
    }

    @Deprecated
    @Override
    public void getUserInfo(UserInfo userInfo) {
        try {
            RepaymentLogin repaymentLogin = new RepaymentLogin();
            RepaymentLogin.RecordBean recordBean = new RepaymentLogin.RecordBean(userInfo.getResult().getIdcard(), userInfo.getResult().getTel(), "android", "jjtapp", System.currentTimeMillis());
            LogUtils.e("recordBean", recordBean.toString());
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

    public void showRequestSuccess(RelativeLayout llMain, LinearLayout llRetry) {
        llMain.setVisibility(View.VISIBLE);
        llRetry.setVisibility(View.GONE);
    }

    public void showRequestError(RelativeLayout llMain, LinearLayout llRetry) {
        llMain.setVisibility(View.VISIBLE);
        llRetry.setVisibility(View.GONE);
    }

}

package com.zyjr.emergencylending.ui.salesman.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.LineCustomerAdapter;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.CircleView;
import com.zyjr.emergencylending.custom.XListView;
import com.zyjr.emergencylending.entity.CustomerBean;
import com.zyjr.emergencylending.entity.RankBean;
import com.zyjr.emergencylending.entity.WaitApplyBean;
import com.zyjr.emergencylending.ui.salesman.presenter.CustomerPresenter;
import com.zyjr.emergencylending.ui.salesman.view.CustomerView;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author wangyin
 * @date 2017/8/9
 */

public class CustomerFragment extends BaseFragment<CustomerPresenter, CustomerView> implements CustomerView, XListView.IXListViewListener {
    @BindView(R.id.circle_client)
    CircleView circleClient;
    @BindView(R.id.circle_apply_for)
    CircleView circleApplyFor;
    @BindView(R.id.circle_success)
    CircleView circleSuccess;
    Unbinder unbinder;
    @BindView(R.id.bg_line_1)
    View bgLine1;
    @BindView(R.id.xlv)
    XListView xlv;
    @BindView(R.id.ll_rank_list)
    LinearLayout llRankList;
    @BindView(R.id.type1)
    TextView type1;
    @BindView(R.id.type2)
    TextView type2;
    @BindView(R.id.type3)
    TextView type3;
    @BindView(R.id.now_month)
    TextView nowMonth;
    @BindView(R.id.ran_list)
    TextView ranList;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry;
    private int type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_fragment_customer_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        LogUtils.e("onCreateView", "onCreateView");
        init();
        xlv.setXListViewListener(this);
        xlv.setPullLoadEnable(false);
        return view;
    }

    private void init() {

        selectTv(1);
        circleClient.setData(1000, 1000);
        circleApplyFor.setData(800, 1000);
        circleSuccess.setData(500, 1000);
    }


    @Override
    protected CustomerPresenter createPresenter() {
        return new CustomerPresenter(mContext);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.circle_client, R.id.circle_apply_for, R.id.circle_success, R.id.ll_rank_list, R.id.type1, R.id.type2, R.id.type3, R.id.btn_retry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_client:
//                startActivity(new Intent(mContext, ActivityClient.class));
                mPresenter.getH5Url(Config.H5_URL_MYRESULTS_CUSTOMER, "客户");
                break;
            case R.id.circle_apply_for:
//                startActivity(new Intent(mContext, ActivityApplyFor.class));
                mPresenter.getH5Url(Config.H5_URL_MYRESULTS_APPLY, "申请");
                break;
            case R.id.circle_success:
//                startActivity(new Intent(mContext, ActivitySuccess.class));
                mPresenter.getH5Url(Config.H5_URL_MYRESULTS_SUCCESS, "成功");
                break;
            case R.id.ll_rank_list:
//                H5WebView.skipH5WebView(mContext, "龙虎榜");
                mPresenter.getH5Url(Config.H5_URL_WINNERSLIST, "龙虎榜");
                break;
            case R.id.type1:
                selectTv(1);
                break;
            case R.id.type2:
                selectTv(2);
                break;
            case R.id.type3:
                selectTv(3);
                break;
            case R.id.btn_retry:
                mPresenter.myPerformance(NetConstantValues.MY_PERFORMANCE, type + "");
                mPresenter.waitApply(NetConstantValues.WAIT_APPLY, type + "", "1");
                mPresenter.rankList(NetConstantValues.RANKING_LIST);
                break;
        }
    }

    public void initTextView() {
        type1.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine);
        type1.setTextColor(Color.parseColor("#FFFFFF"));
        type2.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine);
        type2.setTextColor(Color.parseColor("#FFFFFF"));
        type3.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine);
        type3.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void selectTv(int currentTv) {
        this.type = currentTv;
        initTextView();
        SPUtils.saveInt(mContext, Config.KEY_TYPE, type);
        switch (currentTv) {
            case 1:
                type1.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine_select);
                type1.setTextColor(Color.parseColor("#FFA200"));
                break;
            case 2:
                type2.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine_select);
                type2.setTextColor(Color.parseColor("#FFA200"));
                break;
            case 3:
                type3.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine_select);
                type3.setTextColor(Color.parseColor("#FFA200"));
                break;
        }
        mPresenter.myPerformance(NetConstantValues.MY_PERFORMANCE, type + "");
        mPresenter.waitApply(NetConstantValues.WAIT_APPLY, type + "", "1");
        mPresenter.rankList(NetConstantValues.RANKING_LIST);
    }

    @Override
    public void onRefresh() {
        mPresenter.waitApply(NetConstantValues.WAIT_APPLY, type + "", "1");
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void myPerformance(CustomerBean baseBean) {
        circleClient.setmCircleText(String.valueOf(baseBean.getResult().getCust_num()));
        circleApplyFor.setmCircleText(String.valueOf(baseBean.getResult().getOrder_num()));
        circleSuccess.setmCircleText(String.valueOf(baseBean.getResult().getLend_num()));
    }

    @Override
    public void rankList(RankBean rankBean) {
        RankBean.ResultBean.SelfMapBean.NowMonthBean nowMonthBean = rankBean.getResult().getSelfMap().getNowMonth();
        nowMonth.setText(nowMonthBean.getMon() + "月");
        ranList.setText(nowMonthBean.getRank_num());
        amount.setText(nowMonthBean.getLend_total_amount());
    }

    @Override
    public void waitApply(WaitApplyBean baseBean) {

        xlv.setAdapter(new LineCustomerAdapter(baseBean.getResult().getClerkRecordList(), mContext));
    }

    @Override
    public void getCommonData(Object o) {

    }

    @Override
    public void requestError() {
        WYUtils.showRequestError(llMain, llRetry);
    }

    @Override
    public void requestSuccess() {
        WYUtils.showRequestSuccess(llMain, llRetry);
    }
}

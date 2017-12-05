package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.BankAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.SupportBank;
import com.zyjr.emergencylending.ui.home.View.BankcardInfoView;
import com.zyjr.emergencylending.ui.home.presenter.BankcardInfoPresenter;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.widget.recyc.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/12
 * 备注: 支持银行选择
 */
public class SupportBankActivity extends BaseActivity<BankcardInfoPresenter, BankcardInfoView> implements BankcardInfoView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.rv_banklist)
    RecyclerView rvBankList;
    @BindView(R.id.root_refreshview)
    PullToRefreshScrollView pullToRefreshScrollView;

    private List<SupportBank> supportBankList = new ArrayList<>();
    private BankAdapter bankAdapter;

    @Override
    protected BankcardInfoPresenter createPresenter() {
        return new BankcardInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_banklist);
        ButterKnife.bind(this);
        init();
        loadingSupportBanklist();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        bankAdapter = new BankAdapter(R.layout.rv_item_bankcard, supportBankList);
        rvBankList.setLayoutManager(new LinearLayoutManager(this));
        rvBankList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        rvBankList.setAdapter(bankAdapter);
        // TODO 获取支持银行卡列表
        bankAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SupportBank supportBank = supportBankList.get(position);
                LogUtils.d("选择银行--->" + supportBank.toString());
                Intent intent = new Intent();
                intent.putExtra("bank", supportBank);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadingSupportBanklist();
            }
        });
    }

    @Override
    public void onSuccessGetSupportBanks(String returnCode, List<SupportBank> supportBanks) {
        supportBankList = supportBanks;
        bankAdapter.setNewData(supportBankList);
        pullToRefreshScrollView.onRefreshComplete();
        bankAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessGetNoCard(String returnCode, String bankUsername, String idCard) {

    }


    @Override
    public void onSuccessGet(String returnCode, BankcardInfo bean) {

    }

    @Override
    public void onSuccessAdd(String returnCode, String msg) {

    }

    @Override
    public void onSuccessEdit(String returnCode, String msg) {

    }


    @Override
    public void onFail(String returnCode, String failMsg) {
        pullToRefreshScrollView.onRefreshComplete();
        ToastAlone.showShortToast(this, failMsg);
    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        pullToRefreshScrollView.onRefreshComplete();
        ToastAlone.showShortToast(this, errorMsg);
    }

    private void loadingSupportBanklist() {
        if (supportBankList != null && supportBankList.size() > 0) {
            supportBankList.clear();
            bankAdapter.notifyDataSetChanged();
        }
        Map<String, String> paramsMap = new HashMap<>();
        mPresenter.getSupportBankList(paramsMap);
    }
}

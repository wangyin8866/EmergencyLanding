package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.BankAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.BankBean;
import com.zyjr.emergencylending.utils.AppToast;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/12
 * 备注: 支持银行选择
 */
public class SupportBankActivity extends BaseActivity {

    @BindView(R.id.rv_banklist)
    RecyclerView rvBankList;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;

    private List<BankBean> bankBeanList;
    private BankAdapter bankAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_banklist);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        bankBeanList = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            BankBean bankBean = new BankBean("test" + i, "中国工商", false);
            bankBeanList.add(bankBean);
        }
        bankAdapter = new BankAdapter(R.layout.rv_item_bankcard, bankBeanList);
        rvBankList.setLayoutManager(new LinearLayoutManager(this));
        rvBankList.setAdapter(bankAdapter);
        easylayout.setLoadMoreModel(LoadModel.NONE);
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<BankBean> list = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            BankBean bankBean = new BankBean("test--1>" + i, "中国农业", false);
                            list.add(bankBean);
                        }
                        bankAdapter.setNewData(list);
                        easylayout.refreshComplete();
                        LogUtils.d("refreshComplete------------");
                        AppToast.makeToast(SupportBankActivity.this, "refresh success");
                    }
                }, 1000);
            }
        });

    }
}

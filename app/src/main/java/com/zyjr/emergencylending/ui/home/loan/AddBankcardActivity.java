package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.BankFrontshowAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.BankBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/16
 * 备注: 添加银行卡信息
 */
public class AddBankcardActivity extends BaseActivity {

    @BindView(R.id.rv_supported_bank)
    RecyclerView rvSupportedBank;
    @BindView(R.id.ll_openbank_select)
    LinearLayout llOpenbankSelect;

    private List<BankBean> bankBeanList = null;
    BankFrontshowAdapter bankAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bankcard);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        bankBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BankBean bankBean = new BankBean("test" + i, "中国工商", false);
            bankBeanList.add(bankBean);
        }
        bankAdapter = new BankFrontshowAdapter(R.layout.rv_item_bankcard_show, bankBeanList);
        rvSupportedBank.setLayoutManager(new GridLayoutManager(this, 3));
        rvSupportedBank.setAdapter(bankAdapter);
    }


    @OnClick({R.id.ll_openbank_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_openbank_select:
                startActivity(new Intent(this, SupportBankActivity.class));
                break;
        }
    }
}

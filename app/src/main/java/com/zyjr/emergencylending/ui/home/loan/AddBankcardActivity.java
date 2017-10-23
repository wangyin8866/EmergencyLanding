package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.BankFrontshowAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.BankBean;
import com.zyjr.emergencylending.utils.DateUtil;

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
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.et_bankcard_number)
    ClearEditText etBankcardNumber; // 银行卡号
    @BindView(R.id.tv_openbank)
    TextView tvOpenbank; // 开户行
    @BindView(R.id.et_reserved_phone)
    ClearEditText etReservedPhone; // 预留手机号码
    @BindView(R.id.btn_add)
    Button btnAdd; // 添加银行卡
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

        init();
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


    @OnClick({R.id.ll_openbank_select, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_openbank_select:
                startActivity(new Intent(this, SupportBankActivity.class));
                break;

            case R.id.btn_add:  // 添加银行卡

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
}

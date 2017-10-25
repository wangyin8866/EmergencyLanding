package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.BankFrontshowAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.db.BankcardDb;
import com.zyjr.emergencylending.entity.BankBean;
import com.zyjr.emergencylending.entity.BankDbBean;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.SupportBank;
import com.zyjr.emergencylending.ui.home.View.BankcardInfoView;
import com.zyjr.emergencylending.ui.home.presenter.BankcardInfoPresenter;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/16
 * 备注: 添加银行卡信息
 */
public class AddBankcardActivity extends BaseActivity<BankcardInfoPresenter, BankcardInfoView> implements BankcardInfoView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName; // 姓名
    @BindView(R.id.tv_idcard_number)
    TextView tvIdcardNumer; //身份证号
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
    private CharSequence temp;
    private BankcardInfo bankcardInfo = null;
    private SQLiteDatabase db = null;

    @Override
    protected BankcardInfoPresenter createPresenter() {
        return new BankcardInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bankcard);
        ButterKnife.bind(this);

        init();
        initData();
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

    private void validateData() {
        String bankcardNumber = etBankcardNumber.getText().toString().trim(); // 银行卡号
        String openBank = tvOpenbank.getText().toString().trim(); // 开户行
        String reservedPhone = etReservedPhone.getText().toString().trim();// 预留手机号

    }


    private void init() {
        db = BankcardDb.getInstance().openDatabase(AddBankcardActivity.this);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {
            }
        });

        etBankcardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (temp.length() > 6) {
                    if (StringUtil.isEmpty(tvOpenbank.getText().toString().trim())) {
                        BankDbBean bankDbBean = BankcardDb.getInstance().queryBankcard(db, etBankcardNumber.getText().toString());
                        if (null == bankDbBean) {
//                            ToastAlone.showShortToast(AddBankcardActivity.this, "暂未查到对应的银行");
                        } else {
                            LogUtils.d("获取的银行信息为:" + bankDbBean + ",可用");
                            bankcardInfo = new BankcardInfo();
                            if (StringUtil.isNotEmpty(bankDbBean.getBank_code())) {
                                bankcardInfo.setBank_code(bankDbBean.getBank_code());
                            }
                            if (StringUtil.isNotEmpty(bankDbBean.getBank_name())) {
                                bankcardInfo.setBank_name(bankDbBean.getBank_name());
                                tvOpenbank.setText(bankDbBean.getBank_name().trim());
                                ToastAlone.showShortToast(AddBankcardActivity.this, bankDbBean.getBank_name());
                            }
                        }
                    }
                } else {
                    tvOpenbank.setText("");
                    bankcardInfo = null;
                }
            }
        });
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

    @Override
    public void onSuccessGetSupportBanks(String returnCode, List<SupportBank> supportBanks) {

    }

    @Override
    public void onSuccessGet(String returnCode, BankcardInfo bean) {

    }

    @Override
    public void onSuccessAdd(String returnCode, BankcardInfo bean) {

    }

    @Override
    public void onSuccessEdit(String returnCode, BankcardInfo bean) {

    }

    @Override
    public void onFail(String errorMessage) {

    }
}

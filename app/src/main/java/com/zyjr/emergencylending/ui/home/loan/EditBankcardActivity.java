package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.BankFrontshowAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.db.BankcardDb;
import com.zyjr.emergencylending.entity.BankDbBean;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.SupportBank;
import com.zyjr.emergencylending.ui.home.View.BankcardInfoView;
import com.zyjr.emergencylending.ui.home.presenter.BankcardInfoPresenter;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ReflectionUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/16
 * 备注: 修改银行卡信息
 */
public class EditBankcardActivity extends BaseActivity<BankcardInfoPresenter, BankcardInfoView> implements BankcardInfoView {
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
    @BindView(R.id.iv_choose_bank)
    ImageView ivChooseBank; // 选择银行
    @BindView(R.id.et_reserved_phone)
    ClearEditText etReservedPhone; // 预留手机号码
    @BindView(R.id.btn_add)
    Button btnAdd; // 修改银行卡
    @BindView(R.id.rv_supported_bank)
    RecyclerView rvSupportedBank;
    @BindView(R.id.ll_openbank_select)
    LinearLayout llOpenbankSelect;

    private List<SupportBank> supportBankList = null;
    BankFrontshowAdapter bankAdapter;
    private CharSequence temp;
    private BankcardInfo bankcardInfo = null;
    private SQLiteDatabase db = null;
    private boolean isSupport = false; // 是否支持

    @Override
    protected BankcardInfoPresenter createPresenter() {
        return new BankcardInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bankcard);
        ButterKnife.bind(this);
        defaultHttp();
        init();
    }


    @OnClick({R.id.ll_openbank_select, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_openbank_select:
                Intent intent = new Intent(this, SupportBankActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.btn_add:  // 修改银行卡
                validateData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            SupportBank supportBank = (SupportBank) data.getSerializableExtra("bank");
            LogUtils.d("接收回传信息--->" + supportBank.toString());
            if (StringUtil.isNotEmpty(supportBank.getCode_()) && StringUtil.isNotEmpty(supportBank.getName_())) {
                tvOpenbank.setText(supportBank.getName_().trim());
                if (bankcardInfo == null) {
                    bankcardInfo = new BankcardInfo();
                }
                bankcardInfo.setBank_code(supportBank.getCode_().trim());
            }
        }
    }

    private void validateData() {
        String userName = tvUserName.getText().toString().trim(); // 姓名
        String idcardNumber = tvIdcardNumer.getText().toString().trim(); // 身份证号
        String bankcardNumber = etBankcardNumber.getText().toString().trim(); // 银行卡号
        String openBank = tvOpenbank.getText().toString().trim(); // 开户行
        String reservedPhone = etReservedPhone.getText().toString().trim();// 预留手机号
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(idcardNumber)) {
            ToastAlone.showShortToast(this, "请重新获取绑定银行卡信息");
            return;
        }
        if (StringUtil.isEmpty(bankcardNumber)) {
            ToastAlone.showShortToast(this, "请输入银行卡");
            return;
        }
        if (bankcardNumber.length() < 15) {
            ToastAlone.showShortToast(this, "请正确填写银行卡");
            return;
        }
        if (StringUtil.isEmpty(openBank)) {
            ToastAlone.showShortToast(this, "请选择开户银行");
            return;
        }
        if (StringUtil.isEmpty(reservedPhone)) {
            ToastAlone.showShortToast(this, "请输入银行预留手机号");
            return;
        }
        if (!StringUtil.isMobile(reservedPhone)) {
            ToastAlone.showShortToast(this, "请正确填写银行预留手机号");
            return;
        }
        if (bankcardInfo == null) {
            LogUtils.d("编辑银行卡信息----" + bankcardInfo);
            ToastAlone.showShortToast(this, "请重新获取信息");
            return;
        }
        bankcardInfo.setBankcard_no(bankcardNumber);
        bankcardInfo.setBank_name(openBank);
        bankcardInfo.setBank_phone(reservedPhone);
        Map<String, String> paramsMap = ReflectionUtils.beanToMap(bankcardInfo);
        mPresenter.editBindBankcardInfo(paramsMap);
    }

    private void showBankcardInfo(BankcardInfo bankcard) {
        tvUserName.setText(bankcard.getBank_username());
        tvIdcardNumer.setText(StringUtil.hideIDcard(bankcard.getId_card()));
        etBankcardNumber.setText(bankcard.getBankcard_no());
        tvOpenbank.setText(bankcard.getBank_name());
    }

    private void defaultHttp() {
        loadingBindBankcardInfo();
    }

    private void init() {
        topBar.setTitle("修改银行卡");
        btnAdd.setText("确认修改");
        db = BankcardDb.getInstance().openDatabase(EditBankcardActivity.this);
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
                if (temp.length() >= 15) {
                    if (StringUtil.isEmpty(tvOpenbank.getText().toString().trim())) {
                        BankDbBean bankDbBean = BankcardDb.getInstance().queryBankcard(db, etBankcardNumber.getText().toString());
                        if (null == bankDbBean) {
                            LogUtils.d(etBankcardNumber.getText().toString() + ",暂未查到对应的银行");
                        } else {
                            LogUtils.d("获取的银行信息为:" + bankDbBean + ",可用");
                            if (bankcardInfo == null) {
                                bankcardInfo = new BankcardInfo();
                            }
                            // 如果银行卡不在支持列表中,则提示不符
                            if (supportBankList != null && supportBankList.size() > 0) {
                                for (int i = 0; i < supportBankList.size(); i++) {
                                    if (supportBankList.get(i).getCode_().equals(bankDbBean.getBank_code())) {
                                        isSupport = true;
                                        break;
                                    }
                                    if (i == supportBankList.size() - 1) {
                                        LogUtils.d("库里暂不支持该银行的借记卡");
                                        return;
                                    }
                                }
                            }
                            if (StringUtil.isNotEmpty(bankDbBean.getBank_code())) {
                                bankcardInfo.setBank_code(bankDbBean.getBank_code());
                            }
                            if (StringUtil.isNotEmpty(bankDbBean.getBank_name())) {
                                bankcardInfo.setBank_name(bankDbBean.getBank_name());
                                tvOpenbank.setText(bankDbBean.getBank_name().trim());
                            }
                        }
                    }
                } else if (temp.length() < 15 && temp.length() > 0) {
                    isSupport = false;
                    tvOpenbank.setText("");
                    bankcardInfo.setBank_code("");
                    bankcardInfo.setBank_name("");
                }
            }
        });
    }

    /**
     * 弹出确认对话框
     */
    private void showOperateConfirm() {
        final CustomerDialog customerDialog = new CustomerDialog(EditBankcardActivity.this);
        customerDialog.operateComfirm(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.message_left:
                        ToastAlone.showShortToast(EditBankcardActivity.this, "取消");
                        customerDialog.dismiss();

                        break;
                    case R.id.message_right:
                        ToastAlone.showShortToast(EditBankcardActivity.this, "确认");

                        break;
                }
            }
        }, "您确定要保存修改信息吗", 0, "取消", 0, "确定", 0).show();
    }

    private void loadingBindBankcardInfo() {
        Map<String, String> paramsMap = new HashMap<>();
        mPresenter.getBindBankcardInfo(paramsMap);
    }

    private void loadingSupportBankList() {
        Map<String, String> paramsMap = new HashMap<>();
        mPresenter.getSupportBankList(paramsMap);
    }

    private void showSupportBanklist(List<SupportBank> supportBanks) {
        supportBankList = supportBanks;
        bankAdapter = new BankFrontshowAdapter(R.layout.rv_item_bankcard_show, supportBankList);
        rvSupportedBank.setLayoutManager(new GridLayoutManager(this, 3));
        rvSupportedBank.setAdapter(bankAdapter);
    }


    @Override
    public void onSuccessGetSupportBanks(String returnCode, List<SupportBank> supportBanks) {
        showSupportBanklist(supportBanks);
    }


    @Override
    public void onSuccessGet(String returnCode, BankcardInfo bean) {
        bankcardInfo = bean;
        showBankcardInfo(bankcardInfo);
        loadingSupportBankList();
    }

    @Override
    public void onSuccessGetNoCard(String returnCode, String bankUsername, String idCard) {

    }

    @Override
    public void onSuccessAdd(String returnCode, String msg) {

    }

    @Override
    public void onSuccessEdit(String returnCode, String msg) {
        ToastAlone.showShortToast(this, "修改成功");
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFail(String returnCode, String errorMsg) {
        ToastAlone.showShortToast(this, errorMsg);
    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        ToastAlone.showShortToast(this, errorMsg);
    }
}

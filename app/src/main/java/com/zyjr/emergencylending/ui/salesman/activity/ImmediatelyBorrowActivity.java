package com.zyjr.emergencylending.ui.salesman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.ImmediateBean;
import com.zyjr.emergencylending.ui.salesman.presenter.ImmediatelyPresenter;
import com.zyjr.emergencylending.ui.salesman.view.ImmediatelyView;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author wangyin
 * @date 2017/10/18
 * 立即借款判断
 */

public class ImmediatelyBorrowActivity extends BaseActivity<ImmediatelyPresenter, ImmediatelyView> implements ImmediatelyView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.get_verification_code)
    Button getVerificationCode;
    @BindView(R.id.next_step)
    Button nextStep;

    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    private String phone;
    private Subscription subscription;
    private String verification;

    @Override
    protected ImmediatelyPresenter createPresenter() {
        return new ImmediatelyPresenter(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_activity_immediately_borrow);
        ButterKnife.bind(this);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });

        //发送验证码按钮
        subscription = RxView.clicks(getVerificationCode).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                phone = edPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入手机号码");
                } else if (!WYUtils.checkPhone(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入正确的手机号码");
                } else {
                    mPresenter.sendSMS(getVerificationCode, NetConstantValues.SMS, phone);
                }
            }
        });
        //提交按钮
        subscription = RxView.clicks(nextStep).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                phone = edPhone.getText().toString();
                verification = verificationCode.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入手机号码");
                } else if (!WYUtils.checkPhone(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入正确的手机号码");
                } else if (TextUtils.isEmpty(verification)) {
                    ToastAlone.showShortToast(mContext, "请输入验证码");
                } else {
                    mPresenter.preCheckBook(NetConstantValues.PRE_CHECK_BOOK, phone, verification);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();

    }

    @Override
    public void preCheckBook(ImmediateBean baseBean) {

        ImmediateBean.ResultBean resultBean = baseBean.getResult();

        SPUtils.saveString(mContext, Config.KEY_CUST_ID, resultBean.getCust_id());
        if (Config.TRUE.equals(resultBean.getIs_exists())) {
            if (Config.TRUE.equals(resultBean.getIs_lz())) {
                final CustomerDialog customerDialog = new CustomerDialog(mContext);
                customerDialog.borrowSkip(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.iv_close:
                                customerDialog.dismiss();
                                break;
                            case R.id.btn_confirm_submit:
                                mPresenter.borrowSkip(NetConstantValues.ONLINE_TO_OFFLINE, SPUtils.getString(mContext, Config.KEY_CUST_ID, ""));
                                break;
                        }
                    }
                }).show();
            } else {
                final CustomerDialog customerDialog = new CustomerDialog(mContext);
                customerDialog.borrowSkip2(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.iv_close:
                                customerDialog.dismiss();
                                break;
                            case R.id.btn_confirm_submit:
                                customerDialog.dismiss();
                                break;
                        }
                    }
                }).show();
            }
        } else {
            startActivity(new Intent(mContext, BorrowActivity.class));
        }

    }

    @Override
    public void onlineToOffline(BaseBean baseBean) {
        startActivity(new Intent(mContext, BorrowActivity.class));
    }
}

package com.zyjr.emergencylending.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.UserStatus;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.BankcardInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.ContactInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.PersonalInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.WorkInfoActivity;
import com.zyjr.emergencylending.ui.my.presenter.PersonalDataPresenter;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author wangyin
 * date 2017/10/14.
 * description : 个人资料
 *
 * @author wangyin
 */

public class PersonalDataActivity extends BaseActivity<PersonalDataPresenter, BaseView<UserStatus>> implements BaseView<UserStatus> {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.pic_personal)
    ImageView picPersonal;
    @BindView(R.id.icon_pic_personal)
    ImageView iconPicPersonal;
    @BindView(R.id.pic_job)
    ImageView picJob;
    @BindView(R.id.icon_pic_job)
    ImageView iconPicJob;
    @BindView(R.id.pic_contact)
    ImageView picContact;
    @BindView(R.id.icon_pic_contact)
    ImageView iconPicContact;
    @BindView(R.id.pic_card)
    ImageView picCard;
    @BindView(R.id.icon_pic_card)
    ImageView iconPicCard;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry;
    /**
     * 是否填写个人资料
     */
    private String personalStatus;
    /**
     * 是否可编辑
     */
    private String isPersonalEdit;
    /**
     * 是否填写工作信息
     */
    private String jobStatus;
    /**
     * 是否可编辑
     */
    private String isJobEdit;
    /**
     * 是否填写联系人
     */
    private String contactStatus;
    /**
     * 是否可编辑
     */
    private String isContactEdit;
    /**
     * 是否绑定银行卡
     */
    private String cardStatus;
    /**
     * 是否可编辑
     */
    private String isCardEdit;

    /**
     * 是否银行卡放款失败
     */
    private String isBankError;


    @Override
    protected PersonalDataPresenter createPresenter() {
        return new PersonalDataPresenter(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data_my);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserInfoStatus(NetConstantValues.ROUTER_GET_WRITE_INFO);

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

    @OnClick({R.id.pic_personal, R.id.pic_job, R.id.pic_contact, R.id.pic_card})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.pic_personal:
                intent = new Intent(mContext, PersonalInfoActivity.class);
                intent.putExtra("isEdit", isPersonalEdit);
                intent.putExtra("status", personalStatus);
                startActivity(intent);
                break;
            case R.id.pic_job:
                intent = new Intent(mContext, WorkInfoActivity.class);
                intent.putExtra("isEdit", isJobEdit);
                intent.putExtra("status", jobStatus);
                startActivity(intent);

                break;
            case R.id.pic_contact:
                if (StringUtil.isNotEmpty(personalStatus) && personalStatus.equals("0")) {
                    ToastAlone.showLongToast(this, "请先完善个人信息!");
                    return;
                }
                intent = new Intent(mContext, ContactInfoActivity.class);
                intent.putExtra("isEdit", isContactEdit);
                intent.putExtra("status", contactStatus);
                startActivity(intent);
                break;
            case R.id.pic_card:
                if (StringUtil.isNotEmpty(personalStatus) && personalStatus.equals("0")) {
                    ToastAlone.showLongToast(this, "请先完善个人信息!");
                    return;
                }
                intent = new Intent(mContext, BankcardInfoActivity.class);
                intent.putExtra("isEdit", isCardEdit);
                intent.putExtra("status", cardStatus);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getCommonData(UserStatus baseBean) {
        UserStatus.ResultBean resultBean = baseBean.getResult();
        personalStatus = resultBean.getUser_data_status();
        isPersonalEdit = resultBean.getUser_data_edit();
        jobStatus = resultBean.getUser_job_status();
        isJobEdit = resultBean.getUser_job_edit();
        contactStatus = resultBean.getUser_contact_status();
        isContactEdit = resultBean.getUser_contact_edit();
        cardStatus = resultBean.getUser_bank_status();
        isCardEdit = resultBean.getUser_bank_edit();
        isBankError = resultBean.getIs_loan_fail();
        mPresenter.initStatus(personalStatus, isPersonalEdit, jobStatus, isJobEdit, contactStatus, isContactEdit, cardStatus, isCardEdit, isBankError,
                iconPicPersonal, iconPicJob, iconPicContact, iconPicCard);

    }

    @Override
    public void requestError() {
        WYUtils.showRequestError(llMain, llRetry);
    }

    @Override
    public void requestSuccess() {
        WYUtils.showRequestSuccess(llMain, llRetry);
    }

    @OnClick(R.id.btn_retry)
    public void onViewClicked() {
        mPresenter.getUserInfoStatus(NetConstantValues.ROUTER_GET_WRITE_INFO);
    }
}

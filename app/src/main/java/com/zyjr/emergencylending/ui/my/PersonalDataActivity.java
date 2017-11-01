package com.zyjr.emergencylending.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.BankcardInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.ContactInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.PersonalInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.WorkInfoActivity;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author wangyin
 * date 2017/10/14.
 * description : 个人资料
 * @author wangyin
 */

public class PersonalDataActivity extends BaseActivity {
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
    /**
     * 是否填写个人资料
     */
    private boolean personalStatus = true;
    /**
     * 是否可编辑
     */
    private boolean isPersonalEdit = true;
    /**
     * 是否填写工作信息
     */
    private boolean jobStatus;
    /**
     * 是否可编辑
     */
    private boolean isJobEdit = true;
    /**
     * 是否填写联系人
     */
    private boolean contactStatus;
    /**
     * 是否可编辑
     */
    private boolean isContactEdit = true;
    /**
     * 是否绑定银行卡
     */
    private boolean cardStatus;
    /**
     * 是否可编辑
     */
    private boolean isCardEdit = true;

    /**
     * 是否已提交订单
     */
    private boolean isOrder = true;
    /**
     * 是否银行卡放款失败
     */
    private boolean isBankError = true;


    @Override
    protected BasePresenter createPresenter() {
        return null;
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
        switchIconState();
    }

    private void switchIconState() {




        if (isOrder) {//已提交订单,都不能修改

            isPersonalEdit = false;
            isJobEdit = false;
            isContactEdit = false;
            iconPicPersonal.setImageResource(R.mipmap.data_icon_complete);
            iconPicJob.setImageResource(R.mipmap.data_icon_complete);
            iconPicContact.setImageResource(R.mipmap.data_icon_complete);

            if (isBankError) {//银行放款失败
                isCardEdit = true;
                iconPicCard.setVisibility(View.GONE);
            } else {
                iconPicCard.setVisibility(View.VISIBLE);
                iconPicCard.setImageResource(R.mipmap.data_icon_complete);
                isCardEdit = false;
            }

        } else {//未提交订单

            isPersonalEdit = true;
            isJobEdit = true;
            isContactEdit = true;
            isCardEdit = true;
            if (!personalStatus) {
                iconPicPersonal.setVisibility(View.GONE);
            } else {
                iconPicPersonal.setVisibility(View.VISIBLE);
                iconPicPersonal.setImageResource(R.mipmap.data_icon_edit_complete);
            }
            if (!jobStatus) {
                iconPicJob.setVisibility(View.GONE);
            } else {
                iconPicJob.setVisibility(View.VISIBLE);
                iconPicJob.setImageResource(R.mipmap.data_icon_edit_complete);
            }
            if (!contactStatus) {
                iconPicContact.setVisibility(View.GONE);
            } else {
                iconPicContact.setVisibility(View.VISIBLE);
                iconPicContact.setImageResource(R.mipmap.data_icon_edit_complete);
            }
            if (!cardStatus) {
                iconPicCard.setVisibility(View.GONE);
            } else {
                iconPicCard.setVisibility(View.VISIBLE);
                iconPicCard.setImageResource(R.mipmap.data_icon_edit_complete);
            }
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

    @OnClick({R.id.pic_personal, R.id.pic_job, R.id.pic_contact, R.id.pic_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pic_personal:
                // TODO: 2017/10/16 个人资料
                ToastAlone.showShortToast(mContext, "个人资料");
                startActivity(new Intent(mContext, PersonalInfoActivity.class));
                break;
            case R.id.pic_job:
                // TODO: 2017/10/16 工作信息
                ToastAlone.showShortToast(mContext, "工作信息");
                Intent intent = new Intent(mContext, WorkInfoActivity.class);
                LogUtils.e("isJobEdit", isJobEdit + "");
                intent.putExtra("isJobEdit", isJobEdit);
                startActivity(intent);

                break;
            case R.id.pic_contact:
                // TODO: 2017/10/16 联系人
                ToastAlone.showShortToast(mContext, "联系人");
                startActivity(new Intent(mContext, ContactInfoActivity.class));
                break;
            case R.id.pic_card:
                // TODO: 2017/10/16 银行卡
                ToastAlone.showShortToast(mContext, "银行卡");
                startActivity(new Intent(mContext, BankcardInfoActivity.class));
                break;
        }
    }
}

package com.zyjr.emergencylending.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyin on 2017/10/14.
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
        if (true) { //个人资料
            iconPicPersonal.setImageResource(R.mipmap.data_icon_edit);
            picPersonal.setEnabled(true);
        } else {
            iconPicPersonal.setImageResource(R.mipmap.data_icon_complete);
            picPersonal.setEnabled(false);
        }
        if (true) { //工作信息
            iconPicJob.setImageResource(R.mipmap.data_icon_edit);
            picJob.setEnabled(true);
        } else {
            iconPicJob.setImageResource(R.mipmap.data_icon_complete);
            picJob.setEnabled(false);
        }
        if (true) { //联系人
            iconPicContact.setImageResource(R.mipmap.data_icon_edit);
            picContact.setEnabled(true);
        } else {
            iconPicContact.setImageResource(R.mipmap.data_icon_complete);
            picContact.setEnabled(false);
        }
        if (true) { //银行卡
            iconPicCard.setImageResource(R.mipmap.data_icon_edit);
            picCard.setEnabled(true);
        } else {
            iconPicCard.setImageResource(R.mipmap.data_icon_complete);
            picCard.setEnabled(false);
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
                break;
            case R.id.pic_job:
                // TODO: 2017/10/16 工作信息
                break;
            case R.id.pic_contact:
                // TODO: 2017/10/16 联系人
                break;
            case R.id.pic_card:
                // TODO: 2017/10/16 银行卡
                break;
        }
    }
}

package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.AppConfig;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.UserInfoManager;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.widget.pop.AreaSelectPop;
import com.zyjr.emergencylending.widget.pop.SingleSelectPop;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 联系人信息
 */

public class ContactInfoActivity extends BaseActivity {
    @BindView(R.id.tv_contact_name1)
    ClearEditText tvContactName1; // 联系人姓名1
    @BindView(R.id.iv_contact_phone1)
    ImageView ivContactPhone1; // 联系人号码1
    @BindView(R.id.tv_relation1)
    TextView tvRelation1; // 关系1
    @BindView(R.id.tv_contact_name2)
    ClearEditText tvContactName2; // 联系人姓名2
    @BindView(R.id.iv_contact_phone2)
    ImageView ivContactPhone2; // 联系人号码2
    @BindView(R.id.tv_relation2)
    TextView tvRelation2; // 关系2


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_contact_phone1, R.id.ll_contact_relation1, R.id.iv_contact_phone2, R.id.ll_contact_relation2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_contact_relation1:  // 关系1
                SingleSelectPop popRelationSelect1 = new SingleSelectPop(this, AppConfig.contactRelation());
                popRelationSelect1.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        LogUtils.d("选择的关系信息1:" + select.toString());
                        tvRelation1.setText(select.getName());
                    }
                });
                popRelationSelect1.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_contact_relation2:  // 关系2
                SingleSelectPop popRelationSelect2 = new SingleSelectPop(this, AppConfig.contactRelation());
                popRelationSelect2.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        LogUtils.d("选择的关系信息2:" + select.toString());
                        tvRelation2.setText(select.getName());
                    }
                });
                popRelationSelect2.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.iv_contact_phone1: // 联系电话1

                break;

            case R.id.iv_contact_phone2: // 联系电话2

                break;

        }
    }

}

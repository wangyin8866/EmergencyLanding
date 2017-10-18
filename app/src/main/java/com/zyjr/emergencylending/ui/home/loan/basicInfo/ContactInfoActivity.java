package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.UserInfoManager;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.permission.AppOpsManagerUtil;
import com.zyjr.emergencylending.utils.permission.ToolPermission;
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
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_contact_name1)
    ClearEditText tvContactName1; // 联系人姓名1
    @BindView(R.id.et_contact_phone1)
    EditText etContactPhone1; // 联系人姓名1
    @BindView(R.id.iv_contact_phone1)
    ImageView ivContactPhone1; // 联系人号码1
    @BindView(R.id.tv_relation1)
    TextView tvRelation1; // 关系1
    @BindView(R.id.tv_contact_name2)
    ClearEditText tvContactName2; // 联系人姓名2
    @BindView(R.id.et_contact_phone2)
    EditText etContactPhone2; // 联系人姓名2
    @BindView(R.id.iv_contact_phone2)
    ImageView ivContactPhone2; // 联系人号码2
    @BindView(R.id.tv_relation2)
    TextView tvRelation2; // 关系2

    private int selectPhoneType = 0; // 选择号码标识

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        ButterKnife.bind(this);
        init();
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

            case R.id.iv_contact_phone1: // 联系电话1(可以调往通讯录)
                jumpToSelectPhone(1);
                break;

            case R.id.iv_contact_phone2: // 联系电话2(可以调往通讯录)
                jumpToSelectPhone(2);
                break;

        }
    }


    private void jumpToSelectPhone(int i) {
        selectPhoneType = i;
        if (ToolPermission.checkSelfPermission(this, null, Manifest.permission.READ_CONTACTS, "从电话薄中选择联系人,请允许读取权限!", 123)) {
            if (CommonUtils.queryContactPhoneNumber(mContext).size() > 0) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 1234);
            } else {
                ToastAlone.showLongToast(this, "请添加联系人或检查权限");
            }
        } else {
            if (AppOpsManagerUtil.isCheck(this, AppOpsManagerUtil.OP_READ_CONTACTS) == AppOpsManager.MODE_IGNORED) {
                ToastAlone.showLongToast(this, "请打开读取联系人权限!");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1234 == requestCode && resultCode == Activity.RESULT_OK) {
            String phoneNumber = "";
            if (data == null) {
                ToastAlone.showLongToast(this, "操作失败!");
            } else {
                Uri contactUri = data.getData();
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    phoneNumber = cursor.getString(numberIndex);
                }
                phoneNumber = validatePhoneNumber(phoneNumber);
                if (TextUtils.isEmpty(phoneNumber)) {
                    return;
                }
                switch (selectPhoneType) {
                    case 1:
                        etContactPhone1.setText(phoneNumber);
                        break;
                    case 2:
                        etContactPhone2.setText(phoneNumber);
                        break;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                jumpToSelectPhone(selectPhoneType);
            }
        }
        if (requestCode == 124) {
//            if (ToolPermission.checkPermission(permissions, grantResults)) {
//                if (CommonUtils.queryContactPhoneNumber(mContext).size() > 0) {
//                    if (contactList != null)
//                        contactList.contactList();
//                }
//            }

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


    private String validatePhoneNumber(String phone) {
        if (StringUtil.isNotEmpty(phone)) {
            if (phone.contains(" ")) {
                phone = phone.replace(" ", "").toString();
            }
            if (phone.contains("-")) {
                phone = phone.replace("-", "").toString();
            }
            if (phone.length() > 11) {
                phone = phone.substring(phone.length() - 11);
            }
            if (phone.length() != 11) {
                ToastAlone.showShortToast(this, "联系人电话不合法!");
                phone = "";
            }
            if (!(StringUtil.isMobile(phone))) {
                ToastAlone.showShortToast(this, "联系人电话不合法!");
                phone = "";
            }
            return phone;
        } else {
            return "";
        }
    }

}
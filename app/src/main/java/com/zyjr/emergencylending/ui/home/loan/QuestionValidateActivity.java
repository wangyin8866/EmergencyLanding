package com.zyjr.emergencylending.ui.home.loan;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.ui.home.View.QuestValidateView;
import com.zyjr.emergencylending.ui.home.presenter.QuestValidatePresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.permission.AppOpsManagerUtil;
import com.zyjr.emergencylending.utils.permission.ToolPermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 问题验证
 * 当业务员给客户录件时,填的联系电话为手填,
 * 所在客户在订单确认前,会走此步骤,验证问题，获取通讯录信息
 * 严并没有选中通讯录
 *
 * @author neil
 * @date 2017/11/6
 */
public class QuestionValidateActivity extends BaseActivity<QuestValidatePresenter, QuestValidateView> implements QuestValidateView {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.btn_contact1)
    Button btnContact1;  // 选中第一个联系人
    @BindView(R.id.btn_contact2)
    Button btnContact2; // 选中第二个联系人
    @BindView(R.id.iv_contact_phone)
    ImageView ivContactListChoose; // 通讯录号码选择
    @BindView(R.id.et_contact_phone)
    EditText etContactPhone;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry; // 网络加载失败时重试
    @BindView(R.id.sv_main)
    ScrollView svMain;  // 主布局

    private static final int CODE_PERMISSION_CONTANCT_LIST = 20000; // 权限请求 获取通讯录
    private static final int INTENT_SELECT_PHONE = 20001; // intent请求码 获取号码
    private ContactInfoBean contactBean1 = null; // 联系人1
    private ContactInfoBean contactBean2 = null; // 联系人2
    private ContactInfoBean selectedContactBean = null; // 选中的联系人

    @Override
    protected QuestValidatePresenter createPresenter() {
        return new QuestValidatePresenter(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_validate);
        ButterKnife.bind(this);

        init();
        loadingContactInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (INTENT_SELECT_PHONE == requestCode && resultCode == Activity.RESULT_OK) { // 从通讯录获取号码
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
                etContactPhone.setText(phoneNumber);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_PERMISSION_CONTANCT_LIST == requestCode) {
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                jumpToSelectPhone();
            }
        }
    }

    @OnClick({R.id.btn_contact1, R.id.btn_contact2, R.id.iv_contact_phone, R.id.btn_validate_quickly, R.id.btn_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contact1:
                selectedContactBean = contactBean1;
                btnContact1.setBackgroundResource(R.mipmap.contacts_choice_on);
                btnContact1.setTextColor(getResources().getColor(R.color.auth_success));
                btnContact2.setBackgroundResource(R.mipmap.contacts_choice_off);
                btnContact2.setTextColor(getResources().getColor(R.color.front_text_color_hint));
                break;

            case R.id.btn_contact2:
                selectedContactBean = contactBean2;
                btnContact2.setBackgroundResource(R.mipmap.contacts_choice_on);
                btnContact2.setTextColor(getResources().getColor(R.color.auth_success));
                btnContact1.setBackgroundResource(R.mipmap.contacts_choice_off);
                btnContact1.setTextColor(getResources().getColor(R.color.front_text_color_hint));
                break;

            case R.id.iv_contact_phone:
                jumpToSelectPhone();
                break;

            case R.id.btn_validate_quickly:
                validateData();
                break;

            case R.id.btn_retry:
                loadingContactInfo();
                break;
        }
    }

    private void validateData() {
        if (llContact.getVisibility() != View.VISIBLE) {
            ToastAlone.showLongToast(this, "获取联系人失败");
            return;
        }
        if (selectedContactBean == null) {
            ToastAlone.showLongToast(this, "请选择联系人");
            return;
        }
        String phoneNum = etContactPhone.getText().toString().trim();
        if (StringUtil.isEmpty(phoneNum)) {
            ToastAlone.showLongToast(this, "请选择手机号");
            return;
        }
        if (!phoneNum.equals(selectedContactBean.getContact_phone())) {
            ToastAlone.showLongToast(this, "联系人号码验证错误");
            return;
        } else {
            // TODO 保存通讯录信息
            saveContactsListInfo();
        }
    }

    private void init() {
        etContactPhone.setEnabled(false);
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

    private void jumpToSelectPhone() {
        if (ToolPermission.checkSelfPermission(this, null, Manifest.permission.READ_CONTACTS, "从电话薄中选择联系人,请允许读取权限!", CODE_PERMISSION_CONTANCT_LIST)) {
            if (CommonUtils.queryContactPhoneNumber(mContext).size() > 0) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, INTENT_SELECT_PHONE);
            } else {
                ToastAlone.showLongToast(this, "请添加联系人或检查权限");
            }
        } else {
            if (AppOpsManagerUtil.isCheck(this, AppOpsManagerUtil.OP_READ_CONTACTS) == AppOpsManager.MODE_IGNORED) {
                ToastAlone.showLongToast(this, "请打开读取联系人权限!");
            }
        }
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

    private void showContactInfo(List<ContactInfoBean> contactInfoBeanList) {
        if (contactInfoBeanList != null && contactInfoBeanList.size() > 0) {
            for (int i = 0; i < contactInfoBeanList.size(); i++) {
                switch (contactInfoBeanList.get(i).getFill_location()) {
                    case "1":
                        /**
                         * 联系人1
                         */
                        contactBean1 = contactInfoBeanList.get(i);
                        btnContact1.setText(contactBean1.getContact_name());
                        break;

                    case "2":
                        /**
                         * 联系人2
                         */
                        contactBean2 = contactInfoBeanList.get(i);
                        btnContact2.setText(contactBean2.getContact_name());
                        break;
                }
            }
        }
    }


    private void loadingContactInfo() {
        Map<String, String> paramMaps = new HashMap<>();
        mPresenter.getContactInfo(paramMaps);
    }

    private void saveContactsListInfo() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("contact_list", new Gson().toJson(CommonUtils.queryContactPhoneNumber(this))); // 通讯录集合
        mPresenter.saveContactsList(paramsMap);
    }

    private void showError() {
        llRetry.setVisibility(View.VISIBLE);
        svMain.setVisibility(View.GONE);
    }

    private void showSuccess() {
        llRetry.setVisibility(View.GONE);
        svMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessGetContactInfo(String apiCode, List<ContactInfoBean> contactInfoList) {
        showSuccess();
        llContact.setVisibility(View.VISIBLE);
        showContactInfo(contactInfoList);
    }

    @Override
    public void onSuccessSubmit(String apiCode, String msg) {
        Intent intent = new Intent(this, ReceiveMoneyActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFail(String apiCode, String failMsg) {
        ToastAlone.showLongToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        showError();
    }
}

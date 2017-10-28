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

import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.config.AppConfig;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.ui.home.View.ContactInfoView;
import com.zyjr.emergencylending.ui.home.presenter.ContactInfoPresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.permission.AppOpsManagerUtil;
import com.zyjr.emergencylending.utils.permission.ToolPermission;
import com.zyjr.emergencylending.widget.pop.SingleSelectPop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 联系人信息
 * 普通用户从手机通讯录中选择号码,业务员需要手输号码 【联调时注意】
 */
public class ContactInfoActivity extends BaseActivity<ContactInfoPresenter, ContactInfoView> implements ContactInfoView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.et_contact_name1)
    ClearEditText etContactName1; // 联系人姓名1
    @BindView(R.id.et_contact_phone1)
    EditText etContactPhone1; // 联系人姓名1
    @BindView(R.id.iv_contact_phone1)
    ImageView ivContactPhone1;
    @BindView(R.id.tv_relation1)
    TextView tvRelation1; // 关系1
    @BindView(R.id.et_contact_name2)
    ClearEditText etContactName2; // 联系人姓名2
    @BindView(R.id.et_contact_phone2)
    EditText etContactPhone2; // 联系人电话2
    @BindView(R.id.iv_contact_phone2)
    ImageView ivContactPhone2;
    @BindView(R.id.tv_relation2)
    TextView tvRelation2; // 关系2

    List<CodeBean> selectList; // 初始化选择列表
    List<CodeBean> selectList1 = new ArrayList<>(); // 联系人1可选择的
    CodeBean relationCodeBean1 = null; // 关系1
    List<CodeBean> selectList2 = new ArrayList<>(); // 联系人2可选择的
    CodeBean relationCodeBean2 = null; // 关系2
    private int selectPhoneType = 0; // 选择号码标识
    private static final int CODE_PERMISSION_CONTANCT_LIST = 20000; // 权限请求 获取通讯录
    private static final int INTENT_SELECT_PHONE = 20001; // intent请求码 获取号码
    private ContactInfoBean contactInfoBean = null;

    @Override
    protected ContactInfoPresenter createPresenter() {
        return new ContactInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        ButterKnife.bind(this);
        init();
    }

    @OnClick({R.id.iv_contact_phone1, R.id.ll_contact_relation1, R.id.iv_contact_phone2, R.id.ll_contact_relation2, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_contact_relation1:  // 关系1
                SingleSelectPop popRelationSelect1 = new SingleSelectPop(this, selectList1.size() > 0 ? selectList1 : selectList);
                popRelationSelect1.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select1) {
                        relationCodeBean1 = select1;
                        LogUtils.d("选择的关系信息1:" + select1.toString());
                        tvRelation1.setText(select1.getName());
                        selectList2 = AppConfig.removeSlectCodeBean(selectList, relationCodeBean1.getCode());
                    }
                });
                popRelationSelect1.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_contact_relation2:  // 关系2
                SingleSelectPop popRelationSelect2 = new SingleSelectPop(this, selectList2.size() > 0 ? selectList2 : selectList);
                popRelationSelect2.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select2) {
                        relationCodeBean2 = select2;
                        LogUtils.d("选择的关系信息2:" + select2.toString());
                        tvRelation2.setText(select2.getName());
                        selectList1 = AppConfig.removeSlectCodeBean(selectList, relationCodeBean2.getCode());
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

            case R.id.btn_submit:
                validateData();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseApplication.isSalesman) {
            // 线下业务员
            etContactPhone1.setEnabled(true);
            etContactPhone1.setHint("请填写手机号");
            ivContactPhone1.setVisibility(View.INVISIBLE);
            etContactPhone2.setEnabled(true);
            etContactPhone2.setHint("请填写手机号");
            ivContactPhone2.setVisibility(View.INVISIBLE);
        } else {
            etContactPhone1.setEnabled(false);
            etContactPhone2.setEnabled(false);
        }
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
        if (CODE_PERMISSION_CONTANCT_LIST == requestCode) {
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                jumpToSelectPhone(selectPhoneType);
            }
        }
    }

    private void validateData() {
        String contactName1 = etContactName1.getText().toString().trim(); // 联系人姓名1
        String contactPhone1 = etContactPhone1.getText().toString().trim(); // 联系电话1
        String relation1 = tvRelation1.getText().toString().trim(); // 关系1
        String contactName2 = etContactName2.getText().toString().trim(); // 联系人姓名2
        String contactPhone2 = etContactPhone2.getText().toString().trim(); // 联系电话2
        String relation2 = tvRelation2.getText().toString().trim(); // 关系2
        // TODO 上传通讯录信息
        if (StringUtil.isEmpty(contactName1)) {
            ToastAlone.showLongToast(this, "请填写联系人信息!");
            return;
        }
        if (StringUtil.isEmpty(contactPhone1)) {
            ToastAlone.showLongToast(this, "请选择联系人电话!");
            return;
        }
        if (StringUtil.isEmpty(relation1) && relationCodeBean1 == null) {
            ToastAlone.showLongToast(this, "请选择“关系1”联系人!");
            return;
        }
        if (StringUtil.isEmpty(contactName2)) {
            ToastAlone.showLongToast(this, "请填写联系人信息!");
            return;
        }
        if (StringUtil.isEmpty(contactPhone2)) {
            ToastAlone.showLongToast(this, "请选择联系人电话!");
            return;
        }
        if (StringUtil.isEmpty(relation2) && relationCodeBean2 == null) {
            ToastAlone.showLongToast(this, "请选择“关系2”联系人!");
            return;
        }
        contactInfoBean = new ContactInfoBean();
        List<ContactInfoBean> contactsList = new ArrayList<>();
        ContactInfoBean contactBean1 = new ContactInfoBean();
        contactBean1.setContact_name(contactName1.trim());
        contactBean1.setContact_phone(contactPhone1.trim());
        contactBean1.setRelation(relationCodeBean1.getCode());
        contactsList.add(contactBean1);
        ContactInfoBean contactBean2 = new ContactInfoBean();
        contactBean2.setContact_name(contactName2.trim());
        contactBean2.setContact_phone(contactPhone2.trim());
        contactBean2.setRelation(relationCodeBean2.getCode());
        contactsList.add(contactBean2);
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("contactList", new Gson().toJson(contactsList));
        LogUtils.d("参数明细:" + new Gson().toJson(paramsMap));
        LogUtils.d("参数数量:" + paramsMap.size());
        paramsMap.put("juid", "");
        paramsMap.put("cust_juid", "");
        paramsMap.put("login_token", "");
        mPresenter.addContactInfo(paramsMap);
    }

    private void submitContacts() {
        Map<String, String> params = new HashMap<>();
        params.put("contact_list", new Gson().toJson(CommonUtils.queryContactPhoneNumber(this)));
        params.put("juid", "");
        params.put("cust_juid", "");
        params.put("login_token", "");
        mPresenter.submitContacts(params);
    }

    private void showContactInfo(List<ContactInfoBean> contactInfoBeanList) {
        if (contactInfoBeanList != null && contactInfoBeanList.size() > 0) {
            /**
             * 联系人1
             */
            ContactInfoBean contactBean1 = contactInfoBeanList.get(0);
            if (StringUtil.isNotEmpty(contactBean1.getContact_name())) {
                // 姓名1
                etContactName1.setText(contactBean1.getContact_name());
            }
            if (StringUtil.isNotEmpty(contactBean1.getContact_phone())) {
                // 手机号1
                etContactPhone1.setText(contactBean1.getContact_phone());
            }
            if (StringUtil.isNotEmpty(contactBean1.getRelation())) {
                // 关系1
                int index = AppConfig.getWorkInfo().indexOf(new CodeBean(0, contactBean1.getRelation(), ""));
                if (index != -1) {
                    relationCodeBean1 = AppConfig.contactRelation().get(index);
                    tvRelation1.setText(relationCodeBean1.getName());
                    selectList2 = AppConfig.removeSlectCodeBean(selectList, relationCodeBean1.getCode());
                } else {
                    tvRelation1.setText("");
                }
            }
            /**
             * 联系人2
             */
            ContactInfoBean contactBean2 = contactInfoBeanList.get(1);
            if (StringUtil.isNotEmpty(contactBean2.getContact_name())) {
                // 姓名2
                etContactName1.setText(contactBean2.getContact_name());
            }
            if (StringUtil.isNotEmpty(contactBean2.getContact_phone())) {
                // 手机号2
                etContactPhone1.setText(contactBean2.getContact_phone());
            }
            if (StringUtil.isNotEmpty(contactBean2.getRelation())) {
                // 关系2
                int index = AppConfig.getWorkInfo().indexOf(new CodeBean(0, contactBean2.getRelation(), ""));
                if (index != -1) {
                    relationCodeBean2 = AppConfig.contactRelation().get(index);
                    tvRelation2.setText(relationCodeBean2.getName());
                    selectList1 = AppConfig.removeSlectCodeBean(selectList, relationCodeBean2.getCode());
                } else {
                    tvRelation2.setText("");
                }
            }
        }

    }


    private void init() {
        selectList = AppConfig.contactRelation(); // 初始化联系人关系选择
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

    private void jumpToSelectPhone(int i) {
        selectPhoneType = i;
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

    @Override
    public void onSuccessGet(String returnCode, List<ContactInfoBean> beanList) {
        LogUtils.d("获取联系人信息成功---->" + returnCode + ",List<ContactInfoBean>:" + beanList.toString());
    }

    @Override
    public void onSuccessAdd(String returnCode, List<ContactInfoBean> beanList) {
        LogUtils.d("添加联系人信息成功---->" + returnCode + ",List<ContactInfoBean>:" + beanList.toString());
    }

    @Override
    public void onSuccessEdit(String returnCode, List<ContactInfoBean> beanList) {
        LogUtils.d("修改联系人信息成功---->" + returnCode + ",List<ContactInfoBean>:" + beanList.toString());
    }

    @Override
    public void onFail(String returnCode, String errorMsg) {

    }

    @Override
    public void onError(String returnCode, String errorMsg) {

    }


}

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.config.AppConfig;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.ui.home.View.ContactInfoView;
import com.zyjr.emergencylending.ui.home.presenter.ContactInfoPresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;
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
    @BindView(R.id.ll_cover)
    LinearLayout llCover;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry; // 网络加载失败时重试
    @BindView(R.id.sv_main)
    ScrollView svMain;  // 主布局
    @BindView(R.id.layout_bottom)
    RelativeLayout layoutBottom;  // 底部布局

    CodeBean relationCodeBean1 = null; // 关系1
    CodeBean relationCodeBean2 = null; // 关系2
    private int selectPhoneType = 0; // 选择号码标识
    private static final int CODE_PERMISSION_CONTANCT_LIST = 20000; // 权限请求 获取通讯录
    private static final int INTENT_SELECT_PHONE = 20001; // intent请求码 获取号码
    private ContactInfoBean contactInfoBean = null;
    private String isEdit = ""; // 1,可编辑;0,不可编辑
    private String status = ""; // 1,完成;0,未完成
    private String marriageCode = ""; // 结婚状态 801:未婚; 802:已婚; 806:离婚; 805:丧偶

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
        initGetData();
    }

    @OnClick({R.id.iv_contact_phone1, R.id.ll_contact_relation1, R.id.iv_contact_phone2, R.id.ll_contact_relation2, R.id.btn_submit, R.id.btn_retry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_contact_relation1:  // 关系1
                SingleSelectPop popRelationSelect1 = new SingleSelectPop(this, removeSlectCodeBean(AppConfig.getFirstContacts(marriageCode), tvRelation2.getText().toString().trim()));
                popRelationSelect1.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select1) {
                        relationCodeBean1 = select1;
                        LogUtils.d("选择的关系信息1:" + relationCodeBean1.toString());
                        tvRelation1.setText(relationCodeBean1.getName());
                    }
                });
                popRelationSelect1.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_contact_relation2:  // 关系2
                SingleSelectPop popRelationSelect2 = new SingleSelectPop(this, removeSlectCodeBean(AppConfig.getSecondContacts(marriageCode), tvRelation1.getText().toString().trim()));
                popRelationSelect2.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select2) {
                        relationCodeBean2 = select2;
                        LogUtils.d("选择的关系信息2:" + relationCodeBean2.toString());
                        tvRelation2.setText(relationCodeBean2.getName());
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

            case R.id.btn_retry:
                defaultLoading();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
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
        if (StringUtil.isEmpty(relation1) || relationCodeBean1 == null) {
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
        if (StringUtil.isEmpty(relation2) || relationCodeBean2 == null) {
            ToastAlone.showLongToast(this, "请选择“关系2”联系人!");
            return;
        }
        if (contactPhone1.equals(contactPhone2)) {
            ToastAlone.showLongToast(this, "联系人手机号不能相同!");
            return;
        }
        if (contactInfoBean == null) {
            contactInfoBean = new ContactInfoBean();
        }
        List<ContactInfoBean> contactsList = new ArrayList<>();
        ContactInfoBean contactBean1 = new ContactInfoBean();
        contactBean1.setContact_name(contactName1.trim());
        contactBean1.setContact_phone(contactPhone1.trim());
        contactBean1.setRelation(relationCodeBean1.getCode());
        contactBean1.setFill_location("1");
        contactsList.add(contactBean1);
        ContactInfoBean contactBean2 = new ContactInfoBean();
        contactBean2.setContact_name(contactName2.trim());
        contactBean2.setContact_phone(contactPhone2.trim());
        contactBean2.setRelation(relationCodeBean2.getCode());
        contactBean2.setFill_location("2");
        contactsList.add(contactBean2);
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("contactList", new Gson().toJson(contactsList));
        LogUtils.d("参数明细:" + new Gson().toJson(paramsMap));
        LogUtils.d("参数数量:" + paramsMap.size());
        mPresenter.addContactInfo(paramsMap);
    }

    private void showContactInfo(List<ContactInfoBean> contactInfoBeanList) {
        if (contactInfoBeanList != null && contactInfoBeanList.size() > 0) {
            for (int i = 0; i < contactInfoBeanList.size(); i++) {
                switch (contactInfoBeanList.get(i).getFill_location()) {
                    case "1":
                        /**
                         * 联系人1
                         */
                        ContactInfoBean contactBean1 = contactInfoBeanList.get(i);
                        if (StringUtil.isNotEmpty(contactBean1.getContact_name())) {
                            // 姓名1
                            etContactName1.setText(contactBean1.getContact_name());
                        }
                        if (StringUtil.isNotEmpty(contactBean1.getContact_phone())) {
                            // 手机号1
                            etContactPhone1.setText(contactBean1.getContact_phone());
                        }
                        if (StringUtil.isNotEmpty(contactBean1.getRelation())) {
                            if (StringUtil.isEmpty(marriageCode)) {
                                tvRelation1.setText(contactBean1.getRelation());
                            } else {
                                String cont1 = AppConfig.getCodeName(1, contactBean1.getRelation(), marriageCode);
                                if (StringUtil.isEmpty(cont1)) {
                                    tvRelation1.setText("");
                                } else {
                                    tvRelation1.setText(cont1);
                                    // 关系1
                                    int index = AppConfig.allContactRelation().indexOf(new CodeBean(0, contactBean1.getRelation(), ""));
                                    if (index != -1) {
                                        relationCodeBean1 = AppConfig.allContactRelation().get(index);
                                        LogUtils.d("获取填写关系1信息:" + relationCodeBean1.toString());
                                    }
                                }
                            }
                        }
                        break;

                    case "2":
                        /**
                         * 联系人2
                         */
                        ContactInfoBean contactBean2 = contactInfoBeanList.get(i);
                        if (StringUtil.isNotEmpty(contactBean2.getContact_name())) {
                            // 姓名2
                            etContactName2.setText(contactBean2.getContact_name());
                        }
                        if (StringUtil.isNotEmpty(contactBean2.getContact_phone())) {
                            // 手机号2
                            etContactPhone2.setText(contactBean2.getContact_phone());
                        }
                        if (StringUtil.isNotEmpty(contactBean2.getRelation())) {
                            if (StringUtil.isEmpty(marriageCode)) {
                                tvRelation2.setText(contactBean2.getRelation());
                            } else {
                                String cont1 = AppConfig.getCodeName(2, contactBean2.getRelation(), marriageCode);
                                if (StringUtil.isEmpty(cont1)) {
                                    tvRelation2.setText("");
                                } else {
                                    tvRelation2.setText(cont1);
                                    // 关系2
                                    int index = AppConfig.allContactRelation().indexOf(new CodeBean(0, contactBean2.getRelation(), ""));
                                    if (index != -1) {
                                        relationCodeBean2 = AppConfig.allContactRelation().get(index);
                                        LogUtils.d("获取填写关系2信息:" + relationCodeBean2.toString());
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }
    }


    private void init() {
//        selectList = AppConfig.contactRelation(); // 初始化联系人关系选择
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });

        // 添加联系人资料

    }

    private void initGetData() {
        Intent intent = getIntent();
        isEdit = intent.getStringExtra("isEdit");
        status = intent.getStringExtra("status");
        defaultLoading();
    }

    private void defaultLoading() {
        if (StringUtil.isNotEmpty(isEdit) && isEdit.equals("0")) { // 不可编辑
            WYUtils.coverPage(false, llCover);
            btnSubmit.setEnabled(false);
            getContactInfo();
        } else {
            getPersonInfo();
        }
    }


    private void getContactInfo() {
        Map<String, String> paramMaps = new HashMap<>();
        mPresenter.getContantInfo(paramMaps);
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

    /**
     * 清除选择的
     */
    private List<CodeBean> removeSlectCodeBean(List<CodeBean> list, String msg) {
        List<CodeBean> empCodeBeans = new ArrayList<>();
        for (CodeBean mCodeBean : list) {
            if (!mCodeBean.getName().equals(msg)) {
                empCodeBeans.add(mCodeBean);
            }
        }
        return empCodeBeans;
    }


    private void getPersonInfo() {
        Map<String, String> paramMaps = new HashMap<>();
        mPresenter.getPersonalInfo(paramMaps);
    }

    @Override
    public void onSuccessGet(String returnCode, List<ContactInfoBean> beanList) {
        showSuccess();
        showContactInfo(beanList);
    }

    @Override
    public void onSuccessAdd(String returnCode, String msg) {
        ToastAlone.showLongToast(this, msg);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSuccessEdit(String returnCode, String msg) {
        ToastAlone.showLongToast(this, msg);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFail(String returnCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
        showError();
    }

    @Override
    public void onSuccessGetPersonInfo(String apiCode, PersonalInfoBean bean) {
        marriageCode = bean.getMarriage();
        if (StringUtil.isNotEmpty(status) && status.equals("1")) {
            // 已完成资料时,先获取用户信息(获取婚姻状态),在获取联系人资料
            getContactInfo();
        }
    }

    private void showError() {
        llRetry.setVisibility(View.VISIBLE);
        svMain.setVisibility(View.GONE);
        layoutBottom.setVisibility(View.GONE);
    }

    private void showSuccess() {
        llRetry.setVisibility(View.GONE);
        svMain.setVisibility(View.VISIBLE);
        layoutBottom.setVisibility(View.VISIBLE);
    }
}

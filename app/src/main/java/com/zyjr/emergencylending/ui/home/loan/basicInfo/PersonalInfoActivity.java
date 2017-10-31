package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.config.AppConfig;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.CityModel;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.DistrictModel;
import com.zyjr.emergencylending.entity.HujiAddressBean;
import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.entity.LiveAddressBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.entity.ProvinceModel;
import com.zyjr.emergencylending.ui.home.View.PersonalInfoView;
import com.zyjr.emergencylending.ui.home.presenter.PersonalInfoPresenter;
import com.zyjr.emergencylending.utils.AppToast;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ReflectionUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.ImageUtils;
import com.zyjr.emergencylending.utils.WYUtils;
import com.zyjr.emergencylending.utils.permission.ToolPermission;
import com.zyjr.emergencylending.utils.third.GlideUtils;
import com.zyjr.emergencylending.utils.third.IdcardUtils;
import com.zyjr.emergencylending.widget.pop.AreaSelectPop;
import com.zyjr.emergencylending.widget.pop.SingleSelectPop;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zyjr.emergencylending.utils.CommonUtils.provinceList;

/**
 * Created by neil on 2017/10/12
 * 备注: 个人信息/资料
 * 根据状态判断 是否可编辑修改
 */
public class PersonalInfoActivity extends BaseActivity<PersonalInfoPresenter, PersonalInfoView> implements TakePhoto.TakeResultListener, PersonalInfoView {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.layout_root)
    RelativeLayout layoutRoot;
    @BindView(R.id.iv_idcard_front)
    ImageView ivIdcardFront;
    private String idcardFrontPath = "";  // 身份证正面照路径
    @BindView(R.id.iv_idcard_back)
    ImageView ivIdcardBack;
    private String idcardBackPath = "";   // 身份证正面照路径
    @BindView(R.id.iv_idcard_hold)
    ImageView ivHoldIdcard;
    private String holdIdcardPath = ""; // 手持身份证照片路径
    @BindView(R.id.tv_personal_name)
    TextView tvPersonalName; // 姓名
    @BindView(R.id.tv_personal_idcard)
    TextView tvPersonalIdcard; // 身份证
    @BindView(R.id.tv_marriage_status)
    TextView tvMarriageStatus; // 婚姻状态
    @BindView(R.id.tv_live_status)
    TextView tvLiveStatus;  // 居住状态
    @BindView(R.id.tv_huji_address)
    TextView tvHujiAddress;  // 户籍地址
    @BindView(R.id.tv_huji_detail_address)
    TextView tvHujiDetailAddress;  // 户籍详细地址
    @BindView(R.id.tv_live_address)
    TextView tvLiveAddress;  // 居住地址
    @BindView(R.id.et_live_detail_address)
    ClearEditText etLiveDetailAddress; // 居住详细地址
    @BindView(R.id.ll_cover)
    LinearLayout llCover;

    private File idcardFile;
    private File holdCardFile;
    private TakePhoto takePhoto;
    private String filePath;
    private Bitmap mBitmapHoldIdcard, mBitmapIDcardFront, mBitmapIDcardBack;
    private static final int INTENT_IDCARD_FRONT = 10000; // 请求码 扫描身份证正面
    private static final int INTENT_IDCARD_BACK = 10001; // 请求码 扫描身份证反面
    private static final int INTENT_IDCARD_HOLD = 10002; // 请求码 拍照
    private int mWidth;
    private int mHeight;
    private String isEdit = "1"; // 1,可编辑;0,不可编辑。默认可编辑
    private CodeBean marriageCodeBean = null; // 婚姻状态codeBean
    private CodeBean liveCodeBean = null; // 居住状态codeBean
    private LiveAddressBean liveAddressBean = null; // 居住地址bean
    private HujiAddressBean hujiAddressBean = null; // 户籍地址bean
    private IDCardFrontBean idCardFrontBean = null; // 身份证正面bean
    private PersonalInfoBean personalInfoBean = null; // 提交表单bean

    @Override
    protected PersonalInfoPresenter createPresenter() {
        return new PersonalInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        init();
        initGetData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data); // 手持照片拍照
        if ((requestCode == INTENT_IDCARD_FRONT || requestCode == INTENT_IDCARD_BACK) && resultCode == RESULT_OK) { // 身份证扫描
            Map map = new HashMap<>();
            map.put("side", data.getIntExtra("side", 0));
            map.put("idcardImg", data.getByteArrayExtra("idcardImg"));
            if (data.getIntExtra("side", 0) == 0) {
                map.put("portraitImg", data.getByteArrayExtra("portraitImg"));
            }
            LogUtils.d("身份证扫描信息:" + new Gson().toJson(map));
            Bitmap bitmap = IdcardUtils.getInstance().gitBitmap(map);
            idcardFile = new File(Environment.getExternalStorageDirectory().getPath() + "/myIdCard/");
            if (!idcardFile.exists() && !idcardFile.isDirectory()) {
                idcardFile.mkdirs();
            }
            idcardFile = new File(idcardFile.getPath() + "/" + System.currentTimeMillis() + ".jpg");
            ImageUtils.compressBitmapToFile(bitmap, Bitmap.CompressFormat.JPEG, 100, idcardFile); // 保存文件
            if (requestCode == INTENT_IDCARD_FRONT) {
                mPresenter.uploadFileGetIDCardFrontInfo(idcardFile);
            } else if (requestCode == INTENT_IDCARD_BACK) {
                mPresenter.uploadFileGetIDCardBackInfo(idcardFile);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == INTENT_IDCARD_HOLD) { // 手持照
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                jumpToTakePhoto(INTENT_IDCARD_HOLD);
            } else {
                AppToast.makeToast(PersonalInfoActivity.this, "相机权限被拒绝");
            }
        } else if (requestCode == INTENT_IDCARD_FRONT) { // 扫描正面
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                jumpScanIDcard(INTENT_IDCARD_FRONT, 0, false);
            } else {
                AppToast.makeToast(PersonalInfoActivity.this, "拍照权限被拒绝");
            }
        } else if (requestCode == INTENT_IDCARD_BACK) {
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                jumpScanIDcard(INTENT_IDCARD_BACK, 1, false);
            } else {
                AppToast.makeToast(PersonalInfoActivity.this, "拍照权限被拒绝");
            }
        }
    }

    @OnClick({R.id.ll_idcard_front, R.id.ll_idcard_back, R.id.ll_idcard_hold, R.id.ll_marriage_status, R.id.ll_live_status, R.id.ll_huji_address, R.id.ll_live_address, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_idcard_front: // 身份证正面
                jumpScanIDcard(INTENT_IDCARD_FRONT, 0, false);
                break;

            case R.id.ll_idcard_back: // 身份证背面扫描
                jumpScanIDcard(INTENT_IDCARD_BACK, 1, false);
                break;

            case R.id.ll_idcard_hold: // 手持证件照
                takePhotoModelNotice();
                break;
            case R.id.ll_marriage_status: // 婚姻状况选择
                SingleSelectPop popMarriageStatusSelect = new SingleSelectPop(this, AppConfig.marriageStatus());
                popMarriageStatusSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        marriageCodeBean = select;
                        tvMarriageStatus.setText(select.getName());
                        LogUtils.d("婚姻状况选择:" + marriageCodeBean.toString());
                    }
                });
                popMarriageStatusSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_live_status: // 居住状况选择
                SingleSelectPop popLiveStatusSelect = new SingleSelectPop(this, AppConfig.liveStatus());
                popLiveStatusSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        liveCodeBean = select;
                        tvLiveStatus.setText(select.getName());
                        LogUtils.d("居住状况选择:" + liveCodeBean.toString());
                    }
                });
                popLiveStatusSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_huji_address: // 户籍地址选择
                AreaSelectPop popHujiAddressSelect = new AreaSelectPop(this, CommonUtils.mProvinceDatas, CommonUtils.mCitisDatasMap, CommonUtils.mDistrictDatasMap);
                popHujiAddressSelect.setOnCityPopupWindow(new AreaSelectPop.OnCityPopupWindow() {
                    @Override
                    public void onCityClick(String province, int privinceItem, String city, int cityItem, String district, int districtItem) {
                        tvHujiAddress.setText(province + "," + city + "," + district);
                        hujiAddressBean = new HujiAddressBean(); //  实例化bean
                        ProvinceModel pCode = null;
                        CityModel cityode = null;
                        DistrictModel dCode = null;
                        pCode = provinceList.get(privinceItem);
                        cityode = pCode.getCityList().get(cityItem);
                        dCode = cityode.getDistrictList().get(districtItem);
                        hujiAddressBean.setHuji_province(pCode.getProvinceCode());
                        hujiAddressBean.setHuji_province_name(pCode.getName());
                        hujiAddressBean.setHuji_city(cityode.getCityCode());
                        hujiAddressBean.setHuji_city_name(cityode.getName());
                        hujiAddressBean.setHuji_county(dCode.getZipcode());
                        hujiAddressBean.setHuji_county_name(dCode.getName());
                        hujiAddressBean.setHuji_adr(pCode.getProvinceCode() + "," + cityode.getCityCode() + "," + dCode.getZipcode());
                        LogUtils.d("户籍地址选择:" + hujiAddressBean.toString());
                    }
                });
                popHujiAddressSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_live_address: // 居住地址选择
                AreaSelectPop popLiveAddressSelect = new AreaSelectPop(this, CommonUtils.mProvinceDatas, CommonUtils.mCitisDatasMap, CommonUtils.mDistrictDatasMap);
                popLiveAddressSelect.setOnCityPopupWindow(new AreaSelectPop.OnCityPopupWindow() {
                    @Override
                    public void onCityClick(String province, int privinceItem, String city, int cityItem, String district, int districtItem) {
                        tvLiveAddress.setText(province + "," + city + "," + district);
                        liveAddressBean = new LiveAddressBean(); //  实例化bean
                        ProvinceModel pCode = null;
                        CityModel cityode = null;
                        DistrictModel dCode = null;
                        pCode = provinceList.get(privinceItem);
                        cityode = pCode.getCityList().get(cityItem);
                        dCode = cityode.getDistrictList().get(districtItem);
                        liveAddressBean.setLive_province(pCode.getProvinceCode());
                        liveAddressBean.setLive_province_name(pCode.getName());
                        liveAddressBean.setLive_city(cityode.getCityCode());
                        liveAddressBean.setLive_city_name(cityode.getName());
                        liveAddressBean.setLive_county(dCode.getZipcode());
                        liveAddressBean.setLive_county_name(dCode.getName());
                        liveAddressBean.setLive_adr(pCode.getProvinceCode() + "," + cityode.getCityCode() + "," + dCode.getZipcode());
                        LogUtils.d("居住地址选择:" + liveAddressBean.toString());

                    }
                });
                popLiveAddressSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_submit: // 提交
                // TODO 信息提交
                validateData();
                break;
        }
    }

    /**
     * 接受 传递数据,根据标识判断是新添加资料还是编辑资料
     */
    private void initGetData() {
        Intent intent = getIntent();
        isEdit = intent.getStringExtra("isEdit");
        String status = intent.getStringExtra("status");
        // 已完成状态获取资料信息
        if (StringUtil.isNotEmpty(status) && status.equals("1")) {
            loadingPersonInfo();
        }
        if (isEdit.equals("0")) { // 不可编辑
            WYUtils.coverPage(false, llCover);
        }
    }


    private void validateData() {
        String personalName = tvPersonalName.getText().toString().trim(); // 姓名
        String personalIdcard = tvPersonalIdcard.getText().toString().trim(); // 身份证
        String marriageStatus = tvMarriageStatus.getText().toString().trim(); // 婚姻状态
        String liveStatus = tvLiveStatus.getText().toString().trim(); // 居住状态
        String hujiAddress = tvHujiAddress.getText().toString().trim(); // 户籍地址
        String hujiDetailAddress = tvHujiDetailAddress.getText().toString().trim(); // 户籍详细地址(根据证件上地址来的,不可更改)
        String liveAddress = tvLiveAddress.getText().toString().trim(); // 居住地址
        String liveDetailAddress = etLiveDetailAddress.getText().toString().trim(); // 居住详细地址
//        if (StringUtil.isEmpty(personalName) || StringUtil.isEmpty(personalIdcard) || StringUtil.isEmpty(hujiDetailAddress)) {
//            ToastAlone.showLongToast(this, "请拍照扫描身份证!");
//            return;
//        }
        if (StringUtil.isEmpty(marriageStatus) && marriageCodeBean == null) {
            ToastAlone.showLongToast(this, "请选择婚姻关系!");
            return;
        }
        if (StringUtil.isEmpty(liveStatus) && liveCodeBean == null) {
            ToastAlone.showLongToast(this, "请选择居住状态!");
            return;
        }
        if (StringUtil.isEmpty(hujiAddress) && hujiAddressBean == null) {
            ToastAlone.showLongToast(this, "请选择户籍地址!");
            return;
        } else {
            if (hujiAddressBean != null && (StringUtil.containChinese(hujiAddressBean.getHuji_province())
                    || StringUtil.containChinese(hujiAddressBean.getHuji_city()))
                    || StringUtil.containChinese(hujiAddressBean.getHuji_county())) {
                ToastAlone.showLongToast(this, "请重新选择户籍地址!");
                return;
            }
        }
        if (StringUtil.isEmpty(liveAddress) && liveAddressBean == null) {
            ToastAlone.showLongToast(this, "请选择居住地址!");
            return;
        } else {
            if (liveAddressBean != null && (StringUtil.containChinese(liveAddressBean.getLive_province())
                    || StringUtil.containChinese(liveAddressBean.getLive_city()))
                    || StringUtil.containChinese(liveAddressBean.getLive_county())) {
                ToastAlone.showLongToast(this, "请重新选择居住地址!");
                return;
            }
        }
//        if (StringUtil.isEmpty(liveDetailAddress)) {
//            ToastAlone.showLongToast(this, "请填写现居住详细地址!");
//            return;
//        }
        if (personalInfoBean == null) {
            personalInfoBean = new PersonalInfoBean();
        }
        personalInfoBean.setUsername(personalName); // 用户姓名
        personalInfoBean.setIdCard(personalIdcard); // 用户身份证
        personalInfoBean.setMarriage(marriageCodeBean.getCode()); // 婚姻状况
        personalInfoBean.setHuji_adr(hujiAddressBean.getHuji_adr()); // 户籍省市区
        personalInfoBean.setHuji_province(hujiAddressBean.getHuji_province()); // 户籍省编码
        personalInfoBean.setHuji_province_name(hujiAddressBean.getHuji_province_name());
        personalInfoBean.setHuji_city(hujiAddressBean.getHuji_city()); // 户籍所在市
        personalInfoBean.setHuji_city_name(hujiAddressBean.getHuji_city_name());
        personalInfoBean.setHuji_county(hujiAddressBean.getHuji_county()); // 户籍所在区
        personalInfoBean.setHuji_county_name(hujiAddressBean.getHuji_county_name());
        personalInfoBean.setHuji_adr_detail(hujiDetailAddress); // 户籍详细地址
        personalInfoBean.setLive_status(liveCodeBean.getCode()); // 居住状况
        personalInfoBean.setLive_adr(liveAddressBean.getLive_adr()); // 居住地省市区
        personalInfoBean.setLive_province(liveAddressBean.getLive_province()); // 居住省编码
        personalInfoBean.setLive_province_name(liveAddressBean.getLive_province_name());
        personalInfoBean.setLive_city(liveAddressBean.getLive_city()); // 居住所在市
        personalInfoBean.setLive_city_name(liveAddressBean.getLive_city_name());
        personalInfoBean.setLive_county(liveAddressBean.getLive_county()); // 居住所在区
        personalInfoBean.setLive_county_name(liveAddressBean.getLive_county_name());
        personalInfoBean.setLive_adr_detail(liveDetailAddress); // 居住详细地址
        Map<String, String> paramsMap = ReflectionUtils.beanToMap(personalInfoBean);
        LogUtils.d("参数明细:" + new Gson().toJson(paramsMap));
        LogUtils.d("参数数量:" + paramsMap.size());
        mPresenter.addPersonalInfo(paramsMap);
    }

    /**
     * 用户下次进入时数据信息
     */
    private void showUserInfo(PersonalInfoBean userInfo) {
        if (StringUtil.isNotEmpty(userInfo.getIdcard_z())) {
            // 身份证正面照
            GlideUtils.displayImageWithFixedSize(this, userInfo.getIdcard_z(), R.mipmap.shotcard_positive, ivIdcardFront, mWidth, mHeight);
        }
        if (StringUtil.isNotEmpty(userInfo.getIdcard_f())) {
            // 身份证反面照
            GlideUtils.displayImageWithFixedSize(this, userInfo.getIdcard_f(), R.mipmap.shotcard_back, ivIdcardBack, mWidth, mHeight);
        }
        if (StringUtil.isNotEmpty(userInfo.getIdcard_hand())) {
            // 手持证件照
            GlideUtils.displayImageWithFixedSize(this, userInfo.getIdcard_hand(), R.mipmap.shotcard_back, ivHoldIdcard, mWidth, mHeight);
        }
        if (StringUtil.isNotEmpty(userInfo.getUsername())) {
            // 姓名
            tvPersonalName.setText(userInfo.getUsername());
        }
        if (StringUtil.isNotEmpty(userInfo.getIdCard())) {
            // 身份证
            tvPersonalIdcard.setText(userInfo.getIdCard());
        }
        if (StringUtil.isNotEmpty(userInfo.getMarriage())) {
            // 婚姻状态
            int index = AppConfig.marriageStatus().indexOf(new CodeBean(0, userInfo.getMarriage(), ""));
            if (index != -1) {
                marriageCodeBean = AppConfig.marriageStatus().get(index);
                LogUtils.d("获取婚姻状态:" + marriageCodeBean.toString());
                tvMarriageStatus.setText(marriageCodeBean.getName());
            } else {
                tvMarriageStatus.setText("");
            }
        }
        if (StringUtil.isNotEmpty(userInfo.getLive_status())) {
            // 居住状态
            int index = AppConfig.liveStatus().indexOf(new CodeBean(0, userInfo.getLive_status(), ""));
            if (index != -1) {
                liveCodeBean = AppConfig.liveStatus().get(index);
                LogUtils.d("获取居住状态:" + liveCodeBean.toString());
                tvLiveStatus.setText(liveCodeBean.getName());
            } else {
                tvLiveStatus.setText("");
            }
        }
        if (StringUtil.isNotEmpty(userInfo.getHuji_adr())) {
            // 户籍地址
            try {
                String[] code = userInfo.getHuji_adr().split(",");
                if (code != null && code.length == 3) {
                    ProvinceModel p = null;
                    CityModel c = null;
                    DistrictModel d = null;
                    if (provinceList.indexOf(new ProvinceModel(code[0])) != -1) {
                        p = provinceList.get(provinceList.indexOf(new ProvinceModel(code[0])));
                    }
                    if (p != null && p.getCityList().indexOf(new CityModel(code[1])) != -1) {
                        c = p.getCityList().get(p.getCityList().indexOf(new CityModel(code[1])));
                    }
                    if (c != null && c.getDistrictList().indexOf(new DistrictModel(code[2])) != -1) {
                        d = c.getDistrictList().get(c.getDistrictList().indexOf(new DistrictModel(code[2])));
                    }
                    if (p != null && c != null && d != null) {
                        if (StringUtil.isEmpty(userInfo.getHuji_province())
                                || StringUtil.isEmpty(userInfo.getHuji_city())
                                || StringUtil.isEmpty(userInfo.getHuji_county())) {
                            tvHujiAddress.setText("");
                        } else {
                            tvHujiAddress.setText(p.getName() + "," + c.getName() + "," + d.getName());
                            hujiAddressBean = new HujiAddressBean();
                            hujiAddressBean.setHuji_province(p.getProvinceCode());
                            hujiAddressBean.setHuji_province_name(p.getName());
                            hujiAddressBean.setHuji_city(c.getCityCode());
                            hujiAddressBean.setHuji_city_name(c.getName());
                            hujiAddressBean.setHuji_county(d.getZipcode());
                            hujiAddressBean.setHuji_county_name(d.getName());
                            hujiAddressBean.setHuji_adr(p.getProvinceCode() + "," + c.getCityCode() + "," + d.getZipcode());
                            LogUtils.d("获取填写户籍地址:" + hujiAddressBean.toString());
                        }
                    } else {
                        tvHujiAddress.setText("");
                    }
                } else {
                    tvHujiAddress.setText("");
                }
            } catch (Exception e) {
                tvHujiAddress.setText("");
                e.printStackTrace();
            }
        }
        if (StringUtil.isNotEmpty(userInfo.getHuji_adr_detail())) {
            // 户籍详细地址
            tvHujiDetailAddress.setText(userInfo.getHuji_adr_detail());
        }
        if (StringUtil.isNotEmpty(userInfo.getLive_adr())) {
            // 居住地地址
            try {
                String[] code = userInfo.getLive_adr().split(",");
                if (code != null && code.length == 3) {
                    ProvinceModel p = null;
                    CityModel c = null;
                    DistrictModel d = null;
                    if (provinceList.indexOf(new ProvinceModel(code[0])) != -1) {
                        p = provinceList.get(provinceList.indexOf(new ProvinceModel(code[0])));
                    }
                    if (p != null && p.getCityList().indexOf(new CityModel(code[1])) != -1) {
                        c = p.getCityList().get(p.getCityList().indexOf(new CityModel(code[1])));
                    }
                    if (c != null && c.getDistrictList().indexOf(new DistrictModel(code[2])) != -1) {
                        d = c.getDistrictList().get(c.getDistrictList().indexOf(new DistrictModel(code[2])));
                    }
                    if (p != null && c != null && d != null) {
                        if (StringUtil.isEmpty(userInfo.getLive_province())
                                || StringUtil.isEmpty(userInfo.getLive_city())
                                || StringUtil.isEmpty(userInfo.getLive_county())) {
                            tvLiveAddress.setText("");
                        } else {
                            tvLiveAddress.setText(p.getName() + "," + c.getName() + "," + d.getName());
                            liveAddressBean = new LiveAddressBean(); //  实例化bean
                            liveAddressBean.setLive_province(p.getProvinceCode());
                            liveAddressBean.setLive_province_name(p.getName());
                            liveAddressBean.setLive_city(c.getCityCode());
                            liveAddressBean.setLive_city_name(c.getName());
                            liveAddressBean.setLive_county(d.getZipcode());
                            liveAddressBean.setLive_county_name(d.getName());
                            liveAddressBean.setLive_adr(p.getProvinceCode() + "," + c.getCityCode() + "," + d.getZipcode());
                            LogUtils.d("获取填写居住地址:" + liveAddressBean.toString());
                        }
                    } else {
                        tvLiveAddress.setText("");
                    }
                } else {
                    tvLiveAddress.setText("");
                }
            } catch (Exception e) {
                tvLiveAddress.setText("");
                e.printStackTrace();
            }
        }
        if (StringUtil.isNotEmpty(userInfo.getLive_adr_detail())) {
            // 居住详细地址
            etLiveDetailAddress.setText(userInfo.getLive_adr_detail());
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void jumpToTakePhoto(final int requestCode) {
        if (ToolPermission.checkSelfPermission(
                this,
                null,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                "请允许权限进行拍照",
                requestCode)) {
            holdCardFile = new File(Environment.getExternalStorageDirectory(), "/JJTNEW/temp/" + System.currentTimeMillis() + ".jpg");
            holdIdcardPath = holdCardFile.getAbsolutePath();
            if (!holdCardFile.getParentFile().exists()) holdCardFile.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(holdCardFile);
            takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(true).create());
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
            takePhoto.onEnableCompress(compressConfig, true);
            takePhoto.onPickFromCapture(imageUri);
        }
    }

    /**
     * 身份证扫描
     *
     * @param requestCode
     * @param type        扫描类型  0:正面;1:反面
     * @param isVertical  扫描方向 true:竖屏;false:横屏
     */
    private void jumpScanIDcard(int requestCode, int type, boolean isVertical) {
        if (ToolPermission.checkSelfPermission(
                this,
                null,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                "请允许权限进行扫描",
                requestCode)) {
            Intent intent = IdcardUtils.getInstance().getIdCardIntent(this, type, false);
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = new TakePhotoImpl(this, this);
        }
        return takePhoto;
    }

    // 图片拍照回调
    @Override
    public void takeSuccess(TResult result) {
        LogUtils.d("takeSuccess【图片原始路径】" + result.getImage().getOriginalPath());
        filePath = result.getImage().getOriginalPath();  // 原始路径
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if (bitmap == null) {
            ToastAlone.showLongToast(PersonalInfoActivity.this, "图片为空").show();
            return;
        }
        Bitmap decode = ImageUtils.comp(bitmap);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        ImageUtils.compressBitmapToFile(decode, Bitmap.CompressFormat.PNG, 100, new File(filePath));
        if (decode != null && !decode.isRecycled()) {
            decode.recycle();
        }
        mBitmapHoldIdcard = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(filePath), mWidth, mHeight, true);
        // TODO 图片上传
        uploadFile(holdCardFile, "4");
    }

    @Override
    public void takeFail(TResult result, String msg) {
        LogUtils.d("图片takeFail：" + result.getImage().getCompressPath());
    }

    @Override
    public void takeCancel() {
        ToastAlone.showShortToast(this, getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

    // 扫描证件成功
    private void scanSuccessInfo(final String name, final String num, final String addr) {
        final CustomerDialog dialogCustom = new CustomerDialog(this);
        dialogCustom.scanIdcardInfo(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_confirm:
                        EditText etAddress = dialogCustom.findViewById(R.id.et_detail_address);
                        String finalAddress = etAddress.getText().toString().trim();
                        if (StringUtil.isEmpty(finalAddress)) {
                            ToastAlone.showLongToast(BaseApplication.getContext(), "地址信息有误");
                            return;
                        }
                        idCardFrontBean.setAddress(etAddress.getText().toString().trim());
                        // TODO 点击确认时,1.保存bean;2.上传图片至服务器端
                        dialogCustom.dismiss();
                        mBitmapIDcardFront = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(idcardFile.getPath()), mWidth, mHeight, true);
                        uploadFile(idcardFile, "2");
                        break;

                    case R.id.tv_scan_again:
                        ToastAlone.showLongToast(BaseApplication.getContext(), "重新扫描");
                        break;
                }
            }
        }, name, num, addr).show();
    }

    // 手持证件照 提示
    private void takePhotoModelNotice() {
        final CustomerDialog dialogCustom = new CustomerDialog(this);
        dialogCustom.holdIdcardNotice(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.root_takephoto_model:
                        dialogCustom.dismiss();
                        jumpToTakePhoto(INTENT_IDCARD_HOLD);
                        break;
                }
            }
        }).show();
    }

    private void init() {
        Drawable d = ContextCompat.getDrawable(this, R.mipmap.shotcard_positive);
        mWidth = d.getMinimumWidth();
        mHeight = d.getMinimumHeight();
        LogUtils.d("idcardBgSize--->" + mWidth + "-----" + mHeight);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
        CommonUtils.addressDatas(this);
        IdcardUtils.getInstance().init(this);
    }

    private View getRootView() {
        return this.getWindow().getDecorView();
    }

    private void uploadFile(File file, String fileType) {
        String filePath = file.getPath();
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(".")); // 文件名
        String fileSuffixName = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());  // 后缀名(文件类型)
        String data = ImageUtils.fileBase64(filePath);
        Map<String, String> params = new HashMap<String, String>();
        params.put("fileName", fileName);
        params.put("fileExtName", fileSuffixName);
        params.put("fileContext", data);
        params.put("fileType", fileType);
        mPresenter.uploadFile(params);
    }

    private void loadingPersonInfo() {
        Map<String, String> paramMaps = new HashMap<>();
        mPresenter.getPersonalInfo(paramMaps);
    }

    @Override
    public void onSuccessFrontBean(String returnCode, IDCardFrontBean resultBean) {
        if ("front".equals(returnCode)) {  // 身份证正面信息
            if (!StringUtil.isIDCard(resultBean.getId_card_number())) {
                ToastAlone.showLongToast(this, "身份证号码有误");
                return;
            }
            idCardFrontBean = resultBean;
            scanSuccessInfo(resultBean.getName(), resultBean.getId_card_number(), resultBean.getAddress());
        } else {
            ToastAlone.showLongToast(this, "请使用正式身份证拍照");
        }
    }

    @Override
    public void onSuccessBackBean(String returnCode, IDCardBackBean resultBean) {
        if ("back".equals(returnCode)) {  // 身份证背面
            mBitmapIDcardBack = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(idcardFile.getPath()), mWidth, mHeight, true);
            uploadFile(idcardFile, "3");
        } else {
            ToastAlone.showLongToast(this, "请使用正式身份证拍照");
        }
    }

    @Override
    public void onSuccessUploadPic(String apiCode, String fileFlag, String msg) {
        ToastAlone.showLongToast(this, msg);
        // 区分 正面和反面
        if (fileFlag.equals("2")) {
            ivIdcardFront.setImageBitmap(mBitmapIDcardFront);
            tvPersonalName.setText(idCardFrontBean.getName().trim());
            tvPersonalIdcard.setText(idCardFrontBean.getId_card_number().trim());
            tvHujiDetailAddress.setText(idCardFrontBean.getAddress());
        } else if (fileFlag.equals("3")) {
            ivIdcardBack.setImageBitmap(mBitmapIDcardBack);
        } else if (fileFlag.equals("4")) {
            ivHoldIdcard.setImageBitmap(mBitmapHoldIdcard);
        }
    }

    @Override
    public void onSuccessGet(String returnCode, PersonalInfoBean bean) {
        personalInfoBean = bean;
        showUserInfo(personalInfoBean);
    }

    @Override
    public void onSuccessAdd(String returnCode, String msg) {
        ToastAlone.showLongToast(this, msg);
        finish();
    }

    @Override
    public void onSuccessEdit(String returnCode, String msg) {
        ToastAlone.showLongToast(this, msg);
        finish();
    }

    @Override
    public void onFail(String returnCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }

}

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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
import com.zyjr.emergencylending.custom.dialog.DialogCustom;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.entity.UserInfoManager;
import com.zyjr.emergencylending.ui.home.View.IDCardView;
import com.zyjr.emergencylending.ui.home.presenter.IDCardPresenter;
import com.zyjr.emergencylending.utils.AppToast;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.ToolImage;
import com.zyjr.emergencylending.utils.permission.ToolPermission;
import com.zyjr.emergencylending.utils.third.IdcardUtils;
import com.zyjr.emergencylending.widget.pop.AreaSelectPop;
import com.zyjr.emergencylending.widget.pop.SingleSelectPop;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 个人信息/资料
 */
public class PersonalInfoActivity extends BaseActivity<IDCardPresenter, IDCardView> implements TakePhoto.TakeResultListener, IDCardView {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.iv_idcard_front)
    ImageView ivIdcardFront;
    private String frontFilePath = "";  // 身份证正面照路径
    @BindView(R.id.iv_idcard_back)
    ImageView ivIdcardBack;
    private String backFilePath = "";   // 身份证正面照路径
    @BindView(R.id.iv_idcard_hold)
    ImageView ivHoldIdcard;
    private String holdIdcardPath; // 手持身份证路径
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
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;  // 户籍详细地址
    @BindView(R.id.tv_live_address)
    TextView tvLiveAddress;  // 居住地址
    @BindView(R.id.tv_live_detail_address)
    ClearEditText tvLiveDetailAddress; // 居住详细地址

    private File idcardFile;
    private TakePhoto takePhoto;
    private String filePath;
    private Bitmap mBitmapHoldIdcard, mBitmapIDcardFront, mBitmapIDcardBack;
    private static final int INTENT_IDCARD_FRONT = 100;
    private static final int INTENT_IDCARD_BACK = 101;
    private int mWidth;
    private int mHeight;

    @Override
    protected IDCardPresenter createPresenter() {
        return new IDCardPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        init();
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
            ToolImage.compressBitmapToFile(bitmap, Bitmap.CompressFormat.JPEG, 100, idcardFile); // 保存文件
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
        if (requestCode == 101) {
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                takePhotoModelNotice();
            } else {
                AppToast.makeToast(PersonalInfoActivity.this, "相机权限被拒绝");
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
                        tvMarriageStatus.setText(select.getName());
                        LogUtils.d("婚姻状况选择:" + select.toString());
                    }
                });
                popMarriageStatusSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_live_status: // 居住状况选择
                SingleSelectPop popLiveStatusSelect = new SingleSelectPop(this, AppConfig.liveStatus());
                popLiveStatusSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        tvLiveStatus.setText(select.getName());
                        LogUtils.d("居住状况选择:" + select.toString());
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
                    }
                });
                if (!TextUtils.isEmpty(UserInfoManager.getInstance().getLocation().getmCurrentCity())) {
                    popHujiAddressSelect.setWheel();
                }
                popHujiAddressSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_live_address: // 居住地址选择
                AreaSelectPop popLiveAddressSelect = new AreaSelectPop(this, CommonUtils.mProvinceDatas, CommonUtils.mCitisDatasMap, CommonUtils.mDistrictDatasMap);
                popLiveAddressSelect.setOnCityPopupWindow(new AreaSelectPop.OnCityPopupWindow() {
                    @Override
                    public void onCityClick(String province, int privinceItem, String city, int cityItem, String district, int districtItem) {
                        tvLiveAddress.setText(province + "," + city + "," + district);
                    }
                });
                if (!TextUtils.isEmpty(UserInfoManager.getInstance().getLocation().getmCurrentCity())) {
                    popLiveAddressSelect.setWheel();
                }
                popLiveAddressSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_submit: // 提交
                // TODO 信息提交

                break;
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
            File file = new File(Environment.getExternalStorageDirectory(), "/JJTNEW/temp/" + System.currentTimeMillis() + ".jpg");
            holdIdcardPath = file.getAbsolutePath();
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);
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
                new String[]{Manifest.permission.READ_SMS, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
        File file = new File(holdIdcardPath);
        if (!file.exists()) { // 返回的filepath路径有问题
            filePath = holdIdcardPath;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if (bitmap == null) {
            ToastAlone.showLongToast(PersonalInfoActivity.this, "图片为空").show();
            return;
        }
        Bitmap decode = ToolImage.comp(bitmap);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        ToolImage.compressBitmapToFile(decode, Bitmap.CompressFormat.PNG, 100, new File(filePath));
        if (decode != null && !decode.isRecycled()) {
            decode.recycle();
        }
        String data = ToolImage.fileBase64(filePath); // 图片具体信息
        final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(".")); // 文件名后缀名
        String fileExtName = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        LogUtils.d("图片具体信息----->文件名:" + fileName + ",文件后缀:" + fileExtName);
        mBitmapHoldIdcard = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(filePath), mWidth, mHeight, true);
        ivHoldIdcard.setImageBitmap(mBitmapHoldIdcard);
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
    private void scanSuccessInfo(String name, String num, String addr, final IDCardFrontBean idCardBean) {
        final DialogCustom dialogCustom = new DialogCustom(this);
        dialogCustom.scanIdcardInfo(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_confirm:
                        // TODO 点击确认时,1.保存bean;2.上传图片至服务器端
                        dialogCustom.dismiss();
                        mBitmapIDcardFront = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(idcardFile.getPath()), mWidth, mHeight, true);
                        ivIdcardFront.setImageBitmap(mBitmapIDcardFront);
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
        final DialogCustom dialogCustom = new DialogCustom(this);
        dialogCustom.holdIdcardNotice(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.root_takephoto_model:
                        dialogCustom.dismiss();
                        jumpToTakePhoto(101);
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
        IdcardUtils.getInstance().init(BaseApplication.getContext());
    }

    private View getRootView() {
        return this.getWindow().getDecorView();
    }


    @Override
    public void onSuccessFrontBean(String returnCode, IDCardFrontBean resultBean) {
        if ("front".equals(returnCode)) {  // 身份证正面信息
            if (!StringUtil.isIDCard(resultBean.getId_card_number())) {
                ToastAlone.showLongToast(this, "身份证号码有误");
                return;
            }
            scanSuccessInfo(resultBean.getName(), resultBean.getId_card_number(), resultBean.getAddress(), resultBean);
        } else {
            ToastAlone.showLongToast(this, "请使用正式身份证拍照");
        }
    }

    @Override
    public void onSuccessBackBean(String returnCode, IDCardBackBean resultBean) {
        if ("back".equals(returnCode)) {  // 身份证背面
            mBitmapIDcardBack = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(idcardFile.getPath()), mWidth, mHeight, true);
            ivIdcardBack.setImageBitmap(mBitmapIDcardBack);
        } else {
            ToastAlone.showLongToast(this, "请使用正式身份证拍照");
        }
    }


    @Override
    public void onFail(String errorMessage) {

    }
}

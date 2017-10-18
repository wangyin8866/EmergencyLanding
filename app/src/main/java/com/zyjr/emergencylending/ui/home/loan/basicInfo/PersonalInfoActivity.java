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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.AppConfig;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.UserInfoManager;
import com.zyjr.emergencylending.utils.AppToast;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToolImage;
import com.zyjr.emergencylending.utils.permission.ToolPermission;
import com.zyjr.emergencylending.widget.pop.AreaSelectPop;
import com.zyjr.emergencylending.widget.pop.SingleSelectPop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 个人资料
 */
public class PersonalInfoActivity extends BaseActivity implements TakePhoto.TakeResultListener {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.iv_idcard_hold)
    ImageView ivHoldIdcard;
    @BindView(R.id.tv_marriage_status)
    TextView tvMarriageStatus;
    @BindView(R.id.tv_live_status)
    TextView tvLiveStatus;
    @BindView(R.id.tv_huji_address)
    TextView tvHujiAddress;
    @BindView(R.id.tv_live_address)
    TextView tvLiveAddress;

    private TakePhoto takePhoto;
    private String holdIdcardPath;
    private String filePath;
    private Bitmap mBitmapHoldIdcard;
    private int mWidth;
    private int mHeight;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        Drawable d = ContextCompat.getDrawable(this, R.mipmap.shotcard_positive);
        mWidth = d.getMinimumWidth();
        mHeight = d.getMinimumHeight();
        LogUtils.d("idcard", mWidth + "-----" + mHeight);
        try {
            takePhoto = new TakePhotoImpl(this, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        takePhoto.onCreate(savedInstanceState);

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
    }

    @OnClick({R.id.ll_idcard_hold, R.id.ll_marriage_status, R.id.ll_live_status, R.id.ll_huji_address, R.id.ll_live_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_idcard_hold: // 手持证件照
                jumpToTakePhoto(101);
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
                AreaSelectPop poLiveAddressSelect = new AreaSelectPop(this, CommonUtils.mProvinceDatas, CommonUtils.mCitisDatasMap, CommonUtils.mDistrictDatasMap);
                poLiveAddressSelect.setOnCityPopupWindow(new AreaSelectPop.OnCityPopupWindow() {
                    @Override
                    public void onCityClick(String province, int privinceItem, String city, int cityItem, String district, int districtItem) {
                        tvLiveAddress.setText(province + "," + city + "," + district);
                    }
                });
                if (!TextUtils.isEmpty(UserInfoManager.getInstance().getLocation().getmCurrentCity())) {
                    poLiveAddressSelect.setWheel();
                }
                poLiveAddressSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void jumpToTakePhoto(final int type) {
        if (ToolPermission.checkSelfPermission(
                this, null,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, "请允许权限进行拍照", type)) {
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                jumpToTakePhoto(101);
            } else {
                AppToast.makeToast(PersonalInfoActivity.this, "拍照权限被拒绝");
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePhoto.onActivityResult(requestCode, resultCode, data);
    }

    // 图片拍照回调
    @Override
    public void takeSuccess(TResult result) {
        filePath = result.getImage().getOriginalPath();
        File file = new File(holdIdcardPath);
        if (!file.exists()) { //返回的filepath路径有问题
            filePath = holdIdcardPath;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if (bitmap == null) {
            Toast.makeText(PersonalInfoActivity.this, "图片为空", Toast.LENGTH_LONG).show();
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
        LogUtils.d("图片位置信息filePath:" + filePath);
        String data = ToolImage.fileBase64(filePath);
        final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(".")); // 文件名后缀名
        String fileExtName = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        LogUtils.d("具体信息:" + fileName + "," + fileExtName);
        mBitmapHoldIdcard = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(filePath), mWidth, mHeight, true);
        ivHoldIdcard.setImageBitmap(mBitmapHoldIdcard);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    private View getRootView() {
        return this.getWindow().getDecorView();
    }
}

package com.zyjr.emergencylending.ui.home.loan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.megvii.licensemanager.Manager;
import com.megvii.livenessdetection.LivenessLicenseManager;
import com.megvii.livenesslib.LivenessActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.ui.home.loan.auth.MobileAuthActivity;
import com.zyjr.emergencylending.ui.home.loan.auth.ZhimaAuthActivity;
import com.zyjr.emergencylending.utils.AppToast;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.permission.ToolPermission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 认证中心
 */
public class AuthCenterActivity extends BaseActivity {

    @BindView(R.id.rl_zhimaxinyong_auth)
    RelativeLayout rlZhimaxinyongAuth;
    @BindView(R.id.rl_mobile_auth)
    RelativeLayout rlMobileAuth;
    @BindView(R.id.rl_face_auth)
    RelativeLayout rlFaceAuth;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_center);
        ButterKnife.bind(this);

        netWorkWarranty(); // 联网授权

    }

    @OnClick({R.id.rl_zhimaxinyong_auth, R.id.rl_mobile_auth, R.id.rl_face_auth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_zhimaxinyong_auth:  // 芝麻认证
                startActivity(new Intent(this, ZhimaAuthActivity.class));
                break;
            case R.id.rl_mobile_auth:  // 手机运营商认证
                startActivity(new Intent(this, MobileAuthActivity.class));
                break;
            case R.id.rl_face_auth:  // 人脸认证
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int hasCamera = mContext.checkSelfPermission(Manifest.permission.CAMERA);
                    int hasWsd = mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (hasCamera == PackageManager.PERMISSION_GRANTED && hasWsd == PackageManager.PERMISSION_GRANTED) {
                        startActivity(new Intent(this, LivenessActivity.class));
                    } else {
                        this.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
                    }
                } else {
                    startActivity(new Intent(this, LivenessActivity.class));
                }


                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.d("权限申请------");
        if (requestCode == 111) {
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                startActivity(new Intent(this, LivenessActivity.class));
            } else {
                AppToast.makeToast(AuthCenterActivity.this, "相机权限被拒绝");
            }
        }
    }


    private void netWorkWarranty() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Manager manager = new Manager(AuthCenterActivity.this);
                LivenessLicenseManager licenseManager = new LivenessLicenseManager(AuthCenterActivity.this);
                manager.registerLicenseManager(licenseManager);
                manager.takeLicenseFromNetwork("");
                if (licenseManager.checkCachedLicense() > 0)
                    mHandler.sendEmptyMessage(1);
                else
                    mHandler.sendEmptyMessage(2);
            }
        }).start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                    if (isFirstAuthorization) {
//                        isFirstAuthorization = false;
//                    }
//                    isAuthorizationSuccess = true;
                    ToastAlone.showLongToast(AuthCenterActivity.this, "授权成功！");
                    break;
                case 2:
//                    isAuthorizationSuccess = false;
//                    if (!isFirstAuthorization) {
//                        ToastUtils.showShort(IApplication.globleContext, "授权失败，请重新授权！");
//                    }
                    ToastAlone.showLongToast(AuthCenterActivity.this, "授权失败，请重新授权！");
                    break;
            }
        }
    };
}

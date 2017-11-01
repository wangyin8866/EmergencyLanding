package com.zyjr.emergencylending.ui.home.loan;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.megvii.licensemanager.Manager;
import com.megvii.livenessdetection.LivenessLicenseManager;
import com.megvii.livenesslib.LivenessActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.ui.home.View.AuthInfoView;
import com.zyjr.emergencylending.ui.home.loan.auth.MobileAuthActivity;
import com.zyjr.emergencylending.ui.home.loan.auth.ZhimaAuthActivity;
import com.zyjr.emergencylending.ui.home.presenter.AuthInfoPresenter;
import com.zyjr.emergencylending.utils.AppToast;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.permission.ToolPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 认证中心
 * 人脸识别 需要联网授权
 */
public class AuthCenterActivity extends BaseActivity<AuthInfoPresenter, AuthInfoView> implements AuthInfoView {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.rl_zhimaxinyong_auth)
    RelativeLayout rlZhimaxinyongAuth;
    @BindView(R.id.rl_mobile_auth)
    RelativeLayout rlMobileAuth;
    @BindView(R.id.rl_face_auth)
    RelativeLayout rlFaceAuth;

    private static final int RC_CAMERA_PERMISSION = 101; // 拍照权限请求码
    private static final int RC_PAGE_TO_LIVENESS = 102; // 活体识别请求码
    private boolean isAuthorizationSuccess = false; // 是否授权成功
    private static final int MESSAGE_LIVENESS_WARRANTY_SUCCESS = 1; // 活体授权成功
    private static final int MESSAGE_LIVENESS_WARRANTY_FAIL = 2; // 活体授权失败
    private MediaPlayer mMediaPlayer = null;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_LIVENESS_WARRANTY_SUCCESS:
                    isAuthorizationSuccess = true;
                    break;
                case MESSAGE_LIVENESS_WARRANTY_FAIL:
                    isAuthorizationSuccess = false;
                    ToastAlone.showLongToast(AuthCenterActivity.this, "授权失败,请重新授权!");
                    break;
            }
        }
    };

    @Override
    protected AuthInfoPresenter createPresenter() {
        return new AuthInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_center);
        ButterKnife.bind(this);

        init();
        loadingAuthStatus();
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
                jumpToFaceAuth(RC_CAMERA_PERMISSION);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_CAMERA_PERMISSION) {  // 相机权限
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                startActivity(new Intent(this, LivenessActivity.class));
            } else {
                AppToast.makeToast(AuthCenterActivity.this, "相机权限被拒绝");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PAGE_TO_LIVENESS && resultCode == RESULT_OK) {  // 活体识别返回
            try {
                Bundle bundle = data.getExtras();
                String resultOBJ = bundle.getString("result");
                JSONObject result = new JSONObject(resultOBJ);
                int resultcode = result.getInt("resultcode");
                doMediaPlay(resultcode);
                boolean isSuccess = result.getString("result").equals(getResources().getString(R.string.verify_success));
                if (isSuccess) {
                    String delta = bundle.getString("delta");
                    Map<String, byte[]> images = (Map<String, byte[]>) bundle.getSerializable("images");
                    // TODO  图片和信息进行比对(缓存中获取姓名 和身份证号)
                    String name = "虞进";
                    String idcardNum = "421182199204043732";
                    imageVerify(images, delta, name, idcardNum);
//                    mPresenter.validateFaceAuth(requestParams);
                } else {
                    ToastAlone.showLongToast(this, "人脸识别失败，请重新识别");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    private void loadingAuthStatus(){
        Map<String,String>  params = new HashMap<>();
        mPresenter.getCurrentAuthInfo(params);
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void jumpToFaceAuth(final int requestCode) {
        if (ToolPermission.checkSelfPermission(
                this,
                null,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                "请允许开启相机权限",
                requestCode)) {
            startActivityForResult(new Intent(this, LivenessActivity.class), RC_PAGE_TO_LIVENESS);
        }
    }



    /**
     * 人脸识别联网授权
     */
    private void netWorkWarranty() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Manager manager = new Manager(AuthCenterActivity.this);
                LivenessLicenseManager licenseManager = new LivenessLicenseManager(AuthCenterActivity.this);
                manager.registerLicenseManager(licenseManager);
                manager.takeLicenseFromNetwork("");
                if (licenseManager.checkCachedLicense() > 0) {
                    mHandler.sendEmptyMessage(MESSAGE_LIVENESS_WARRANTY_SUCCESS);
                } else {
                    mHandler.sendEmptyMessage(MESSAGE_LIVENESS_WARRANTY_FAIL);
                }
            }
        }).start();
    }


    /**
     * 如何调用Verify2.0方法
     */
    public void imageVerify(Map<String, byte[]> images, String delta, String name, String idCard) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("name", name);
        requestParams.put("idcard", idCard);
        try {
            requestParams.put("image_ref1", new FileInputStream(new File("image_idcard"))); // 传入身份证头像照片路径
        } catch (Exception e) {
        }
        requestParams.put("delta", delta);
        requestParams.put("api_key", Constants.FACE_APPKEY);
        requestParams.put("api_secret", Constants.FACE_APPSECRET);
        requestParams.put("comparison_type", 1 + "");
        requestParams.put("face_image_type", "meglive");
        requestParams.put("idcard_name", name);
        requestParams.put("idcard_number", idCard);
        for (Map.Entry<String, byte[]> entry : images.entrySet()) {
            requestParams.put(entry.getKey(), new ByteArrayInputStream(entry.getValue()));
        }
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = "https://api.megvii.com/faceid/v2/verify";
        try {
            asyncHttpClient.post(url, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    String successStr = new String(responseBody);
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(successStr);
                        if (!jsonObject.has("error")) {
                            // 活体最好的一张照片和公安部系统上身份证上的照片比较
                            double confidence = jsonObject.getJSONObject("result_faceid").getDouble("confidence");
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result_faceid").getJSONObject("thresholds");
                            double threshold = jsonObject2.getDouble("1e-3");
                            double tenThreshold = jsonObject2.getDouble("1e-4");
                            double hundredThreshold = jsonObject2.getDouble("1e-5");
                            LogUtils.d("confidence:" + confidence + ",tenThreshold:" + tenThreshold);
                            if (confidence > tenThreshold) {
                                ToastAlone.showLongToast(AuthCenterActivity.this, "人脸识别通过");
                                // TODO 识别通过后,做一些业务操作
                                // 提交请求到服务端
                            } else {
                                ToastAlone.showLongToast(AuthCenterActivity.this, "人脸识别未通过,请本人再次尝试");
                            }
                        } else {
                            ToastAlone.showLongToast(AuthCenterActivity.this, "人脸识别失败,请重试");
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        LogUtils.d("人脸识别比对--e1:" + e1.getMessage().toString());
                        ToastAlone.showLongToast(AuthCenterActivity.this, "人脸识别失败,请重试");
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    ToastAlone.showLongToast(AuthCenterActivity.this, "人脸识别失败,请重试");
                }
            });
        } catch (Exception e) {
            ToastAlone.showLongToast(AuthCenterActivity.this, "人脸识别失败,请重试");
            LogUtils.d("人脸识别比对人脸识别e----->" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void doMediaPlay(int resultcode) {
        int rawId = 0;
        if (resultcode == R.string.verify_success) {
            rawId = R.raw.meglive_success;
        } else {
            rawId = R.raw.meglive_failed;
        }
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.reset();
        try {
            AssetFileDescriptor localAssetFileDescriptor = getResources().openRawResourceFd(rawId);
            mMediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception localIOException) {
            localIOException.printStackTrace();
        }
    }


    @Override
    public void onSuccessGet(String returnCode, List<AuthInfoBean> beanList) {

    }

    @Override
    public void onSuccessSubmit(String returnCode, AuthInfoBean bean) {

    }

    @Override
    public void onFail(String errorMessage) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

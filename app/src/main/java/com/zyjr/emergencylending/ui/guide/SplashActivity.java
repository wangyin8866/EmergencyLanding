package com.zyjr.emergencylending.ui.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bqs.risk.df.android.BqsDF;
import com.bqs.risk.df.android.BqsParams;
import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.ui.salesman.activity.LineMainActivity;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.permission.PermissionUtils;

/**
 * @author wy
 * @date 2016/9/13
 */
public class SplashActivity extends BaseActivity {
    //获取6.0运行时权限列表，第一个参数：是否授权gps，第二个参数：是否授权通讯录，第三个参数：是否授权通话记录
    String[] requestPermissions = BqsDF.getRuntimePermissions(true, true, true);

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);


        PermissionUtils.requestMultiPermissions(this, requestPermissions, mPermissionGrant);
//        if (!NetworkUtils.isNetAvailable(SplashActivity.this)) {
//            Toast.makeText(SplashActivity.this, "亲,您的网络没有打开,无法进行操作,请连接网络之后再操作！", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //不是第一次就跳转到主页
                if (SPUtils.getWyBoolean(mContext, Config.KEY_GUIDE)) {

                    if (BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
                        Intent intent = new Intent(SplashActivity.this, LineMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    //第一次就跳到导航页
                    Intent intent = new Intent(SplashActivity.this, GuideAtActivity.class);
                    startActivity(intent);
                    //保存访问记录
                    SPUtils.saveWyBoolean(mContext, Config.KEY_GUIDE, true);
                    finish();
                }
            }
        }, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断是否设置手势密码
      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!"".equals(gesturePsw) || !TextUtils.isEmpty(gesturePsw)){
                    startActivity(new Intent(SplashActivity.this, GestureVerifyActivity.class));
                    finish();
                }
            }
        },3000);*/
    }

    /**
     * 授权结果，该回调不管权限是拒绝还是同意都会进入该回调方法
     */
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, String[] requestPermissions) {
            Constants.authRuntimePermissions = true;

            /**
             * 因为在启动页进行运行时权限授权，所以要在授权结果回调中出发一次初始化
             */
            //2、配置初始化参数
            BqsParams params = new BqsParams();
            params.setPartnerId("haoliwang");//商户编号
            params.setTestingEnv(true);//false是生产环境 true是测试环境
            params.setGatherGps(true);
            params.setGatherContact(true);
            params.setGatherCallRecord(true);
            //3、执行初始化
            BqsDF.initialize(mContext, params);
            //采集通讯录,第一个参数：是否采集通讯录，第二个参数：是否采集通话记录
            BqsDF.commitContactsAndCallRecords(false, false);
            BqsDF.commitLocation();
            //BqsDF.commitLocation(longitude, latitude);

            //4、提交tokenkey到贵公司服务器
            String tokenkey = BqsDF.getTokenKey();
            LogUtils.e("tokenkey", tokenkey);
            SPUtils.saveWyString(mContext, Config.KEY_BAI_QI_SHI, tokenkey);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(requestCode, requestPermissions, grantResults, requestPermissions, mPermissionGrant);
    }
}

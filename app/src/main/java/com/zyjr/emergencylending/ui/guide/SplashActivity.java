package com.zyjr.emergencylending.ui.guide;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.ui.salesman.activity.LineMainActivity;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.SPUtils;

import cn.tongdun.android.shell.FMAgent;
import cn.tongdun.android.shell.exception.FMException;

/**
 * @author wy
 * @date 2016/9/13
 */
public class SplashActivity extends BaseActivity {


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);
        /**
         * 同盾
         */
        //权限申请
        if (Build.VERSION.SDK_INT > 23) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,  //必选
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,  //必选
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_CONTACTS  //通讯录

            }, 100);
        }
        //FMAgent.ENV_SANDBOX表示测试环境，FMAgent.ENV_PRODUCTION表示生产环境
        try {
            FMAgent.init(SplashActivity.this, FMAgent.ENV_SANDBOX);
            String blackBox = FMAgent.onEvent(SplashActivity.this);
            LogUtils.e("tokenkey", blackBox);
            SPUtils.saveWyString(SplashActivity.this, Config.KEY_TONG_DUN, blackBox);
        } catch (FMException e) {
            e.printStackTrace();
        }

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


}

package com.zyjr.emergencylending.ui.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.ui.salesman.activity.LineMainActivity;
import com.zyjr.emergencylending.utils.SPUtils;

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
//        if (!NetworkUtils.isNetAvailable(SplashActivity.this)) {
//            Toast.makeText(SplashActivity.this, "亲,您的网络没有打开,无法进行操作,请连接网络之后再操作！", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //不是第一次就跳转到主页
                if (SPUtils.getGuideBoolean(mContext, "splash", false)) {
                
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
                    SPUtils.saveGuideBoolean(mContext, "splash", true);
                    finish();
                }
            }
        }, 1500);
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

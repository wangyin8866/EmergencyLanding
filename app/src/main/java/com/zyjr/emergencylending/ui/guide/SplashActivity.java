package com.zyjr.emergencylending.ui.guide;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.bqs.risk.df.android.BqsDF;
import com.bqs.risk.df.android.BqsParams;
import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.ui.salesman.activity.LineMainActivity;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.permission.ToolPermission;

import cn.tongdun.android.shell.FMAgent;
import cn.tongdun.android.shell.exception.FMException;

/**
 * @author wy
 * @date 2016/9/13
 * update 2017/12/12 优化白骑士、同盾授权
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
//        setContentView(R.layout.activity_splash_main);

//        if (!NetworkUtils.isNetAvailable(SplashActivity.this)) {
//            Toast.makeText(SplashActivity.this, "亲,您的网络没有打开,无法进行操作,请连接网络之后再操作！", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }


        initPermission();
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

    private void initPermission() {

        /**
         * 同盾 FMAgent.ENV_SANDBOX表示测试环境，FMAgent.ENV_PRODUCTION表示生产环境
         */
        try {
            FMAgent.init(SplashActivity.this, FMAgent.ENV_PRODUCTION);
            String blackBox = FMAgent.onEvent(SplashActivity.this);
            LogUtils.e("tokenkey", blackBox);
            SPUtils.saveWyString(SplashActivity.this, Config.KEY_TONG_DUN, blackBox);
        } catch (FMException e) {
            e.printStackTrace();
        }

        ToolPermission.checkPermission(this, new ToolPermission.PermissionCallBack() {
                    @Override
                    public void callBack(int requestCode, boolean isPass) {
                        LogUtils.d("权限检测结果---" + requestCode + "," + isPass);
                        /**
                         * 白骑士 因为在启动页进行运行时权限授权，所以要在授权结果回调中出发一次初始化
                         */
                        //2、配置初始化参数
                        BqsParams params = new BqsParams();
                        params.setPartnerId("haoliwang");//商户编号
                        params.setTestingEnv(false);//false是生产环境 true是测试环境
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
                },
                2000,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_COARSE_LOCATION,  //必选
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,  //必选
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS);
    }

}

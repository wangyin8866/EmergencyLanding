package com.zyjr.emergencylending.base;


import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.igexin.sdk.PushManager;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.service.DemoIntentService;
import com.zyjr.emergencylending.service.DemoPushService;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.SPUtils;


/**
 * 主工程
 *
 * @author wangyin
 * @date 2016/05/19
 */
public class BaseApplication extends Application {
    /**
     * 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了
     */
    private static BaseApplication mInstance;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;

    public static Context context;

    /**
     * 是否是线下（业务员）
     */
    public static String isSalesman;

    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();

    public interface MsgDisplayListener {
        void handle(String msg);
    }

    {
        PlatformConfig.setWeixin("wx14758d54ffd39c18", "2f5a5462b73e1952ae9121b125e050cd");
        PlatformConfig.setQQZone("1105705046", "mh4btC2fYzyHJsyo");
        PlatformConfig.setSinaWeibo("634140413", "49cfa168cc7892724dda804a642b3ce0", "http://sns.whalecloud.ckeom");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
        //是否打印日志
        LogUtils.isDebug = true;
        //友盟日志
        com.umeng.socialize.Config.DEBUG = true;
        initUMShare();
        initUMStatistics();

        initGT();

        isSalesman = SPUtils.getString(this, Config.KEY_USER_TYPE, Config.USER_COMMON);


    }

    private void initGT() {
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
    }

    private void initUMStatistics() {
        UMConfigure.init(context, "58107c9c717c190463000384", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        //设置dplus case
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_DUM_NORMAL);
    }

    private void initUMShare() {
        UMShareAPI.get(this);

    }

    public static Context getContext() {
        return context;
    }


    public static BaseApplication getApplication() {
        return mInstance;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initSopfix();
        MultiDex.install(base);
    }

    private void initSopfix() {
        String appVersion = "";
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            appVersion = "1.0.0";
        }
        SophixManager.getInstance().setContext(this).setAppVersion(appVersion).setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                        String msg = new StringBuilder("")
                                .append(" Mode:")
                                .append(mode)
                                .append(" Code:")
                                .append(code)
                                .append(" Info:")
                                .append(info)
                                .append(" HandlePatchVersion:")
                                .append(handlePatchVersion).toString();
                        System.out.println("加载补丁msg：" + msg);
                        if (msgDisplayListener != null) {
                            msgDisplayListener.handle(msg);
                        } else {
                            cacheMsg.append("\n").append(msg);
                        }
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载ok
                            System.out.println("加载补丁ok:" + PatchStatus.CODE_LOAD_SUCCESS);
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启，开发者课题室用户或者强制重启
                            // 建议:用户可以监听进入后台事件,然后应用自杀
                            System.out.println("加载补丁:" + PatchStatus.CODE_LOAD_RELAUNCH);
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常，推荐此时清空本地补丁，防止失败补丁重复加载
                            SophixManager.getInstance().cleanPatches();
                            System.out.println("加载补丁:" + PatchStatus.CODE_LOAD_FAIL);
                        } else {
                            // 其他错误信息
                            System.out.println("加载补丁:其他");
                        }

                    }
                }).initialize();
        SophixManager.getInstance().queryAndLoadNewPatch(); // 加载新的补丁包
    }
}

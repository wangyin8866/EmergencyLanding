package com.zyjr.emergencylending.base;


import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.zyjr.emergencylending.config.SupportCityConfig;
import com.zyjr.emergencylending.utils.LogUtils;


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
    public static boolean isSalesman;
    public static String clientId = "";


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
        //获取版本号
        SupportCityConfig.getInstance().initCitys(); // 加载城市数据
        //是否打印日志
        LogUtils.isDebug = true;
        //友盟日志
        Config.DEBUG = false;
//        UMShareAPI.get(this);
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

    {

        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }
}

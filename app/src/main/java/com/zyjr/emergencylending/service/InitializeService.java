package com.zyjr.emergencylending.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.igexin.sdk.PushManager;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zyjr.emergencylending.base.BaseApplication;


/**
 * @author wyman
 * @date 2018/3/16
 * description :
 */

public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.zyjr.emergencylending.service.action.INIT";
    public static StringBuilder cacheMsg = new StringBuilder();
    public static MsgDisplayListener msgDisplayListener;

    public interface MsgDisplayListener {
        void handle(String msg);
    }

    {
        PlatformConfig.setWeixin("wx14758d54ffd39c18", "2f5a5462b73e1952ae9121b125e050cd");
        PlatformConfig.setQQZone("1105705046", "mh4btC2fYzyHJsyo");
        PlatformConfig.setSinaWeibo("634140413", "49cfa168cc7892724dda804a642b3ce0", "http://sns.whalecloud.ckeom");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public InitializeService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }


    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    private void performInit() {
        initUMShare();
        initUMStatistics();
        initGT();
        initSopfix();
    }

    private void initGT() {
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
    }

    private void initUMStatistics() {
        UMConfigure.init(BaseApplication.context, "58107c9c717c190463000384", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        //设置dplus case
        MobclickAgent.setScenarioType(BaseApplication.context, MobclickAgent.EScenarioType.E_DUM_NORMAL);
    }

    private void initUMShare() {
        UMShareAPI.get(this);

    }

    private void initSopfix() {
        String appVersion = "";
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            appVersion = "2.0.0";
        }
        SophixManager.getInstance().setContext(BaseApplication.mInstance).setAppVersion(appVersion).setAesKey(null)
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

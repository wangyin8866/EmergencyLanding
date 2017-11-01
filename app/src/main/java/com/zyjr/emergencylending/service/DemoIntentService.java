package com.zyjr.emergencylending.service;

import android.content.Context;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.utils.LogUtils;

/**
 * @author wangyin
 * @date 2017/11/1.
 * @description :
 */

public class DemoIntentService extends GTIntentService {

    public DemoIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        LogUtils.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        BaseApplication.clientId = clientid;
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }
}

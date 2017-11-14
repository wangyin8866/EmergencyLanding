package com.zyjr.emergencylending.ui.home.loan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.permission.ToolPermission;
import com.zyjr.emergencylending.widget.step.HorizontalStepView;
import com.zyjr.emergencylending.widget.step.StepBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/13
 * 备注: 测试
 */

public class TestAc1 extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test1);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btnTest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTest:
                ToolPermission.checkPermission(this, new ToolPermission.PermissionCallBack() {
                            @Override
                            public void callBack(int requestCode, boolean isPass) {
                                LogUtils.d("权限检测结果---" + requestCode + "," + isPass);
                                if (isPass) {
                                    LogUtils.d("通讯录集合---->"+new Gson().toJson(CommonUtils.queryContactPhoneNumber(TestAc1.this))); // 通讯录集合
                                }else {
                                    Intent localIntent = new Intent();
                                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    if (Build.VERSION.SDK_INT >= 9) {
                                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                                    } else if (Build.VERSION.SDK_INT <= 8) {
                                        localIntent.setAction(Intent.ACTION_VIEW);
                                        localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
                                        localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                                    }
                                    startActivity(localIntent);
                                }
                            }
                        },
                        2000,
                        Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE);
                break;
        }
    }

}

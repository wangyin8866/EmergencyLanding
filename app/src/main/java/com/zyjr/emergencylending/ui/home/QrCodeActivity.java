package com.zyjr.emergencylending.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyin on 2017/10/11.
 */

public class QrCodeActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.qr_save)
    ImageView qrSave;
    @BindView(R.id.qr_we_chat)
    ImageView qrWeChat;
    @BindView(R.id.circle_of_friends)
    ImageView circleOfFriends;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    protected void init() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        ButterKnife.bind(this);
        init();
    }


    @OnClick({R.id.qr_save, R.id.qr_we_chat, R.id.circle_of_friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qr_save:
                break;
            case R.id.qr_we_chat:
                break;
            case R.id.circle_of_friends:
                break;
        }
    }
}

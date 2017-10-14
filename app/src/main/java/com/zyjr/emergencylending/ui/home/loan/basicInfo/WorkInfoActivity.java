package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.AppConfig;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.widget.pop.SingleSelectPop;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 工作资料
 */

public class WorkInfoActivity extends BaseActivity {

    @BindView(R.id.tv_job)
    TextView tvJob;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_info);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_job})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_job:
                SingleSelectPop popJobSelect = new SingleSelectPop(this, AppConfig.marriageStatus());
                popJobSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        tvJob.setText(select.getName());
                        LogUtils.d("选择的工作信息:" + select.toString());
                    }
                });
                popJobSelect.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
        }
    }
}

package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.RemindBean;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/16
 * 备注: 放款中
 */

public class SendLoanProcessingActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.layout_remind)
    LinearLayout layoutRemind; // 温馨提示
    @BindView(R.id.tv_warm_notice_1)
    TextView tvWarmNotice1;
    @BindView(R.id.tv_warm_notice_2)
    TextView tvWarmNotice2;
    @BindView(R.id.tv_warm_notice_3)
    TextView tvWarmNotice3;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_loan_processing);
        ButterKnife.bind(this);

        init();
        initGetData();
    }

    private void init() {
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

    private void initGetData() {
        Intent intent = getIntent();
        String isShow = intent.getStringExtra("isShow");
        if (Constants.ZERO.equals(isShow)) {
            layoutRemind.setVisibility(View.GONE);
        } else if (Constants.ONE.equals(isShow)) {
            RemindBean remindInfo = (RemindBean) intent.getSerializableExtra("remindInfo");
            List<String> infoList = remindInfo.getInfoList();
            if (infoList != null && infoList.size() > 0) {
                layoutRemind.setVisibility(View.VISIBLE);
                for (int i = 0; i < infoList.size(); i++) {
                    switch (i) {
                        case 0:
                            tvWarmNotice1.setText("1." + infoList.get(i).toString().replace("\"",""));
                            break;

                        case 1:
                            tvWarmNotice2.setText("2." + infoList.get(i).toString().replace("\"",""));
                            break;

                        case 2:
                            tvWarmNotice3.setText("3." + infoList.get(i).toString().replace("\"",""));
                            break;
                    }
                }
            }
        }
    }

    private void test(){
        String json = "\n" +
                "    \"flag\":\"API0000\",\n" +
                "    \"msg\":\"操作成功\",\n" +
                "    \"errorMsg\":null,\n" +
                "    \"promptMsg\":\"操作成功\",\n" +
                "    \"result\":{\n" +
                "        \"infoList\":[\n" +
                "\n" +
                "        ]\n" +
                "    },\n" +
                "    \"ext\":null,\n" +
                "    \"lockerFlag\":false\n" +
                "}";
        JsonElement parse = new JsonParser().parse(json);
        layoutRemind.setVisibility(View.VISIBLE);
        JsonElement result = parse.getAsJsonObject().get("result");
        JsonArray infoList1 = result.getAsJsonObject().getAsJsonArray("infoList");
        if (infoList1 != null && infoList1.size() > 0) {
            for (int i = 0; i < infoList1.size(); i++) {
                switch (i) {
                    case 0:
                        tvWarmNotice1.setText("1." + infoList1.get(i).toString().replace("\"",""));
                        break;

                    case 1:
                        tvWarmNotice2.setText("2." + infoList1.get(i).toString().replace("\"",""));
                        break;

                    case 2:
                        tvWarmNotice3.setText("3." + infoList1.get(i).toString().replace("\"",""));
                        break;
                }
            }
        }

    }
}

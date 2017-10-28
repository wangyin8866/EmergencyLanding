package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.SupportBank;
import com.zyjr.emergencylending.ui.home.View.BankcardInfoView;
import com.zyjr.emergencylending.ui.home.loan.AddBankcardActivity;
import com.zyjr.emergencylending.ui.home.loan.EditBankcardActivity;
import com.zyjr.emergencylending.ui.home.presenter.BankcardInfoPresenter;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 银行卡信息
 * 当没有银行卡时,管理 按钮消失;有银行卡信息时展示
 */
public class BankcardInfoActivity extends BaseActivity<BankcardInfoPresenter, BankcardInfoView> implements BankcardInfoView {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.rl_add_bankcard)
    RelativeLayout rlAddBankcard;  // 添加银行卡布局
    @BindView(R.id.rl_edit_bankcard)
    RelativeLayout rlEditBankcard;  // 银行卡信息
    @BindView(R.id.iv_bankcard_icon)
    ImageView ivBankcardIcon; // 银行卡logo
    @BindView(R.id.tv_bankcard_name)
    TextView tvBankcardName;  // 银行卡名称
    @BindView(R.id.tv_bankcard_type)
    TextView tvBankcardType;  // 银行卡类型
    @BindView(R.id.tv_bankcard_number)
    TextView tvBankcardNumber; // 银行卡号码
    @BindView(R.id.root_refreshview)
    PullToRefreshScrollView pullToRefreshScrollView;
    private BankcardInfo bankcardInfo = null; // 银行卡bean

    private static final int INTENT_CODE_ADD_BANKCARD = 1; // 添加银行卡 请求码
    private static final int INTENT_CODE_EDIT_BANKCARD = 2; // 编辑银行卡 请求码

    @Override
    protected BankcardInfoPresenter createPresenter() {
        return new BankcardInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bankcard);
        ButterKnife.bind(this);
        init();
        loadingBindBankcardInfo();
    }

    @OnClick({R.id.rl_add_bankcard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_add_bankcard:
                Intent intent = new Intent(this, AddBankcardActivity.class);
                startActivityForResult(intent, INTENT_CODE_ADD_BANKCARD);
                break;
        }
    }

    @OnLongClick(R.id.rl_edit_bankcard)
    boolean onLongClickBindButton() {
        showEditDialog();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_CODE_ADD_BANKCARD && resultCode == RESULT_OK) {
            loadingBindBankcardInfo();
        } else if (requestCode == INTENT_CODE_EDIT_BANKCARD && resultCode == RESULT_OK) {
            loadingBindBankcardInfo();
        }
    }

    private void showBankcardInfo(BankcardInfo bankcard) {
        bankcardInfo = bankcard;
        setBankIcon(bankcardInfo);
        tvBankcardName.setText(bankcardInfo.getBank_name());
        tvBankcardType.setText(""); // 此处没有字段返回
        if (StringUtil.isNotEmpty(bankcardInfo.getBankcard_no())) {
            String bankcardNum = bankcardInfo.getBankcard_no();
            tvBankcardNumber.setText(bankcardNum.substring(0, 4) + "* * * *   * * * *" + bankcardNum.substring(bankcardNum.length() - 4, bankcardNum.length()));
        }
    }


    private void showEditDialog() {
        final CustomerDialog customerDialog = new CustomerDialog(BankcardInfoActivity.this);
        customerDialog.editBankcard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_edit_bankcard:
                        customerDialog.dismiss();
                        // TODO: 调往编辑银行卡页面
                        Intent intent = new Intent(BankcardInfoActivity.this, EditBankcardActivity.class);
                        intent.putExtra("bankcardInfo", bankcardInfo);
                        startActivityForResult(intent, INTENT_CODE_EDIT_BANKCARD);
                        break;
                    case R.id.tv_cancel:
                        ToastAlone.showLongToast(BankcardInfoActivity.this, "取消");
                        customerDialog.dismiss();
                        break;
                }
            }
        }).show();
    }



    private void init() {
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {
                showEditDialog();
            }
        });

        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadingBindBankcardInfo();
            }
        });
        topBar.setRightButtonVisible(View.INVISIBLE);
    }

    private void loadingBindBankcardInfo() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("cust_juid", "e517fafd0d4a4034b4a88a6a1e041540");
        mPresenter.getBindBankcardInfo(paramsMap);
    }

    private void setBankIcon(BankcardInfo item) {
        if (item.getBank_name().contains("中国工商") || item.getBank_name().contains("工商")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_a);
        } else if (item.getBank_name().contains("中国农业")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_b);
        } else if (item.getBank_name().contains("中国银行")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_c);
        } else if (item.getBank_name().contains("中国建设银行") || item.getBank_name().contains("建设")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_d);
        } else if (item.getBank_name().contains("中国交通银行") || item.getBank_name().contains("交通")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_e);
        } else if (item.getBank_name().contains("光大银行") || item.getBank_name().contains("光大")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_f);
        } else if (item.getBank_name().contains("中信银行") || item.getBank_name().contains("中信")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_g);
        } else if (item.getBank_name().contains("兴业银行") || item.getBank_name().contains("兴业")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_h);
        } else if (item.getBank_name().contains("华夏银行") || item.getBank_name().contains("华夏")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_i);
        } else if (item.getBank_name().contains("平安银行") || item.getBank_name().contains("平安")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_j);
        } else if (item.getBank_name().contains("广东发展银行") || item.getBank_name().contains("广东发展")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_k);
        } else if (item.getBank_name().contains("中国民生银行") || item.getBank_name().contains("民生")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_l);
        } else if (item.getBank_name().contains("上海浦东发展银行") || item.getBank_name().contains("浦东发展")) {
            ivBankcardIcon.setImageResource(R.mipmap.banklogo_m);
        }
    }

    @Override
    public void onSuccessGet(String returnCode, BankcardInfo bean) {
        pullToRefreshScrollView.onRefreshComplete();
        rlEditBankcard.setVisibility(View.VISIBLE);
        rlAddBankcard.setVisibility(View.GONE);
        topBar.setRightButtonVisible(View.VISIBLE);
        showBankcardInfo(bean);
    }

    @Override
    public void onSuccessAdd(String returnCode, BankcardInfo bean) {

    }

    @Override
    public void onSuccessEdit(String returnCode, BankcardInfo bean) {

    }

    @Override
    public void onFail(String apiCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }

    @Override
    public void onSuccessGetSupportBanks(String returnCode, List<SupportBank> supportBanks) {

    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }
}

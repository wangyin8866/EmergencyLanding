package com.zyjr.emergencylending.ui.home.loan.online;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.SupportStoreAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.StoreBean;
import com.zyjr.emergencylending.widget.BubbleSeekBar;
import com.zyjr.emergencylending.widget.SelectorImageView;
import com.zyjr.emergencylending.widget.recyc.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/14
 * 备注: 线上申请 转线下
 */
public class ApplyToOfflineConfirmActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.seekbar_loan_money)
    BubbleSeekBar seekbarMoney;
    @BindView(R.id.seekbar_loan_week)
    BubbleSeekBar seekbarWeek;
    @BindView(R.id.tv_loan_money_min)
    TextView tvMinLoadMoney;
    @BindView(R.id.tv_loan_money_max)
    TextView tvMaxLoadMoney;
    @BindView(R.id.tv_loan_week_min)
    TextView tvMinLoadWeek;
    @BindView(R.id.tv_loan_week_max)
    TextView tvMaxLoadWeek;
    @BindView(R.id.rv_store_supported)
    RecyclerView rvStoreSupported;
    @BindView(R.id.pb_store_supported_loading)
    ProgressBar pbLoadingStore;
    @BindView(R.id.btn_submit_apply)
    Button btnSubmitApply;
    private List<StoreBean> storeBeanList = null;
    private SupportStoreAdapter adapter = null;
    private StoreBean storeBean = null;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_apply_confirm_to_offline);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        storeBeanList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            StoreBean item = new StoreBean("L上海市" + i, "百度文库是百度发布的供jsd胜多负少胜多负少东方闪电胜多负少大法师的...", "118号", false);
            storeBeanList.add(item);
        }
        pbLoadingStore.setVisibility(View.GONE);
        adapter = new SupportStoreAdapter(R.layout.rv_item_store_info, storeBeanList);
        rvStoreSupported.setLayoutManager(new LinearLayoutManager(this));
        rvStoreSupported.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.auth_success)));
        rvStoreSupported.setAdapter(adapter);
        rvStoreSupported.setVisibility(View.VISIBLE);
        adapter.bindToRecyclerView(rvStoreSupported);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < storeBeanList.size(); i++) {
                    SelectorImageView v = (SelectorImageView) adapter.getViewByPosition(i, R.id.iv_item_store_selected);
                    v.toggle(false);
                }
                storeBean = (StoreBean) adapter.getItem(position);
                SelectorImageView v = (SelectorImageView) adapter.getViewByPosition(position, R.id.iv_item_store_selected);
                v.toggle(true);
            }
        });

    }

}

package com.zyjr.emergencylending.ui.my;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.BorrowLog;
import com.zyjr.emergencylending.ui.my.presenter.MyBorrowPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyin
 * @date 2017/10/16
 * 我的借款
 */

public class MyBorrowActivity extends BaseActivity<MyBorrowPresenter, BaseView<BaseBean>> implements BaseView<BaseBean> {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.borrow_type)
    TextView borrowType;
    @BindView(R.id.borrow_status)
    TextView borrowState;
    @BindView(R.id.borrow_amount)
    TextView borrowAmount;
    @BindView(R.id.borrow_deadline)
    TextView borrowDeadline;
    @BindView(R.id.borrow_date)
    TextView borrowDate;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private List<BorrowLog> borrowLogs;

    @Override
    protected MyBorrowPresenter createPresenter() {
        return new MyBorrowPresenter(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrow);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mPresenter.getData(NetConstantValues.MY_LOAN, "1", "15");

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
    public void callBack(BaseBean baseBean) {
        llData.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);

//        borrowLogs = new ArrayList<>();
//        for (int i = 1; i < 10; i++) {
//            BorrowLog borrowLog = new BorrowLog(1000 * i + "", i + "周", new Date().toString(), "已结清");
//            borrowLogs.add(borrowLog);
//        }
//        rvMain.setLayoutManager(new LinearLayoutManager(this));
//        BorrowLogAdapter adapter = new BorrowLogAdapter(R.layout.item_borrow_log, borrowLogs);
//        rvMain.setAdapter(adapter);
//        if (borrowLogs.size() < 10) {
//            easylayout.setLoadMoreModel(LoadModel.NONE);//取消加载更多
//        }
//        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
//            @Override
//            public void onLoadMore() {
//                easylayout.loadMoreComplete();
//            }
//
//            @Override
//            public void onRefreshing() {
//                easylayout.refreshComplete();
//            }
//        });
    }
}

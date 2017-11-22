package com.zyjr.emergencylending.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.BorrowLogAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.ui.home.loan.HandleFailActivity;
import com.zyjr.emergencylending.ui.my.View.MyBorrowView;
import com.zyjr.emergencylending.ui.my.presenter.MyBorrowPresenter;
import com.zyjr.emergencylending.utils.AppToast;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wangyin
 * @date 2017/10/16
 * 我的借款
 */

public class MyBorrowActivity extends BaseActivity<MyBorrowPresenter, MyBorrowView> implements MyBorrowView, EasyRefreshLayout.EasyEvent {
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
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.current_borrow)
    LinearLayout currentBorrow;
    @BindView(R.id.history_borrow)
    LinearLayout historyBorrow;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry;
    @BindView(R.id.swipe_container)
    EasyRefreshLayout swipeContainer;

    private List<MyBorrow.ResultBean.HisBorrowListBean> hisBorrowListBeans;
    private MyBorrow.ResultBean.CurrentBorrowBean currentBorrowBean;
    private int pageNum = 1;
    private int pageSize = 15;

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
        swipeContainer.addEasyEvent(this);
        //隐藏上拉加载
        swipeContainer.setLoadMoreModel(LoadModel.NONE);

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
    protected void onResume() {
        super.onResume();
        mPresenter.getData(NetConstantValues.MY_LOAN, "1", pageSize + "");
    }

    @Override
    public void getCommonData(MyBorrow baseBean) {
        currentBorrowBean = baseBean.getResult().getCurrent_borrow();
        hisBorrowListBeans = baseBean.getResult().getHis_borrow_list();
        if (currentBorrowBean == null && hisBorrowListBeans.size() == 0) {
            currentBorrow.setVisibility(View.GONE);
            historyBorrow.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
        } else if (currentBorrowBean == null && hisBorrowListBeans.size() != 0) {
            currentBorrow.setVisibility(View.GONE);
            historyBorrow.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
        } else if (currentBorrowBean != null && hisBorrowListBeans.size() == 0) {
            currentBorrow.setVisibility(View.VISIBLE);
            historyBorrow.setVisibility(View.GONE);
            llEmpty.setVisibility(View.GONE);
        } else {
            currentBorrow.setVisibility(View.VISIBLE);
            historyBorrow.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
        }

        if (currentBorrowBean != null) {
            borrowState.setText(WYUtils.getMyBorrowStatus(Integer.parseInt(currentBorrowBean.getStep_status()), Integer.parseInt(currentBorrowBean.getLoan_status())));
            borrowAmount.setText(currentBorrowBean.getLoan_amount());
            borrowDeadline.setText(currentBorrowBean.getLoan_period() + WYUtils.getPeriod(currentBorrowBean.getLoan_unit()));
            borrowType.setText("   (" + currentBorrowBean.getProduct_type_name() + ")");
            borrowDate.setText(currentBorrowBean.getLoan_time());

        }
        if (hisBorrowListBeans.size() != 0) {
            rvMain.setLayoutManager(new LinearLayoutManager(this));
            BorrowLogAdapter adapter = new BorrowLogAdapter(R.layout.item_borrow_log, hisBorrowListBeans);


            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (Integer.parseInt(hisBorrowListBeans.get(position).getLoan_status()) == 9) {
                        //  跳转到审批拒件

                        Intent intent = new Intent(mContext, HandleFailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", hisBorrowListBeans.get(position));
                        bundle.putString("jumpFlag", "brrow");
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                }
            });


            rvMain.setAdapter(adapter);


            easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
                @Override
                public void onLoadMore() {
                    easylayout.loadMoreComplete();
                    pageNum += 1;
                    mPresenter.getMoreData(NetConstantValues.MY_LOAN, pageNum + "", pageSize + "");
                }

                @Override
                public void onRefreshing() {
                    easylayout.refreshComplete();
                }
            });


        }


    }

    @Override
    public void getMoreData(MyBorrow myBorrow) {
        if (myBorrow.getResult().getHis_borrow_list().size() > 0) {
            hisBorrowListBeans.addAll(myBorrow.getResult().getHis_borrow_list());
            rvMain.setLayoutManager(new LinearLayoutManager(this));
            BorrowLogAdapter adapter = new BorrowLogAdapter(R.layout.item_borrow_log, hisBorrowListBeans);

            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (Integer.parseInt(hisBorrowListBeans.get(position).getLoan_status()) == 9) {
                        //  跳转到审批拒件
                        Intent intent = new Intent(mContext, HandleFailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", hisBorrowListBeans.get(position));
                        bundle.putString("jumpFlag", "brrow");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            });
            rvMain.setAdapter(adapter);

            //定位
            moveToPosition(new LinearLayoutManager(this), rvMain, hisBorrowListBeans.size() - myBorrow.getResult().getHis_borrow_list().size());
        } else {
            pageNum = 1;
            AppToast.makeShortToast(mContext, "没有数据了");
        }
    }

    @Override
    public void requestError() {
        swipeContainer.setVisibility(View.GONE);
        llRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestSuccess() {
        swipeContainer.setVisibility(View.VISIBLE);
        llRetry.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_retry)
    public void onViewClicked() {
        mPresenter.getData(NetConstantValues.MY_LOAN, "1", pageSize + "");
    }

    @Override
    public void onLoadMore() {
        swipeContainer.refreshComplete();
    }

    @Override
    public void onRefreshing() {
        swipeContainer.refreshComplete();
        mPresenter.getData(NetConstantValues.MY_LOAN, "1", pageSize + "");
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager       设置RecyclerView对应的manager
     * @param mRecyclerView 当前的RecyclerView
     * @param n             要跳转的位置
     */
    private void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }
}

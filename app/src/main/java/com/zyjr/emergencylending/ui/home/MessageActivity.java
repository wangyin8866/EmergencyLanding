package com.zyjr.emergencylending.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.SwipeAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.ui.home.View.MessageView;
import com.zyjr.emergencylending.ui.home.presenter.MessagePresenter;
import com.zyjr.emergencylending.utils.AppToast;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wangyin
 * @date 2017/10/11
 */

public class MessageActivity extends BaseActivity<MessagePresenter, MessageView> implements MessageView, SwipeAdapter.onSwipeListener, EasyRefreshLayout.EasyEvent, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_mark)
    TextView tvMark;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    SwipeAdapter myAdapter;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    private int pageNum = 1;
    private List<MessageBean.ResultBean.ResultListBean> messageBeanList;

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.getMessage(NetConstantValues.USER_NEWS, "1");
    }

    private void init() {


        easylayout.addEasyEvent(this);


    }

    @OnClick({R.id.layout_back, R.id.tv_mark})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.tv_mark:
                final CustomerDialog dialog = new CustomerDialog(mContext);
                dialog.deleteMessage(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.message_left:
                                dialog.dismiss();

                                break;
                            case R.id.message_right:
                                dialog.dismiss();
                                mPresenter.updateUserNews(NetConstantValues.UPDATE_USER_NEWS, "", "3", 0);
                                break;
                        }
                    }
                }).show();

                break;
        }
    }

    @Override
    public void onDel(int pos) {


        mPresenter.updateUserNews(NetConstantValues.UPDATE_USER_NEWS, messageBeanList.get(pos).getNews_id(), "2", pos);

    }

    @Override
    public void onLoadMore() {
        easylayout.loadMoreComplete();
        pageNum += 1;
        mPresenter.getMessageMore(NetConstantValues.USER_NEWS, pageNum + "");

    }

    @Override
    public void onRefreshing() {
        easylayout.refreshComplete();
        mPresenter.getMessage(NetConstantValues.USER_NEWS, "1");

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        mPresenter.updateUserNews(NetConstantValues.UPDATE_USER_NEWS, messageBeanList.get(position).getNews_id(), "1", position);

    }


    @Override
    public void getMessage(MessageBean messageBean) {

        if (messageBean.getResult().getResultList().size() == 0) {
            easylayout.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
            tvMark.setEnabled(false);
        } else {
            tvMark.setEnabled(true);
            easylayout.setVisibility(View.VISIBLE);

            llEmpty.setVisibility(View.GONE);
            messageBeanList = messageBean.getResult().getResultList();
            myAdapter = new SwipeAdapter(R.layout.item_message_swipe, messageBeanList);

            rvMain.setLayoutManager(new LinearLayoutManager(this));
            rvMain.setAdapter(myAdapter);
            myAdapter.setOnItemChildClickListener(this);
            myAdapter.setOnDelListener(this);
        }

    }

    @Override
    public void getMessageMore(final MessageBean messageBean) {

        if (messageBean.getResult().getResultList().size() == 0) {
            pageNum = 1;
            AppToast.makeShortToast(mContext, "没有数据了");
        } else {

            messageBeanList.addAll(messageBean.getResult().getResultList());
            myAdapter = new SwipeAdapter(R.layout.item_message_swipe, messageBeanList);
            rvMain.setLayoutManager(new LinearLayoutManager(this));
            rvMain.setAdapter(myAdapter);
            myAdapter.setOnItemChildClickListener(this);
            myAdapter.setOnDelListener(this);
            //定位
            moveToPosition(new LinearLayoutManager(this), rvMain, messageBeanList.size() - messageBean.getResult().getResultList().size());
        }
    }

    @Override
    public void updateMessage(String opr_type, int position) {
        switch (opr_type) {
            case "1":
                Intent intent = new Intent(mContext, MessageDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", messageBeanList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case "2":
                LogUtils.e(position + "wy");
                myAdapter.getData().remove(position);
                myAdapter.notifyItemRemoved(position);
                if (position != (myAdapter.getData().size())) {
                    myAdapter.notifyItemRangeChanged(position, myAdapter.getData().size() - position);
                }
                break;
            case "3":
                myAdapter.getData().removeAll(messageBeanList);

                mPresenter.getMessage(NetConstantValues.USER_NEWS, "1");
                break;
        }
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

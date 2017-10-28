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
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.SwipeAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.ui.home.View.MessageView;
import com.zyjr.emergencylending.ui.home.presenter.MessagePresenter;
import com.zyjr.emergencylending.utils.AppToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", NetConstantValues.USER_NEWS);
        map.put("pageNo", "1");
        mPresenter.getMessage(map);
    }

    private void init() {


        easylayout.addEasyEvent(this);


    }

    @OnClick({R.id.iv_back, R.id.tv_mark})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
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
                                Map<String, String> map = new HashMap<String, String>(2);
                                map.put("router", NetConstantValues.UPDATE_USER_NEWS);
                                map.put("opr_type", "3");
                                mPresenter.updateUserNews(map);
                                break;
                            case R.id.message_right:
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();

                break;
        }
    }

    @Override
    public void onDel(int pos) {
        myAdapter.getData().remove(pos);
        myAdapter.notifyItemRemoved(pos);
        if (pos != (myAdapter.getData().size())) {
            myAdapter.notifyItemRangeChanged(pos, myAdapter.getData().size() - pos);
        }
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("router", NetConstantValues.UPDATE_USER_NEWS);
        map.put("news_id", messageBeanList.get(pos).getNews_id());
        map.put("opr_type", "2");
        mPresenter.updateUserNews(map);

    }

    @Override
    public void onLoadMore() {
        pageNum += 1;
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", NetConstantValues.USER_NEWS);
        map.put("pageNo", pageNum + "");
        mPresenter.getMessageMore(map);

    }

    @Override
    public void onRefreshing() {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", NetConstantValues.USER_NEWS);
        map.put("pageNo", "1");
        mPresenter.getMessage(map);

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("router", NetConstantValues.UPDATE_USER_NEWS);
        map.put("news_id", messageBeanList.get(position).getNews_id());
        map.put("opr_type", "1");
        mPresenter.updateUserNews(map);
        Intent intent = new Intent(mContext, MessageDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("message",messageBeanList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void getMessage(MessageBean messageBean) {
        easylayout.refreshComplete();
        if (messageBean.getResult().getResultList().size() == 0) {
            easylayout.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
        } else {
            easylayout.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
            if (!(messageBean.getResult().getResultList().size() > Config.PAGE_SIZE)) {
                //隐藏上拉加载
                easylayout.setLoadMoreModel(LoadModel.NONE);
            } else {
                //显示上拉加载
                easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
            }
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
        easylayout.loadMoreComplete();
        if (messageBean.getResult().getResultList().size() == 0) {
            pageNum = 1;
            AppToast.makeShortToast(mContext, "没有数据了");
            //隐藏上拉加载
            easylayout.setLoadMoreModel(LoadModel.NONE);
        } else {
            //显示上拉加载
            easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);

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
    public void updateMessage(BaseBean baseBean) {

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

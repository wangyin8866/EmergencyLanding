package com.zyjr.emergencylending.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.SwipeAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.MessageBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyin on 2017/10/11.
 */

public class MessageActivity extends BaseActivity implements SwipeAdapter.onSwipeListener, EasyRefreshLayout.EasyEvent, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_mark)
    TextView tvMark;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    SwipeAdapter myAdapter;

    private int pageNum = 1;
    private int pageSize;
    private List<MessageBean> messageBeanList;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        init();

    }

    private void init() {


        easylayout.setLoadMoreModel(LoadModel.NONE);
        easylayout.addEasyEvent(this);
        messageBeanList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            MessageBean messageBean = new MessageBean("test" + i, new Date().toString(), "wangyin" + i);
            messageBeanList.add(messageBean);
        }
        myAdapter = new SwipeAdapter(R.layout.item_message_swipe, messageBeanList);

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(myAdapter);
        myAdapter.setOnItemChildClickListener(this);
        myAdapter.setOnDelListener(this);


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
        Toast.makeText(MessageActivity.this, pos + "c", Toast.LENGTH_SHORT).show();
        myAdapter.getData().remove(pos);
        myAdapter.notifyItemRemoved(pos);
        if (pos != (myAdapter.getData().size())) {
            myAdapter.notifyItemRangeChanged(pos, myAdapter.getData().size() - pos);
        }
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefreshing() {
        easylayout.refreshComplete();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(mContext, MessageDetail.class));
    }
}

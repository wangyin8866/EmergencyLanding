package com.zyjr.emergencylending.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
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

public class MessageActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_mark)
    TextView tvMark;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;


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

        messageBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MessageBean messageBean = new MessageBean("test" + i, new Date().toString(), "wangyin" + i);
            messageBeanList.add(messageBean);
        }
        rvMain = (RecyclerView) findViewById(R.id.rv_main);
        easylayout = (EasyRefreshLayout) findViewById(R.id.easylayout);

        final SwipeAdapter myAdapter = new SwipeAdapter(R.layout.item_message_swipe, messageBeanList);

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(myAdapter);
//        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(MessageActivity.this, position + "a", Toast.LENGTH_SHORT).show();
//            }
//        });
        myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                startActivity(new Intent(mContext, MessageDetail.class));
            }
        });
//        myAdapter.addHeaderView(View.inflate(this, R.layout.header, null));
        myAdapter.setOnDelListener(new SwipeAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos) {
                Toast.makeText(MessageActivity.this, pos + "c", Toast.LENGTH_SHORT).show();
                myAdapter.getData().remove(pos - 1);
                myAdapter.notifyItemRemoved(pos);//推荐用这个
                if (pos != (myAdapter.getData().size())) { // 如果移除的是最后一个，忽略 注意：这里的mDataAdapter.getDataList()不需要-1，因为上面已经-1了
                    myAdapter.notifyItemRangeChanged(pos, myAdapter.getData().size() - pos);
                }
            }
        });
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<MessageBean> list = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            MessageBean messageBean = new MessageBean("test" + i, new Date().toString(), "wangyin" + i);
                            messageBeanList.add(messageBean);
                        }

//                        myAdapter.addData(mList);

                        easylayout.loadMoreComplete(new EasyRefreshLayout.Event() {
                            @Override
                            public void complete() {
                                myAdapter.addData(list);
                                myAdapter.getData().addAll(list);
                                myAdapter.notifyDataSetChanged();

                            }
                        }, 500);

                    }
                }, 2000);


            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<MessageBean> list = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            MessageBean user = new MessageBean("test" + i, new Date().toString(), "wangyin" + i);
                            list.add(user);
                        }
                        myAdapter.setNewData(list);
                        easylayout.refreshComplete();
                        Toast.makeText(getApplicationContext(), "refresh success", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);

            }
        });


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
}

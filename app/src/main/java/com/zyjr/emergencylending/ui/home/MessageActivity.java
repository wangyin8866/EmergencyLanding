package com.zyjr.emergencylending.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.SwipeAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.ui.home.presenter.MessagePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * @author wangyin
 * @date 2017/10/11
 */

public class MessageActivity extends BaseActivity<MessagePresenter, BaseView<MessageBean>> implements BaseView<MessageBean>, SwipeAdapter.onSwipeListener, EasyRefreshLayout.EasyEvent, BaseQuickAdapter.OnItemChildClickListener {

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
    private int pageSize;
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

    private void init() {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", NetConstantValues.USER_NEWS);
        map.put("pageNo", "1");
        mPresenter.getMessage(map);


        easylayout.setLoadMoreModel(LoadModel.NONE);
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

    @Override
    public void callBack(MessageBean baseBean) {
        messageBeanList = baseBean.getResult().getResultList();

        myAdapter = new SwipeAdapter(R.layout.item_message_swipe, messageBeanList);

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(myAdapter);
        myAdapter.setOnItemChildClickListener(this);
        myAdapter.setOnDelListener(this);
    }
}

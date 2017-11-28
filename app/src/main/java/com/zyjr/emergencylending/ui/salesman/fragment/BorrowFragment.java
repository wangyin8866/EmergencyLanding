package com.zyjr.emergencylending.ui.salesman.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.AutoVerticalScrollTextView;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.entity.NoticeBean;
import com.zyjr.emergencylending.ui.home.MessageActivity;
import com.zyjr.emergencylending.ui.home.QrCodeActivity;
import com.zyjr.emergencylending.ui.salesman.activity.ImmediatelyBorrowActivity;
import com.zyjr.emergencylending.ui.salesman.presenter.BorrowPresenter;
import com.zyjr.emergencylending.ui.salesman.view.MessageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author wangyin
 * @date 2017/8/9
 */

public class BorrowFragment extends BaseFragment<BorrowPresenter, MessageView> implements MessageView, EasyRefreshLayout.EasyEvent {
    @BindView(R.id.swipe_container)
    EasyRefreshLayout easyRefreshLayout;
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.notice_auto_roll)
    AutoVerticalScrollTextView noticeAutoRoll;
    @BindView(R.id.notice_close)
    ImageView noticeClose;
    @BindView(R.id.QR_code)
    ImageView QRCode;
    @BindView(R.id.visiting_card)
    ImageView visitingCard;
    @BindView(R.id.activity)
    ImageView activity;
    @BindView(R.id.invest)
    LinearLayout invest;
    @BindView(R.id.handle)
    LinearLayout handle;
    @BindView(R.id.buy)
    LinearLayout buy;
    Unbinder unbinder;
    @BindView(R.id.more_dynamic)
    LinearLayout moreDynamic;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;
    @BindView(R.id.message_auto_roll)
    AutoVerticalScrollTextView messageAutoRoll;
    private int autoRollIndex;
    private int autoRollIndex2;
    private List<String> auto_roll_data;
    private List<String> auto_roll_data2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_fragment_borrow_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        easyRefreshLayout.addEasyEvent(this);
        //隐藏上拉加载
        easyRefreshLayout.setLoadMoreModel(LoadModel.NONE);
        return view;
    }

    protected void init() {

        mPresenter.getNoticeList(NetConstantValues.NOTICE_LIST, "3");
        mPresenter.getMessage(NetConstantValues.USER_NEWS, "1");

    }

    private void showAutoRollStrings() {
        noticeAutoRoll.setTText(auto_roll_data.get(0));
        handler.sendEmptyMessage(199);

    }

    private void showAutoRollStrings2() {
        messageAutoRoll.setTText2(auto_roll_data2.get(0));
        handler.sendEmptyMessage(200);

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                noticeAutoRoll.next();
                autoRollIndex++;
                noticeAutoRoll.setTText(auto_roll_data.get(autoRollIndex % auto_roll_data.size()));
                handler.sendEmptyMessageDelayed(199, 2000);
            } else if (msg.what == 200) {
                messageAutoRoll.next();
                autoRollIndex2++;
                messageAutoRoll.setTText2(auto_roll_data2.get(autoRollIndex2 % auto_roll_data2.size()));
                handler.sendEmptyMessageDelayed(200, 2000);
            }
        }
    };

    @Override
    protected BorrowPresenter createPresenter() {
        return new BorrowPresenter(mContext);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        unbinder.unbind();
    }

    @OnClick({R.id.notice_close, R.id.QR_code, R.id.visiting_card, R.id.activity, R.id.invest, R.id.handle, R.id.buy, R.id.more_dynamic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notice_close:
                llNotice.setVisibility(View.GONE);
                break;
            case R.id.QR_code:
                startActivity(new Intent(mContext, QrCodeActivity.class));
                break;
            case R.id.visiting_card:
                mPresenter.getH5Url(Config.H5_URL_MYCARD, "我的名片");
                break;
            case R.id.activity:
                mPresenter.getH5Url(Config.H5_URL_ACTIVITYLIST, "活动");
                break;
            case R.id.invest:
                mPresenter.getH5Url(Config.H5_URL_INVITE, "邀请有奖");
                break;
            case R.id.more_dynamic:
                startActivity(new Intent(mContext, MessageActivity.class));
                break;
            case R.id.handle:
                startActivity(new Intent(mContext, ImmediatelyBorrowActivity.class));
                break;
            case R.id.buy:
                break;
        }
    }

    @Override
    public void getCommonData(NoticeBean baseBean) {
        auto_roll_data = new ArrayList<>();
        if (baseBean.getResult().getResultList().size() > 0) {
            for (int i = 0; i < baseBean.getResult().getResultList().size(); i++) {
                auto_roll_data.add(baseBean.getResult().getResultList().get(i).getTitle());
            }

        } else {
            auto_roll_data.add("没有消息");
        }
        showAutoRollStrings();
    }

    @Override
    public void requestError() {

    }

    @Override
    public void requestSuccess() {

    }

    @Override
    public void getMessage(MessageBean messageBean) {
        auto_roll_data2 = new ArrayList<>();
        for (int i = 0; i < messageBean.getResult().getResultList().size(); i++) {
            auto_roll_data2.add(messageBean.getResult().getResultList().get(i).getNews_title());
        }
        if (auto_roll_data2.size() == 0) {
            auto_roll_data2.add("没有最新动态");
        }
        showAutoRollStrings2();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefreshing() {
        easyRefreshLayout.refreshComplete();

        mPresenter.getNoticeList(NetConstantValues.NOTICE_LIST, "3");
        mPresenter.getMessage(NetConstantValues.USER_NEWS, "1");
    }
}

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

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.AutoVerticalScrollTextView;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.ui.home.QrCodeActivity;
import com.zyjr.emergencylending.ui.salesman.activity.ActivityActivity;
import com.zyjr.emergencylending.ui.salesman.activity.ImmediatelyBorrowActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by wangyin on 2017/8/9.
 */

public class BorrowFragment extends BaseFragment {
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
    private int autoRollIndex;
    private List<String> auto_roll_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_fragment_borrow_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    protected void init() {
        auto_roll_data = new ArrayList<>();
        auto_roll_data.add("wangyin");
        auto_roll_data.add("wangyin2");
        auto_roll_data.add("wangyin3");
        auto_roll_data.add("wangyin4");
        showAutoRollStrings();
    }

    private void showAutoRollStrings() {
        noticeAutoRoll.setText(auto_roll_data.get(0));
        handler.sendEmptyMessage(199);

    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                noticeAutoRoll.next();
                autoRollIndex++;
                noticeAutoRoll.setTText(auto_roll_data.get(autoRollIndex % auto_roll_data.size()));
                handler.sendEmptyMessageDelayed(199, 3000);
            }
        }
    };

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        unbinder.unbind();
    }

    @OnClick({R.id.notice_close, R.id.QR_code, R.id.visiting_card, R.id.activity, R.id.invest, R.id.handle, R.id.buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notice_close:
                break;
            case R.id.QR_code:
                startActivity(new Intent(mContext, QrCodeActivity.class));
                break;
            case R.id.visiting_card:
                break;
            case R.id.activity:
                startActivity(new Intent(mContext, ActivityActivity.class));
                break;
            case R.id.invest:
                break;
            case R.id.handle:
                startActivity(new Intent(mContext, ImmediatelyBorrowActivity.class));

                break;
            case R.id.buy:
                break;
        }
    }
}

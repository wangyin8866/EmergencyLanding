package com.zyjr.emergencylending.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.AutoVerticalScrollTextView;
import com.zyjr.emergencylending.custom.LocalImageHolderView;
import com.zyjr.emergencylending.entity.Banner;
import com.zyjr.emergencylending.entity.EffectiveOrderBean;
import com.zyjr.emergencylending.entity.UserInfo;
import com.zyjr.emergencylending.ui.account.LoginActivity;
import com.zyjr.emergencylending.ui.h5.H5WebView;
import com.zyjr.emergencylending.ui.home.View.HomeView;
import com.zyjr.emergencylending.ui.home.loan.LoanMainActivity;
import com.zyjr.emergencylending.ui.home.loan.LoanOrderStatusActivity;
import com.zyjr.emergencylending.ui.home.presenter.HomePresenter;
import com.zyjr.emergencylending.utils.SPUtils;

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

public class HomeFragment extends BaseFragment<HomePresenter, HomeView> implements HomeView, EasyRefreshLayout.EasyEvent {
    @BindView(R.id.banner)
    ConvenientBanner banner;
    @BindView(R.id.QR_code)
    ImageView QRCode;
    @BindView(R.id.message_center)
    ImageView messageCenter;
    @BindView(R.id.notice_img)
    ImageView noticeImg;
    @BindView(R.id.notice_auto_roll)
    AutoVerticalScrollTextView noticeAutoRoll;
    Unbinder unbinder;
    @BindView(R.id.swipe_container)
    EasyRefreshLayout easyRefreshLayout;
    private View view;
    private int autoRollIndex;
    private List<String> auto_roll_data;
    private ArrayList<String> images;
    private String is_effective_order;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();


        easyRefreshLayout.addEasyEvent(this);
        //隐藏上拉加载
        easyRefreshLayout.setLoadMoreModel(LoadModel.NONE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //banner
        mPresenter.getHomeAds(NetConstantValues.HOME_AD);


        //是否有消息
        if (SPUtils.getBoolean(mContext, Config.KEY_LOGIN, false)) {
            mPresenter.getBasicInfo(NetConstantValues.GET_BASIC_INFO);
            mPresenter.isEffectiveOrder(NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
        }
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
        @Override
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        handler.removeCallbacksAndMessages(null);
    }

    @OnClick({R.id.QR_code, R.id.message_center, R.id.pro1_btn, R.id.pro2_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.QR_code:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN, false)) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), QrCodeActivity.class));
                }
                break;
            case R.id.message_center:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN, false)) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {

                    startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;
            case R.id.pro1_btn:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN, false)) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else if (Config.TRUE.equals(is_effective_order)) {
                    startActivity(new Intent(mContext, LoanOrderStatusActivity.class));
                } else {
                    Intent intent = new Intent(getActivity(), LoanMainActivity.class);
                    intent.putExtra("flag", "online");
                    startActivity(intent);
                }
                break;
            case R.id.pro2_btn:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN, false)) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else if (Config.TRUE.equals(is_effective_order)) {
                    startActivity(new Intent(mContext, LoanOrderStatusActivity.class));
                } else {
                    Intent intent1 = new Intent(getActivity(), LoanMainActivity.class);
                    intent1.putExtra("flag", "offline");
                    startActivity(intent1);
                }
                break;
        }
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(mContext);
    }


    @Override
    public void getBanner(final Banner banner) {
        if (images != null) {
            images.clear();
        }
        images = new ArrayList<>();
        for (int i = 0; i < banner.getResult().getAd_list().size(); i++) {
            images.add(banner.getResult().getAd_list().get(i).getAd_pic());
        }
        if (images.size() > 1) {
            this.banner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new LocalImageHolderView();
                }
            }, images).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused}).startTurning(2000).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    H5WebView.skipH5WebView(mContext, banner.getResult().getAd_list().get(position).getTitle(), banner.getResult().getAd_list().get(position).getAd_url());
                }
            });
        } else {
            this.banner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new LocalImageHolderView();
                }
            }, images).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused}).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    H5WebView.skipH5WebView(mContext, banner.getResult().getAd_list().get(position).getTitle(), banner.getResult().getAd_list().get(position).getAd_url());
                }
            });
        }


    }

    @Override
    public void getBasicInfo(UserInfo userInfo) {
        if (Config.TRUE.equals(userInfo.getResult().getNews_status())) {
            messageCenter.setImageResource(R.mipmap.icon_message_reddot);
        } else {
            messageCenter.setImageResource(R.mipmap.icon_message);
        }
    }

    @Override
    public void isEffectiveOrder(EffectiveOrderBean baseBean) {
        is_effective_order = baseBean.getResult().getIs_effective_order();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefreshing() {
        easyRefreshLayout.refreshComplete();
        //banner
        mPresenter.getHomeAds(NetConstantValues.HOME_AD);

        //是否有消息
        if (SPUtils.getBoolean(mContext, Config.KEY_LOGIN, false)) {
            mPresenter.getBasicInfo(NetConstantValues.GET_BASIC_INFO);
            mPresenter.isEffectiveOrder(NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
        }
    }
}

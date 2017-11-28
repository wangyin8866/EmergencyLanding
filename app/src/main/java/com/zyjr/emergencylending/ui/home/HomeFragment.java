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
import com.zyjr.emergencylending.config.Constants;
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
import com.zyjr.emergencylending.ui.home.loan.WriteInfoMainActivity;
import com.zyjr.emergencylending.ui.home.presenter.HomePresenter;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.WYUtils;

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
    private String turnFlag = "";
    private String[] surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王",
            "冯", "陈", "卫", "沈", "杨", "朱", "尤", "何",
            "吕", "张"};
    private String[] sex = {"先生", "女士"};
    int[] types = {0, 1};

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

        //版本更新
        if (Constants.update) {
            WYUtils.upDateVersion(mContext, NetConstantValues.VERSION_UPDATE, false);
        }
        //banner
        mPresenter.getHomeAds(NetConstantValues.HOME_AD);
        //是否有消息
        if (SPUtils.getBoolean(mContext, Config.KEY_LOGIN)) {
            mPresenter.getBasicInfo(NetConstantValues.GET_BASIC_INFO);
        }
    }

    protected void init() {


        auto_roll_data = new ArrayList<>();


        for (int i = 0; i < surname.length; i++) {

            String CarouselFamilyName = surname[WYUtils.getOneRandom(surname.length)];
            String CarouselSex = sex[WYUtils.getOneRandom(sex.length)];
            int CarouselType = types[WYUtils.getOneRandom(types.length)];

            if (CarouselType == 0) {
                int random = WYUtils.getOneRandom(28);
                int money = 2000 + random * 1000;
                auto_roll_data.add("喜讯  " + CarouselFamilyName + CarouselSex + "  成功借款" + money + "元");
            } else {
                String inviteFamilyName = surname[WYUtils.getOneRandom(surname.length)];
                String inviteSex = sex[WYUtils.getOneRandom(sex.length)];
                auto_roll_data.add(CarouselFamilyName + CarouselSex + "  成功邀请  " + inviteFamilyName + inviteSex);
            }

        }


        showAutoRollStrings();

        noticeAutoRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN)) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    if (noticeAutoRoll.getText().contains("邀请")) {
                        startActivity(new Intent(getActivity(), QrCodeActivity.class));
                    }
                }

            }
        });
    }

    private void showAutoRollStrings() {
        noticeAutoRoll.setTText(auto_roll_data.get(0));
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

    @OnClick({R.id.cl_QR_code, R.id.cl_message_center, R.id.pro1_btn, R.id.pro2_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cl_QR_code:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN)) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), QrCodeActivity.class));
                }
                break;
            case R.id.cl_message_center:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN)) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;
            case R.id.pro1_btn:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN)) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    turnFlag = "online";
                    mPresenter.isEffectiveOrder(NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
                }
                break;
            case R.id.pro2_btn:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN)) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    turnFlag = "offline";
                    mPresenter.isEffectiveOrder(NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
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
            }, images).setPageIndicator(new int[]{R.mipmap.carouselspot_off, R.mipmap.carouselspot_on}).startTurning(2000).setOnItemClickListener(new OnItemClickListener() {
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
            }, images).setPageIndicator(new int[]{R.mipmap.carouselspot_off, R.mipmap.carouselspot_on}).setOnItemClickListener(new OnItemClickListener() {
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
        if (Config.TRUE.equals(is_effective_order)) {
            startActivity(new Intent(mContext, LoanOrderStatusActivity.class));
        } else {
            if (turnFlag.equals("online")) {
                Intent intent = new Intent(getActivity(), WriteInfoMainActivity.class);
                intent.putExtra("online_type", "0");
                intent.putExtra("product_id", "0");
                startActivity(intent);
            } else if (turnFlag.equals("offline")) {
                Intent intent = new Intent(getActivity(), LoanMainActivity.class);
                intent.putExtra("flag", "offline");
                startActivity(intent);
            }
        }
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
        if (SPUtils.getBoolean(mContext, Config.KEY_LOGIN)) {
            mPresenter.getBasicInfo(NetConstantValues.GET_BASIC_INFO);
        }
    }
}

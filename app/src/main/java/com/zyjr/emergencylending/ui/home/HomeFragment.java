package com.zyjr.emergencylending.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class HomeFragment extends BaseFragment<HomePresenter, HomeView> implements HomeView {
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
    private View view;
    private int autoRollIndex;
    private List<String> auto_roll_data;
    private ArrayList<String> images;
    private String is_effective_order;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
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


        //本地图片
//        ArrayList<Integer> imgs = new ArrayList<>();
//        imgs.add(R.mipmap.ic_launcher);
//        imgs.add(R.mipmap.ic_launcher_round);
//        imgs.add(R.mipmap.ic_launcher);
//        this.banner.setPages(new CBViewHolderCreator() {
//            @Override
//            public Object createHolder() {
//                return new LocalImageHolderViewNative();
//            }
//        }, imgs).startTurning(2000);

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        handler.removeCallbacksAndMessages(null);
    }

    @OnClick({R.id.QR_code, R.id.message_center, R.id.pro1_btn, R.id.pro2_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.QR_code:
                startActivity(new Intent(getActivity(), QrCodeActivity.class));
                break;
            case R.id.message_center:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.pro1_btn:
                if (Config.TRUE.equals(is_effective_order)) {
                    startActivity(new Intent(mContext, LoanOrderStatusActivity.class));
                } else {
                    Intent intent = new Intent(getActivity(), LoanMainActivity.class);
                    intent.putExtra("flag", "online");
                    startActivity(intent);
                }
                break;
            case R.id.pro2_btn:
                if (Config.TRUE.equals(is_effective_order)) {
                    startActivity(new Intent(mContext, LoanOrderStatusActivity.class));
                } else {
                    Intent intent1 = new Intent(getActivity(), LoanOrderStatusActivity.class);
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
    public void getBanner(Banner banner) {
        if (images != null) {
            images.clear();
        }
        images = new ArrayList<String>();
        for (int i = 0; i < banner.getResult().getAd_list().size(); i++) {
            images.add(banner.getResult().getAd_list().get(i).getAd_pic());
        }
        this.banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        }, images).startTurning(2000);

        this.banner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


            }
        });
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
}

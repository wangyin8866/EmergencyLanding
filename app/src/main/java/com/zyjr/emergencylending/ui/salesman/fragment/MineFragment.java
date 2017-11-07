package com.zyjr.emergencylending.ui.salesman.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.GlideApp;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.GlideCircleTransform;
import com.zyjr.emergencylending.entity.CardBean;
import com.zyjr.emergencylending.ui.home.MessageActivity;
import com.zyjr.emergencylending.ui.my.SettingActivity;
import com.zyjr.emergencylending.ui.salesman.activity.EditInformation;
import com.zyjr.emergencylending.ui.salesman.presenter.MinePresenter;
import com.zyjr.emergencylending.ui.salesman.view.MineView;
import com.zyjr.emergencylending.utils.UIUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author wangyin
 * @date 2017/8/9
 */

public class MineFragment extends BaseFragment<MinePresenter, MineView> implements MineView {
    @BindView(R.id.edit_information)
    ImageView editInformation;
    @BindView(R.id.ll_starts)
    LinearLayout llStarts;
    @BindView(R.id.income)
    TextView income;
    @BindView(R.id.message_center)
    TextView messageCenter;
    @BindView(R.id.my_repayment)
    TextView myRepayment;
    @BindView(R.id.help)
    TextView help;
    @BindView(R.id.setting)
    TextView setting;
    Unbinder unbinder;
    @BindView(R.id.user_pic)
    ImageView userPic;
    @BindView(R.id.user_name_phone)
    TextView userNamePhone;
    @BindView(R.id.user_position)
    TextView userPosition;
    private CardBean.ResultBean resultBean;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_fragment_my_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        //创建星级
        addGroupImage(3);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.myCard(NetConstantValues.MY_CARD);
    }

    private void addGroupImage(int size) {
        llStarts.removeAllViews();  //clear linearlayout
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            //设置图片宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(UIUtils.dip2px(5), 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
            //图片资源
            imageView.setImageResource(R.mipmap.icon_star);
            //动态添加图片
            llStarts.addView(imageView);
        }
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(mContext);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_pic, R.id.message_center, R.id.my_repayment, R.id.help, R.id.setting, R.id.income, R.id.edit_information})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_pic:
                break;
            case R.id.message_center:
                startActivity(new Intent(mContext, MessageActivity.class));
                break;
            case R.id.my_repayment:
                break;
            case R.id.help:
                mPresenter.getH5Url(Config.H5_URL_CLERKHELP, "帮助说明");

                break;
            case R.id.setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.income:
                mPresenter.getH5Url(Config.H5_URL_MYINCOME, "我的收入");
                break;
            case R.id.edit_information:
                Intent intent = new Intent(mContext, EditInformation.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("information", resultBean);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
        }
    }


    @Override
    public void myCard(CardBean baseBean) {
        resultBean = baseBean.getResult();
        GlideApp.with(mContext).load(resultBean.getHead_url()).placeholder(R.mipmap.head_portrait).error(R.mipmap.head_portrait).transform(new GlideCircleTransform(mContext)).into(userPic);
        if (!TextUtils.isEmpty(resultBean.getName())) {
            userNamePhone.setText(WYUtils.nameSecret(resultBean.getName()) + " " + WYUtils.phoneSecret(resultBean.getPhone()));
        } else {
            userNamePhone.setText("未实名" + " " + WYUtils.phoneSecret(resultBean.getPhone()));
        }
        if (!TextUtils.isEmpty(resultBean.getPosition())) {
            userPosition.setText(resultBean.getPosition());
        } else {
            userPosition.setText("客户经理");
        }
    }
}

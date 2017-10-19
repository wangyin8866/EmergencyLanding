package com.zyjr.emergencylending.ui.salesman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.CircleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 *
 * @author wangyin
 * @date 2017/8/9
 */

public class CustomerFragment extends BaseFragment {
    @BindView(R.id.rg_title)
    RadioGroup rgTitle;
    @BindView(R.id.circle_client)
    CircleView circleClient;
    @BindView(R.id.circle_apply_for)
    CircleView circleApplyFor;
    @BindView(R.id.circle_success)
    CircleView circleSuccess;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_fragment_customer_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        circleClient.setData(1000, 1000);
        circleApplyFor.setData(800, 1000);
        circleSuccess.setData(500, 1000);
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}

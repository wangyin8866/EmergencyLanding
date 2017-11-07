package com.zyjr.emergencylending.ui.repayment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyjr.emergencylending.R;


/**
 *
 * @author wangyin
 * @date 2017/8/9
 */

public class RepaymentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_main,container,false);
        return view;
    }
}

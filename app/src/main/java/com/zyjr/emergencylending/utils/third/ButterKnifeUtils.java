package com.zyjr.emergencylending.utils.third;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import butterknife.ButterKnife;

/**
 * ButterKnife框架的初始化
 */
public class ButterKnifeUtils {

    public static void bind(Activity activity){
        ButterKnife.bind(activity);
    }

    public static void bind(Object obj, View view){
        ButterKnife.bind(obj, view);
    }

    public static void bind(Dialog dialog){
        ButterKnife.bind(dialog);
    }

}

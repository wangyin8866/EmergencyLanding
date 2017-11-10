package com.zyjr.emergencylending.custom;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.zyjr.emergencylending.R;

/**
 * Created by wangyin on 2017/5/19.
 */


public class LocalImageHolderView implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Glide.with(context).load(data).placeholder(R.mipmap.jjt_banner).error(R.mipmap.jjt_banner).into(imageView);
    }
}

package com.zyjr.emergencylending.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;


/**
 * Created by neil on 2017/2/27.
 */
public class SettingItemView extends FrameLayout {

    private ImageView ivLeftIcon; // 左侧icon
    private TextView tvLeftContent; // 左侧内容
    private ImageView ivRightIcon; // 右侧icon
    private TextView tvRightContent; // 右侧内容

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_setting_item, this);
        ivLeftIcon = (ImageView) view.findViewById(R.id.iv_left_icon);
        tvLeftContent = (TextView) view.findViewById(R.id.tv_left_content);
        ivRightIcon = (ImageView) view.findViewById(R.id.iv_right_icon);
        tvRightContent = (TextView) view.findViewById(R.id.tv_right_content);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        int leftIcon = a.getResourceId(R.styleable.SettingItemView_setting_left_icon, -1);
        String leftContent = a.getString(R.styleable.SettingItemView_setting_left_content);
        int rightIcon = a.getResourceId(R.styleable.SettingItemView_setting_right_icon, -1);
        String rightContent = a.getString(R.styleable.SettingItemView_setting_right_content);
        if (leftIcon != -1) {
            ivLeftIcon.setImageResource(leftIcon);
        }
        if (!TextUtils.isEmpty(leftContent)) {
            tvLeftContent.setText(leftContent);
        }
        if (rightIcon != -1) {
            ivRightIcon.setImageResource(rightIcon);
        }
        if (!TextUtils.isEmpty(rightContent)) {
            tvRightContent.setText(rightContent);
        }
        a.recycle();
    }

    public void setLeftIcon(int resId) {
        ivLeftIcon.setImageResource(resId);
    }

    public void setLeftContentDrawable(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvLeftContent.setCompoundDrawables(drawable, null, null, null);
    }

    public void setRightIcon(int resId) {
        ivRightIcon.setImageResource(resId);
    }

    public void setRightContentDrawable(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvRightContent.setCompoundDrawables(drawable, null, null, null);
    }

    public void setRightContentVisible(boolean visible) {
        tvRightContent.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setLeftContent(String msg, int color) {
        tvLeftContent.setText(msg);
        tvLeftContent.setTextColor(color);
    }

    public void setRightContent(String msg, int color) {
        tvRightContent.setText(msg);
        tvRightContent.setTextColor(color);
    }
}

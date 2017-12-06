package com.zyjr.emergencylending.ui.h5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 沉浸式 H5
 *
 * @author neil
 * @date 2017/12/6
 */
public class ImmersionH5WebView extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tool_bar_left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.layout_left_icon)
    LinearLayout layoutLeftIcon; // 左侧icon
    @BindView(R.id.iv_left_icon)
    ImageView ivLeftIcon;
    @BindView(R.id.tv_left_title)
    TextView tvLeftTitle;
    @BindView(R.id.tool_bar_center_layout)
    LinearLayout centerLayout;  // 中间布局
    @BindView(R.id.tv_center_title)
    TextView tvCenterTitle; // 标题
    @BindView(R.id.tool_bar_right_layout)
    LinearLayout rightLayout;  // 右侧布局
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle; // 右侧标题
    @BindView(R.id.layout_right_icon)
    LinearLayout layoutRightIcon; // 右侧icon
    @BindView(R.id.iv_right_icon)
    ImageView ivRightIcon;
    @BindView(R.id.tool_bar_view_divider)
    View viewDivider;  // 分割线

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    protected ImmersionBar mImmersionBar;
    private String title = ""; // title
    private String rightFlag = ""; // 右边icon

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    /**
     * @param title
     * @param url
     * @param rightFlag 右侧内容操作 默认不显示
     */
    public static void jump(Context context, String title, String url, String rightFlag) {
        Intent intent = new Intent(context, ImmersionH5WebView.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("rightFlag", rightFlag);
        context.startActivity(intent);
    }

    /**
     * 包含拨打电话
     */
    public static void jumpIncludeCall(Context context, String title, String url) {
        Intent intent = new Intent(context, ImmersionH5WebView.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("isInclude", "1");
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.immersion_web_view);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        ImmersionBar.with(this).titleBar(toolbar, false).transparentStatusBar().statusBarDarkFont(true, 0.2f).init();
        init();
        initListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mWebView != null) {
                mWebView.removeAllViews();
                mWebView.destroy();
                mWebView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() {
        title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        String isInclude = getIntent().getStringExtra("isInclude");
        rightFlag = getIntent().getStringExtra("rightFlag");
        setCenterTitle(title);
        if (StringUtil.isNotEmpty(rightFlag)) {
            setRightIconVisibility(true);
        }
        if (StringUtil.isNotEmpty(isInclude)) {
            WYUtils.loadHtmlIncludeCall(url, mWebView, mProgressBar);
        } else {
            WYUtils.loadHtmlNew(url, mWebView, mProgressBar);
        }
    }

    private void initListener(){
        leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    /** 设置左侧icon是否可见  */
    protected void setLeftIconVisibility(boolean visible) {
        layoutLeftIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /** 设置左侧icon  */
    protected void setLeftIcon(int resId) {
        ivLeftIcon.setImageResource(resId);
    }

    /** 设置左侧title  */
    protected void setLeftTitle(String titile) {
        tvLeftTitle.setText(titile);
    }

    /** 设置左侧title及颜色  */
    protected void setLeftTitleAndColor(String titile, int color) {
        tvLeftTitle.setText(titile);
        tvLeftTitle.setTextColor(color);
    }

    /** 设置中间title  */
    protected void setCenterTitle(String titile) {
        tvCenterTitle.setText(titile);
    }

    /** 设置中间title及颜色  */
    protected void setCenterTitleAndColor(String titile, int color) {
        tvCenterTitle.setText(titile);
        tvCenterTitle.setTextColor(color);
    }

    /** 设置右侧icon是否可见  */
    protected void setRightIconVisibility(boolean visible) {
        layoutRightIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /** 设置右侧icon  */
    protected void setRightIcon(int resId) {
        ivRightIcon.setImageResource(resId);
    }

    /** 设置右侧title  */
    protected void setRightTitle(String titile) {
        tvRightTitle.setText(titile);
    }

    /** 设置右侧title及颜色  */
    protected void setRightTitleAndColor(String titile, int color) {
        tvRightTitle.setText(titile);
        tvRightTitle.setTextColor(color);
    }



}

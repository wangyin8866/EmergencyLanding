package com.zyjr.emergencylending.ui.salesman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.ui.h5.H5WebView;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyin
 * @date 2017/11/13.
 * @description :
 */

public class ApplyActivity extends BaseActivity {


    @BindView(R.id.go_back)
    View goBack;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected BasePresenter createPresenter() {
        return null;

    }

    public static void skipH5WebView(Context context, String url) {
        Intent intent = new Intent(context, ApplyActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    // 包含拨打电话
    public static void skipH5WebViewIncludeCall(Context context, String url) {
        Intent intent = new Intent(context, ApplyActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isInclude", "1");
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String url = getIntent().getStringExtra("url");
        String isInclude = getIntent().getStringExtra("isInclude");
        if (StringUtil.isNotEmpty(isInclude)) {
            WYUtils.loadHtmlIncludeCall(url, webView, progressBar);
        } else {
            WYUtils.loadHtmlNew(url, webView, progressBar);
        }
    }

}

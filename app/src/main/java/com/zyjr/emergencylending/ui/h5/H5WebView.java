package com.zyjr.emergencylending.ui.h5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author wangyin
 * date 2017/10/25.
 * description :
 */

public class H5WebView extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public static void skipH5WebView(Context context, String title) {
        Intent intent = new Intent(context, H5WebView.class);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        String title = getIntent().getStringExtra("title");
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
        switch (title) {
            case "帮助说明":
                topBar.setTitle(title);
                WYUtils.loadHtml(NetConstantValues.HELP, webView, progressBar);
                break;
            case "我的名片":
                topBar.setTitle(title);
                WYUtils.loadHtml(NetConstantValues.MY_NAME, webView, progressBar);
                break;
            case "我的收入":
                topBar.setTitle(title);
                WYUtils.loadHtml(NetConstantValues.MY_INCOME, webView, progressBar);
                break;
            case "龙虎榜":
                topBar.setTitle(title);
                WYUtils.loadHtml(NetConstantValues.RANKING_LIST, webView, progressBar);
                break;
        }


    }
}

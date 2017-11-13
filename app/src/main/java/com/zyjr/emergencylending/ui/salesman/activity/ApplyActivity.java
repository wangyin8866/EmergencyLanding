package com.zyjr.emergencylending.ui.salesman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyin
 * @date 2017/11/13.
 * @description :
 */

public class ApplyActivity extends BaseActivity {


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

    public static void skipH5WebView(Context context, String url) {
        Intent intent = new Intent(context, ApplyActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {
            }
        });
        String url = getIntent().getStringExtra("url");
        WYUtils.loadHtml(url, webView, progressBar);
    }

}

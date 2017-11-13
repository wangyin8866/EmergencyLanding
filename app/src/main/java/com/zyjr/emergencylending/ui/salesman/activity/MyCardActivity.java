package com.zyjr.emergencylending.ui.salesman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyin
 * @date 2017/11/13.
 * @description :
 */

public class MyCardActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ImmersionBar mImmersionBar;

    @Override
    protected BasePresenter createPresenter() {
        return null;

    }

    public static void skipH5WebView(Context context, String url) {
        Intent intent = new Intent(context, MyCardActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true).init();
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {
                final CustomerDialog customerDialog = new CustomerDialog(mContext);
                customerDialog.cardShareDialog(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.qr_we_chat:
                                customerDialog.dismiss();
                                break;
                            case R.id.circle_of_friends:
                                customerDialog.dismiss();
                                break;
                            case R.id.qq:
                                customerDialog.dismiss();
                                break;
                            case R.id.qq_zone:
                                customerDialog.dismiss();
                                break;
                            case R.id.weibo:
                                customerDialog.dismiss();
                                break;
                            case R.id.share_close:
                                customerDialog.dismiss();
                                break;
                        }
                    }
                }).show();
            }
        });
        String url = getIntent().getStringExtra("url");
        WYUtils.loadHtml(url, webView, progressBar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mContext, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();

        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar.destroy();
        UMShareAPI.get(this).release();
    }
}

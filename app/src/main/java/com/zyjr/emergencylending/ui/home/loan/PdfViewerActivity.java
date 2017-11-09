package com.zyjr.emergencylending.ui.home.loan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;
import com.xfqz.xjd.mylibrary.CustomProgressDialog;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.ui.h5.H5WebView;
import com.zyjr.emergencylending.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * pdf 文件预览
 *
 * @author neil
 * @date 2017/11/8
 */
public class PdfViewerActivity extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    private String title = "";
    private String pdfUrl = "";
    private CustomProgressDialog loadingDialog = null;

    public static void jumpToPdfView(Context context, String title, String url) {
        Intent intent = new Intent(context, PdfViewerActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        ButterKnife.bind(this);

        init();
        initGetData();
    }

    private void init(){
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
    }

    private void initGetData() {
        Intent instent = getIntent();
        title = instent.getStringExtra("title");
        topBar.setTitle(title);
        pdfUrl = instent.getStringExtra("url");
        displayFromUrl(pdfUrl, title);
    }

    /**
     * 获取打开网络的pdf文件
     *
     * @param fileUrl
     * @param fileName
     */
    private void displayFromUrl(String fileUrl, String fileName) {
        showProgress();
        pdfView.fileFromLocalStorage(this, this, this, fileUrl, System.currentTimeMillis() + ".pdf");   // 设置pdf文件地址
    }

    /**
     * 加载中
     */
    private void showProgress() {
        loadingDialog = CustomProgressDialog.createDialog(this);
        loadingDialog.show();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    /**
     * 加载完成回调
     *
     * @param nbPages 总共的页数
     */
    @Override
    public void loadComplete(int nbPages) {
        loadingDialog.dismiss();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        LogUtils.d("pdf--翻页回调page= " + page + ";pageCount= " + pageCount);
    }
}

package com.zyjr.emergencylending.ui.home.loan;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.ReceiveMoneyBean;
import com.zyjr.emergencylending.entity.RemindBean;
import com.zyjr.emergencylending.ui.home.View.ReceiveMoneyView;
import com.zyjr.emergencylending.ui.home.presenter.ReceiveMoneyPresenter;
import com.zyjr.emergencylending.utils.Arithmetic;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/14
 * 备注: 确认领取金额
 */
public class ReceiveMoneyActivity extends BaseActivity<ReceiveMoneyPresenter, ReceiveMoneyView> implements ReceiveMoneyView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_loan_money)
    TextView tvLoanMoney; // 借款金额
    @BindView(R.id.tv_loan_period)
    TextView tvLoanPeriod;  // 借款周期
    @BindView(R.id.tv_period_amount)
    TextView tvPeriodAmount; // 期还款额
    @BindView(R.id.tv_loan_agreement_1)
    TextView tvLoanAgreement1; // 借款协议
    @BindView(R.id.tv_loan_agreement_2)
    TextView tvLoanAgreement2; // 信用管理服务协议
    @BindView(R.id.tv_loan_agreement_3)
    TextView tvLoanAgreement3; // 账户管理与数据管理协议
    @BindView(R.id.tv_loan_agreement_4)
    TextView tvLoanAgreement4; // 还款明细说明
    @BindView(R.id.btn_receive_quickly)
    Button btnReceiveQuickly;
    @BindView(R.id.cb_check)
    CheckBox cbxAgree;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry; // 网络加载失败时重试
    @BindView(R.id.sv_main)
    ScrollView svMain;  // 主布局

    private ReceiveMoneyBean receiveMoneyBean = null;
    private ReceiveMoneyBean.Contract jiekuanContract = null;
    private ReceiveMoneyBean.Contract xinyongContract = null;
    private ReceiveMoneyBean.Contract zhanghuContract = null;
    private ReceiveMoneyBean.Contract huankuanContract = null;
    private String frontFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.RESOURCE + "money.ttf";
    private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.RESOURCE;
    private Typeface typeface;

    @Override
    protected ReceiveMoneyPresenter createPresenter() {
        return new ReceiveMoneyPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_money);
        ButterKnife.bind(this);

        init();
        loadingReceiveMoneyInfo();
    }

    @OnClick({R.id.tv_loan_agreement_1, R.id.tv_loan_agreement_2, R.id.tv_loan_agreement_3, R.id.tv_loan_agreement_4, R.id.btn_receive_quickly, R.id.cb_check, R.id.btn_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_loan_agreement_1: // 借款协议
                if (jiekuanContract == null) {
                    return;
                }
                PdfViewerActivity.jumpToPdfView(ReceiveMoneyActivity.this, jiekuanContract.getContract_name(), jiekuanContract.getContract_url());
                break;

            case R.id.tv_loan_agreement_2: // 信用管理服务协议
                if (xinyongContract == null) {
                    return;
                }
                PdfViewerActivity.jumpToPdfView(ReceiveMoneyActivity.this, xinyongContract.getContract_name(), xinyongContract.getContract_url());
                break;

            case R.id.tv_loan_agreement_3: // 账户管理与数据服务协议
                if (zhanghuContract == null) {
                    return;
                }
                PdfViewerActivity.jumpToPdfView(ReceiveMoneyActivity.this, zhanghuContract.getContract_name(), zhanghuContract.getContract_url());
                break;

            case R.id.tv_loan_agreement_4: // 还款明细说明
                if (huankuanContract == null) {
                    return;
                }
                PdfViewerActivity.jumpToPdfView(ReceiveMoneyActivity.this, huankuanContract.getContract_name(), huankuanContract.getContract_url());
                break;

            case R.id.cb_check:

                break;

            case R.id.btn_receive_quickly:
                validateData();

                break;

            case R.id.btn_retry:
                loadingReceiveMoneyInfo();
                break;
        }
    }

    private void validateData() {
        if (receiveMoneyBean == null) {
            ToastAlone.showLongToast(this, "获取信息失败,请重试");
            return;
        }
        if (!cbxAgree.isChecked()) {
            ToastAlone.showLongToast(this, "请阅读并同意签约上述合同");
            return;
        }
        confirmReceiveInfo();

    }


    private void init() {
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });

        // 下载字体
        typeface = openFront(this);
        try {
            tvLoanMoney.setTypeface(typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showReceiveMoneyInfo(ReceiveMoneyBean bean) {
        if (StringUtil.isNotEmpty(bean.getLoan_amount())) {
            // 借款金额
            tvLoanMoney.setText(Arithmetic.addComma(bean.getLoan_amount()));
        } else {
            tvLoanMoney.setText("---");
        }
        // 区分 天/周
        if (Constants.ONE.equals(bean.getLoan_unit())) {
            tvLoanPeriod.setText(bean.getLoan_period() + "天"); // 借款周期 (天)
            tvPeriodAmount.setText(bean.getPeriod_amount() + "元/期");  // 期还款额  元/期
        } else if (Constants.TWO.equals(bean.getLoan_unit())) {
            tvLoanPeriod.setText(bean.getLoan_period() + "周"); // 借款周期 (周)
            tvPeriodAmount.setText(bean.getPeriod_amount() + "元/周");  // 期还款额 元/周
        }
        List<ReceiveMoneyBean.Contract> contractList = bean.getLoan_contract();
        for (int i = 0; i < contractList.size(); i++) {
            ReceiveMoneyBean.Contract contract = contractList.get(i);
            switch (contract.getContract_no()) {
                case "loanAgreement": // 借款协议
                    jiekuanContract = contract;
                    tvLoanAgreement1.setText("《" + jiekuanContract.getContract_name() + "》");
                    break;

                case "creditManagement":  // 信用管理
                    xinyongContract = contract;
                    tvLoanAgreement2.setText("《" + xinyongContract.getContract_name() + "》");
                    break;

                case "accountManagement": // 账户管理与服务
                    zhanghuContract = contract;
                    tvLoanAgreement3.setText("《" + zhanghuContract.getContract_name() + "》");
                    break;

                case "reimbursementDetail": // 还款说明协议
                    huankuanContract = contract;
                    tvLoanAgreement4.setText("《" + huankuanContract.getContract_name() + "》");
                    break;
            }
        }
    }

    private void loadingReceiveMoneyInfo() {
        Map<String, String> map = new HashMap<>();
        mPresenter.getReceiveMoneyInfo(map);
    }

    private void confirmReceiveInfo() {
        Map<String, String> map = new HashMap<>();
        mPresenter.confirmReceiveInfo(map);
    }

    private void showError() {
        llRetry.setVisibility(View.VISIBLE);
        svMain.setVisibility(View.GONE);
    }

    private void showSuccess() {
        llRetry.setVisibility(View.GONE);
        svMain.setVisibility(View.VISIBLE);
    }


    @Override
    public void onSuccessGet(String apiCode, ReceiveMoneyBean bean) {
        showSuccess();
        receiveMoneyBean = bean;
        showReceiveMoneyInfo(receiveMoneyBean);
    }

    @Override
    public void onFail(String apiCode, String failMsg) {
        ToastAlone.showLongToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        showError();
    }

    @Override
    public void onSuccessConfirmReceive(String apiCode, String msg, RemindBean remindInfo) {
        Intent intent = new Intent(this, SendLoanProcessingActivity.class);
        if (null == remindInfo) {
            intent.putExtra("isShow", "0");
        } else {
            intent.putExtra("isShow", "1");
            intent.putExtra("remindInfo", remindInfo);
        }
        startActivity(intent);
        finish();
    }


    public Typeface openFront(Context context) {
        LogUtils.d("字体文件-filePath:" + frontFilePath);
        File jhPath = new File(frontFilePath);
        //查看数据库文件是否存在
        if (jhPath.exists()) {
            LogUtils.d("存在字体文件------打开");
            //存在则直接返回打开的数据库
            return Typeface.createFromAsset(getAssets(), "money.ttf");
        } else {
            //不存在先创建文件夹
            LogUtils.d("不存在字体文件------创建");
            File path = new File(rootPath);
            LogUtils.d("rootPath=" + path);
            if (path.mkdir()) {
                LogUtils.d("创建成功");
            } else {
                LogUtils.d("创建失败");
            }
            try {
                //得到资源
                AssetManager am = context.getAssets();
                //得到数据库的输入流
                InputStream is = am.open("money.ttf");
                LogUtils.d("输入流---->" + is + "");
                //用输出流写到SDcard上面
                FileOutputStream fos = new FileOutputStream(jhPath);
                LogUtils.d("输出流---->" + "fos=" + fos);
                LogUtils.d("dbPath---->" + jhPath);
                //创建byte数组  用于1KB写一次
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return Typeface.createFromAsset(getAssets(), "money.ttf");
        }
    }

}

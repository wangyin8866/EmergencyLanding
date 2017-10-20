package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.AppConfig;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.DialogCustom;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.UserInfoManager;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.widget.pop.AreaSelectPop;
import com.zyjr.emergencylending.widget.pop.SingleSelectPop;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 工作资料
 */
public class WorkInfoActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_industry)
    TextView tvIndustry; // 单位行业
    @BindView(R.id.et_unit_name)
    ClearEditText etUnitName; // 单位名称
    @BindView(R.id.et_unit_district_num)
    EditText etUnitDistrictNum; // 单位区号
    @BindView(R.id.et_unit_tel)
    EditText etUnitTel; // 电话号码
    @BindView(R.id.tv_job_address)
    TextView tvJobAddress; // 工作地址
    @BindView(R.id.et_detail_address)
    ClearEditText etDetailAddress; // 详细地址
    @BindView(R.id.et_department)
    ClearEditText etDepartment; // 部门
    @BindView(R.id.tv_job)
    TextView tvJob; // 职位
    @BindView(R.id.tv_income)
    TextView tvIncome; // 税后月收入

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_info);
        ButterKnife.bind(this);

        init();
    }

    @OnClick({R.id.ll_job_unit_industry, R.id.ll_job_address, R.id.ll_unit_job, R.id.ll_income, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_job_unit_industry:  // 单位行业
                SingleSelectPop popUnitSelect = new SingleSelectPop(this, AppConfig.getWorkInfo());
                popUnitSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        LogUtils.d("选择的单位行业信息:" + select.toString());
                        tvJob.setText(select.getName());
                    }
                });
                popUnitSelect.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_job_address:  // 工作地址
                AreaSelectPop popJobAddressSelect = new AreaSelectPop(this, CommonUtils.mProvinceDatas, CommonUtils.mCitisDatasMap, CommonUtils.mDistrictDatasMap);
                popJobAddressSelect.setOnCityPopupWindow(new AreaSelectPop.OnCityPopupWindow() {
                    @Override
                    public void onCityClick(String province, int privinceItem, String city, int cityItem, String district, int districtItem) {
                        LogUtils.d("选择的单位行业信息:" + province + "," + city + "," + district);
                        tvJobAddress.setText(province + "," + city + "," + district);
                    }
                });
                if (!TextUtils.isEmpty(UserInfoManager.getInstance().getLocation().getmCurrentCity())) {
                    popJobAddressSelect.setWheel();
                }
                popJobAddressSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_unit_job:  // 部门职位
                SingleSelectPop popJobSelect = new SingleSelectPop(this, AppConfig.marriageStatus());
                popJobSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        LogUtils.d("选择的部门职位:" + select.toString());
                        tvJob.setText(select.getName());
                    }
                });
                popJobSelect.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_income: // 月收入
                SingleSelectPop popIncomeSelect = new SingleSelectPop(this, AppConfig.monthSalary());
                popIncomeSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        LogUtils.d("选择的税后月收入:" + select.toString());
                        tvIncome.setText(select.getName());
                    }
                });
                popIncomeSelect.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_submit:
                // TODO 信息提交
                scanSuccessInfo("张三","123454648755","太远录32号");
                break;

        }
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
    }

    private void scanSuccessInfo(String name, String num, String addr) {
        final DialogCustom dialogCustom = new DialogCustom(this);
        dialogCustom.scanIdcardInfo(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_confirm:
                        ToastAlone.showLongToast(BaseApplication.getContext(), "确认");
                        break;

                    case R.id.tv_scan_again:
                        ToastAlone.showLongToast(BaseApplication.getContext(), "重新扫描");
                        break;
                }
            }
        }, name, num, addr).show();
    }

    private View getRootView() {
        return this.getWindow().getDecorView();
    }
}

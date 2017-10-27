package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.AppConfig;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.entity.UserInfoManager;
import com.zyjr.emergencylending.ui.home.View.WorkInfoView;
import com.zyjr.emergencylending.ui.home.presenter.WorkInfoPresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.WYUtils;
import com.zyjr.emergencylending.widget.pop.AreaSelectPop;
import com.zyjr.emergencylending.widget.pop.SingleSelectPop;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 工作资料
 */
public class WorkInfoActivity extends BaseActivity<WorkInfoPresenter,WorkInfoView> implements WorkInfoView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_unit_industry)
    TextView tvUintIndustry; // 单位行业
    @BindView(R.id.et_unit_name)
    ClearEditText etUnitName; // 单位名称
    @BindView(R.id.et_unit_district_num)
    EditText etUnitDistrictNum; // 单位区号
    @BindView(R.id.et_unit_tel)
    EditText etUnitTel; // 电话号码
    @BindView(R.id.tv_work_address)
    TextView tvWorkAddress; // 工作地址
    @BindView(R.id.et_work_detail_address)
    ClearEditText etWorkDetailAddress; // 工作详细地址
    @BindView(R.id.et_work_department)
    ClearEditText etWorkDepartment; // 工作部门
    @BindView(R.id.tv_work_position)
    TextView tvWorkPosition; // 职位
    @BindView(R.id.tv_income)
    TextView tvIncome; // 税后月收入
    @BindView(R.id.root)
    ScrollView root;
    @BindView(R.id.ll_cover)
    LinearLayout llCover;
    private boolean isJobEdit;

    @Override
    protected WorkInfoPresenter createPresenter() {
        return new WorkInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_info);
        ButterKnife.bind(this);
        init();
    }

    @OnClick({R.id.ll_unit_industry, R.id.ll_work_address, R.id.ll_work_position, R.id.ll_income, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_industry:  // 单位行业
                SingleSelectPop popUnitSelect = new SingleSelectPop(this, AppConfig.getWorkInfo());
                popUnitSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        LogUtils.d("选择的单位行业信息:" + select.toString());
                        tvUintIndustry.setText(select.getName());
                    }
                });
                popUnitSelect.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_work_address:  // 工作地址
                AreaSelectPop popJobAddressSelect = new AreaSelectPop(this, CommonUtils.mProvinceDatas, CommonUtils.mCitisDatasMap, CommonUtils.mDistrictDatasMap);
                popJobAddressSelect.setOnCityPopupWindow(new AreaSelectPop.OnCityPopupWindow() {
                    @Override
                    public void onCityClick(String province, int privinceItem, String city, int cityItem, String district, int districtItem) {
                        LogUtils.d("选择的单位行业信息:" + province + "," + city + "," + district);
                        tvWorkAddress.setText(province + "," + city + "," + district);
                    }
                });
                if (!TextUtils.isEmpty(UserInfoManager.getInstance().getLocation().getmCurrentCity())) {
                    popJobAddressSelect.setWheel();
                }
                popJobAddressSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_work_position:  // 工作职位
                SingleSelectPop popJobSelect = new SingleSelectPop(this, AppConfig.marriageStatus());
                popJobSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        LogUtils.d("选择的工作职位:" + select.toString());
                        tvWorkPosition.setText(select.getName());
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
                break;

        }
    }

    private void validateData() {
        String unitIndustry = tvUintIndustry.getText().toString().trim(); // 单位行业
        String unitName = etUnitName.getText().toString().trim(); // 单位名称
        String districtNum = etUnitDistrictNum.getText().toString().trim();// 区号
        String unitTel = etUnitTel.getText().toString().trim(); // 单位电话
        String workAddress = tvWorkAddress.getText().toString().trim(); // 工作地址
        String workDetailAddress = etWorkDetailAddress.getText().toString().trim(); // 工作详细地址
        String workDepartment = etWorkDepartment.getText().toString().trim(); // 工作部门
        String workPosition = tvWorkPosition.getText().toString().trim(); // 工作职位
        String income = tvIncome.getText().toString().trim(); // 税后月收入


    }


    private void init() {
        //是否可编辑
        isJobEdit = getIntent().getBooleanExtra("isJobEdit", true);
        WYUtils.coverPage(isJobEdit,llCover);


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

    private View getRootView() {
        return this.getWindow().getDecorView();
    }

    @Override
    public void onSuccessGet(String returnCode, IDCardFrontBean model) {

    }

    @Override
    public void onSuccessAdd(String returnCode, IDCardFrontBean model) {

    }

    @Override
    public void onSuccessEdit(String returnCode, IDCardFrontBean model) {

    }

    @Override
    public void onFail(String errorMessage) {

    }
}

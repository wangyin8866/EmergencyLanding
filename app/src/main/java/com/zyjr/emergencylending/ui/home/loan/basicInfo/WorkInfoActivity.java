package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.config.AppConfig;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.CityModel;
import com.zyjr.emergencylending.entity.CodeBean;
import com.zyjr.emergencylending.entity.DistrictModel;
import com.zyjr.emergencylending.entity.ProvinceModel;
import com.zyjr.emergencylending.entity.WorkAddressBean;
import com.zyjr.emergencylending.entity.WorkInfoBean;
import com.zyjr.emergencylending.ui.home.View.WorkInfoView;
import com.zyjr.emergencylending.ui.home.presenter.WorkInfoPresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ReflectionUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;
import com.zyjr.emergencylending.widget.pop.AreaSelectPop;
import com.zyjr.emergencylending.widget.pop.SingleSelectPop;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zyjr.emergencylending.utils.CommonUtils.provinceList;

/**
 * Created by neil on 2017/10/12
 * 备注: 工作资料
 */
public class WorkInfoActivity extends BaseActivity<WorkInfoPresenter, WorkInfoView> implements WorkInfoView {
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
    private CodeBean unintIndustryCodebean = null; // 单位行业codeBean
    private WorkAddressBean workAddressBean = null; // 工作地址
    private CodeBean workPositionCodebean = null; // 工作职位
    private CodeBean inComeCodebean = null; // 收入
    private WorkInfoBean workInfoBean = null; // 表单bean
    @BindView(R.id.root)
    ScrollView root;
    @BindView(R.id.ll_cover)
    LinearLayout llCover;
    private String isEdit = "1"; // 1,可编辑;0,不可编辑。默认可编辑

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
        initGetData();
    }

    @OnClick({R.id.ll_unit_industry, R.id.ll_work_address, R.id.ll_work_position, R.id.ll_income, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_industry:  // 单位行业
                SingleSelectPop popUnitSelect = new SingleSelectPop(this, AppConfig.getWorkInfo());
                popUnitSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        unintIndustryCodebean = select;
                        LogUtils.d("选择的单位行业信息:" + unintIndustryCodebean.toString());
                        tvUintIndustry.setText(unintIndustryCodebean.getName());
                    }
                });
                popUnitSelect.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_work_address:  // 工作地址
                AreaSelectPop popJobAddressSelect = new AreaSelectPop(this, CommonUtils.mProvinceDatas, CommonUtils.mCitisDatasMap, CommonUtils.mDistrictDatasMap);
                popJobAddressSelect.setOnCityPopupWindow(new AreaSelectPop.OnCityPopupWindow() {
                    @Override
                    public void onCityClick(String province, int privinceItem, String city, int cityItem, String district, int districtItem) {
                        tvWorkAddress.setText(province + "," + city + "," + district);
                        workAddressBean = new WorkAddressBean(); //  实例化bean
                        ProvinceModel pCode = null;
                        CityModel cityode = null;
                        DistrictModel dCode = null;
                        pCode = provinceList.get(privinceItem);
                        cityode = pCode.getCityList().get(cityItem);
                        dCode = cityode.getDistrictList().get(districtItem);
                        workAddressBean.setUnit_province(pCode.getProvinceCode());
                        workAddressBean.setUnit_province_name(pCode.getName());
                        workAddressBean.setUnit_city(cityode.getCityCode());
                        workAddressBean.setUnit_city_name(cityode.getName());
                        workAddressBean.setUnit_county(dCode.getZipcode());
                        workAddressBean.setUnit_county_name(dCode.getName());
                        workAddressBean.setUnit_adr(pCode.getProvinceCode() + "," + cityode.getCityCode() + "," + dCode.getZipcode());
                        LogUtils.d("工作地址选择:" + workAddressBean.toString());
                    }
                });
                popJobAddressSelect.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_work_position:  // 工作职位
                SingleSelectPop popJobSelect = new SingleSelectPop(this, AppConfig.job());
                popJobSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        workPositionCodebean = select;
                        LogUtils.d("选择的工作职位:" + workPositionCodebean.toString());
                        tvWorkPosition.setText(workPositionCodebean.getName());
                    }
                });
                popJobSelect.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.ll_income: // 月收入
                SingleSelectPop popIncomeSelect = new SingleSelectPop(this, AppConfig.monthSalary());
                popIncomeSelect.setOnSelectPopupWindow(new SingleSelectPop.onSelectPopupWindow() {
                    @Override
                    public void onSelectClick(int index, CodeBean select) {
                        inComeCodebean = select;
                        LogUtils.d("选择的税后月收入:" + inComeCodebean.toString());
                        tvIncome.setText(inComeCodebean.getName());
                    }
                });
                popIncomeSelect.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_submit:
                // TODO 信息提交
                validateData();
                break;

        }
    }

    private void validateData() {
        String unitIndustry = tvUintIndustry.getText().toString().trim(); // 单位行业
        String unitName = etUnitName.getText().toString().trim(); // 单位名称
        String districtNum = etUnitDistrictNum.getText().toString().trim();// 区号
        String unitTel = etUnitTel.getText().toString().trim(); // 单位固话
        String workAddress = tvWorkAddress.getText().toString().trim(); // 工作地址
        String workDetailAddress = etWorkDetailAddress.getText().toString().trim(); // 工作详细地址
        String workDepartment = etWorkDepartment.getText().toString().trim(); // 工作部门
        String workPosition = tvWorkPosition.getText().toString().trim(); // 工作职位
        String income = tvIncome.getText().toString().trim(); // 税后月收入
        if (StringUtil.isEmpty(unitIndustry) && unintIndustryCodebean == null) {
            ToastAlone.showLongToast(this, "请选择单位行业!");
            return;
        }
        if (StringUtil.isEmpty(unitName)) {
            ToastAlone.showLongToast(this, "请填写单位名称!");
            return;
        }
        if (StringUtil.isEmpty(districtNum)) {
            ToastAlone.showLongToast(this, "请填写区号!");
            return;
        }
        if (StringUtil.isEmpty(unitTel)) {
            ToastAlone.showLongToast(this, "请填写单位固话!");
            return;
        }
        if (StringUtil.isEmpty(workAddress) && workAddressBean == null) {
            ToastAlone.showLongToast(this, "请选择工作地址!");
            return;
        } else {
            if (workAddressBean != null && (StringUtil.containChinese(workAddressBean.getUnit_province())
                    || StringUtil.containChinese(workAddressBean.getUnit_city()))
                    || StringUtil.containChinese(workAddressBean.getUnit_county())) {
                ToastAlone.showLongToast(this, "请重新选择工作地址!");
                return;
            }
        }
        if (StringUtil.isEmpty(workDetailAddress)) {
            ToastAlone.showLongToast(this, "请填写工作详细地址!");
            return;
        }
        if (StringUtil.isEmpty(workDepartment)) {
            ToastAlone.showLongToast(this, "请填写单位部门!");
            return;
        }
        if (StringUtil.isEmpty(workPosition) && workPositionCodebean == null) {
            ToastAlone.showLongToast(this, "请选择职位!");
            return;
        }
        if (StringUtil.isEmpty(income) && inComeCodebean == null) {
            ToastAlone.showLongToast(this, "请选择税后月收入!");
            return;
        }
        workInfoBean = new WorkInfoBean();
        workInfoBean.setUnit_industry(unintIndustryCodebean.getCode()); // 单位行业
        workInfoBean.setUnit_name(unitName); // 单位名称
        workInfoBean.setUnit_phone(districtNum + "-" + unitTel); // 单位区号 格式：区号-电话号
        workInfoBean.setUnit_adr(workAddressBean.getUnit_adr()); // 工作地址省市区
        workInfoBean.setUnit_province(workAddressBean.getUnit_province());  // 单位所在省
        workInfoBean.setUnit_province_name(workAddressBean.getUnit_province_name());
        workInfoBean.setUnit_city(workAddressBean.getUnit_city()); // 单位所在市
        workInfoBean.setUnit_city_name(workAddressBean.getUnit_city_name());
        workInfoBean.setUnit_county(workAddressBean.getUnit_county()); // 单位所在区
        workInfoBean.setUnit_county_name(workAddressBean.getUnit_county_name());
        workInfoBean.setUnit_adr_detail(workDetailAddress); // 工作详细地址
        workInfoBean.setUnit_department(workDepartment); // 单位部门
        workInfoBean.setTitle(workPositionCodebean.getCode()); // 工作职位
        workInfoBean.setMonth_pay(inComeCodebean.getCode()); // 每月收入
        Map<String, String> paramsMap = ReflectionUtils.beanToMap(workInfoBean);
        LogUtils.d("参数明细:" + new Gson().toJson(paramsMap));
        LogUtils.d("参数数量:" + paramsMap.size());
        mPresenter.addWorkInfo(paramsMap);
    }


    /**
     * 用户下次进入时数据信息
     */
    private void showWorkInfo(WorkInfoBean workInfoBean) {
        if (StringUtil.isNotEmpty(workInfoBean.getUnit_industry())) {
            // 单位行业
            int index = AppConfig.getWorkInfo().indexOf(new CodeBean(0, workInfoBean.getUnit_industry(), ""));
            if (index != -1) {
                unintIndustryCodebean = AppConfig.getWorkInfo().get(index);
                tvUintIndustry.setText(unintIndustryCodebean.getName());
                LogUtils.d("获取填写的单位行业:" + unintIndustryCodebean.toString());
            } else {
                tvUintIndustry.setText("");
            }
        }
        if (StringUtil.isNotEmpty(workInfoBean.getUnit_name())) {
            // 单位名称
            etUnitName.setText(workInfoBean.getUnit_name());
        }
        if (StringUtil.isNotEmpty(workInfoBean.getUnit_phone())) {
            // 单位电话
            String phone = workInfoBean.getUnit_phone();
            String[] split = phone.split("-");
            int len = split.length;
            if (len > 0) {
                etUnitDistrictNum.setText(split[0] == null ? "" : split[0]);
                etUnitTel.setText(split[1] == null ? "" : split[1]);
            } else {
                etUnitDistrictNum.setText("");
                etUnitTel.setText("");
            }
        }
        if (StringUtil.isNotEmpty(workInfoBean.getUnit_adr())) {
            // 工作地址
            try {
                String[] code = workInfoBean.getUnit_adr().split(",");
                if (code != null && code.length == 3) {
                    ProvinceModel p = null;
                    CityModel c = null;
                    DistrictModel d = null;
                    if (provinceList.indexOf(new ProvinceModel(code[0])) != -1) {
                        p = provinceList.get(provinceList.indexOf(new ProvinceModel(code[0])));
                    }
                    if (p != null && p.getCityList().indexOf(new CityModel(code[1])) != -1) {
                        c = p.getCityList().get(p.getCityList().indexOf(new CityModel(code[1])));
                    }
                    if (c != null && c.getDistrictList().indexOf(new DistrictModel(code[2])) != -1) {
                        d = c.getDistrictList().get(c.getDistrictList().indexOf(new DistrictModel(code[2])));
                    }
                    if (p != null && c != null && d != null) {
                        if (StringUtil.isEmpty(workInfoBean.getUnit_province())
                                || StringUtil.isEmpty(workInfoBean.getUnit_city())
                                || StringUtil.isEmpty(workInfoBean.getUnit_county())) {
                            tvWorkAddress.setText("");
                        } else {
                            tvWorkAddress.setText(p.getName() + "," + c.getName() + "," + d.getName());
                            workAddressBean = new WorkAddressBean();
                            workAddressBean.setUnit_province(p.getProvinceCode());
                            workAddressBean.setUnit_province_name(p.getName());
                            workAddressBean.setUnit_city(c.getCityCode());
                            workAddressBean.setUnit_city_name(c.getName());
                            workAddressBean.setUnit_county(d.getZipcode());
                            workAddressBean.setUnit_county_name(d.getName());
                            workAddressBean.setUnit_adr(p.getProvinceCode() + "," + c.getCityCode() + "," + d.getZipcode());
                            LogUtils.d("获取填写的工作地址:" + workAddressBean.toString());
                        }
                    } else {
                        tvWorkAddress.setText("");
                    }
                } else {
                    tvWorkAddress.setText("");
                }
            } catch (Exception e) {
                tvWorkAddress.setText("");
                e.printStackTrace();
            }
        }
        if (StringUtil.isNotEmpty(workInfoBean.getUnit_adr_detail())) {
            // 工作详细地址
            etWorkDetailAddress.setText(workInfoBean.getUnit_adr_detail());
        }
        if (StringUtil.isNotEmpty(workInfoBean.getUnit_department())) {
            // 部门
            etWorkDepartment.setText(workInfoBean.getUnit_department());
        }
        if (StringUtil.isNotEmpty(workInfoBean.getTitle())) {
            // 职位
            int index = AppConfig.job().indexOf(new CodeBean(0, workInfoBean.getTitle(), ""));
            if (index != -1) {
                workPositionCodebean = AppConfig.job().get(index);
                tvWorkPosition.setText(workPositionCodebean.getName());
                LogUtils.d("获取填写的职位信息:" + workPositionCodebean.toString());
            } else {
                tvWorkPosition.setText("");
            }
        }
        if (StringUtil.isNotEmpty(workInfoBean.getMonth_pay())) {
            // 税后月收入
            int index = AppConfig.monthSalary().indexOf(new CodeBean(0, workInfoBean.getMonth_pay(), ""));
            if (index != -1) {
                inComeCodebean = AppConfig.monthSalary().get(index);
                tvIncome.setText(inComeCodebean.getName());
                LogUtils.d("获取填写的税后月收入:" + inComeCodebean.toString());
            } else {
                tvIncome.setText("");
            }
        }

    }


    private void init() {
        CommonUtils.addressDatas(this);
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
        Intent intent = getIntent();
        isEdit = intent.getStringExtra("isEdit");
        String status = intent.getStringExtra("status");
        // 已完成状态获取资料信息
        if (StringUtil.isNotEmpty(status) && status.equals("1")) {
            loadingWorkInfo();
        }
        if (isEdit.equals("0")) { // 不可编辑
            WYUtils.coverPage(false, llCover);
        }
    }

    private void loadingWorkInfo() {
        Map<String, String> paramMaps = new HashMap<>();
        mPresenter.getWorkInfo(paramMaps);
    }

    private View getRootView() {
        return this.getWindow().getDecorView();
    }

    @Override
    public void onSuccessGet(String returnCode, WorkInfoBean bean) {
        showWorkInfo(bean);
    }

    @Override
    public void onSuccessAdd(String returnCode, String msg) {
        ToastAlone.showLongToast(this, msg);
        finish();
    }

    @Override
    public void onSuccessEdit(String returnCode, String msg) {
        ToastAlone.showLongToast(this, msg);
        finish();
    }

    @Override
    public void onFail(String returnCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }
}

package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.SortAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.SupportCityBean;
import com.zyjr.emergencylending.ui.home.View.ProductInfoView;
import com.zyjr.emergencylending.ui.home.presenter.ProductInfoPresenter;
import com.zyjr.emergencylending.utils.PinyinComparator;
import com.zyjr.emergencylending.utils.SoftHideKeyBoardUtil;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.widget.CharacterParser;
import com.zyjr.emergencylending.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/21
 * 备注: 支持城市列表
 */
public class SupportCitiesActivity extends BaseActivity<ProductInfoPresenter, ProductInfoView> implements ProductInfoView {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.sidebar)
    SideBar sidebar;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.lv_support_cities)
    ListView sortListView;

    //    private List<CityBean> cityList;
    private SortAdapter adapter;


    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SupportCityBean> sourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected ProductInfoPresenter createPresenter() {
        return new ProductInfoPresenter(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_cities);
        ButterKnife.bind(this);

        getSupportCities();
        init();
    }

    private void init() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
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

    // TODO 获取支持城市
    private void getSupportCities() {
        Map<String, String> map = new HashMap<>();
        mPresenter.getSupportCities(map);
    }

    @Override
    public void onSuccessGetSupportCity(String returnCode, List<SupportCityBean> cityList) {
        sourceDateList = cityList;
        // 根据a-z进行排序源数据
        filledData(sourceDateList);
        Collections.sort(sourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, sourceDateList);
        sortListView.setAdapter(adapter);
        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                SoftHideKeyBoardUtil.closeSoftKeyboard(SupportCitiesActivity.this);
                int position = adapter.getPositionForSection(s.charAt(0)); //该字母首次出现的位置
                if (position != -1) {
                    sortListView.setSelection(position);
                }
                sidebar.setTextView(tvShow);
            }
        });
    }

    @Override
    public void onSuccessGetIntro(String returnCode, List<String> introList) {

    }

    @Override
    public void onFail(String returnCode, String errorMessage) {

    }

    @Override
    public void onError(String returnCode, String errorMsg) {

    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SupportCityBean> filledData(List<SupportCityBean> date) {
        List<SupportCityBean> mSortList = new ArrayList<SupportCityBean>();
        for (int i = 0; i < date.size(); i++) {
            SupportCityBean sortModel = date.get(i);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(sortModel.getCity_name());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setPinyin(sortString.toUpperCase());
            } else {
                sortModel.setPinyin("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SupportCityBean> filterDateList = new ArrayList<SupportCityBean>();
        if (StringUtil.isEmpty(filterStr)) {
            filterDateList = sourceDateList;
        } else {
            filterDateList.clear();
            for (SupportCityBean supportCityBean : sourceDateList) {
                String cityName = supportCityBean.getCity_name();
                if (cityName.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(cityName).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(supportCityBean);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
}

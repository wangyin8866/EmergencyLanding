package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.SortAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.SupportCityConfig;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.CityBean;
import com.zyjr.emergencylending.entity.CityModel;
import com.zyjr.emergencylending.utils.PinyinComparator;
import com.zyjr.emergencylending.utils.SoftHideKeyBoardUtil;
import com.zyjr.emergencylending.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/21
 * 备注: 支持城市列表
 */
public class SupportCitiesActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.sidebar)
    SideBar sidebar;
    @BindView(R.id.tv_show)
    TextView tvShow;

    private List<CityBean> cityList;
    private ListView sortListView;
    private SortAdapter adapter;
    private List<CityModel> sourceDateList; //用于存放排序后的二级城市，最主要的功能
    private PinyinComparator pinyinComparator;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_cities);
        ButterKnife.bind(this);

        init();
        initViews();
    }

    private void init() {
        cityList = SupportCityConfig.getInstance().getCitys();
        pinyinComparator = new PinyinComparator();
        // 根据a-z进行排序源数据
        sourceDateList = filledData(cityList);
        Collections.sort(sourceDateList, pinyinComparator);
    }

    private List<CityModel> filledData(List<CityBean> date) {
        List<CityModel> mSortList = new ArrayList<>();
        for (int i = 0; i < date.size(); i++) {
            CityModel cityModel = new CityModel();
            String name = date.get(i).getName();
            String firstName = date.get(i).getFirstName();
            String pinyin = date.get(i).getPinyin();
            cityModel.setName(name);
            cityModel.setFirstName(firstName);
            cityModel.setPingyin(pinyin);
            mSortList.add(cityModel);
        }
        return mSortList;
    }

    private void initViews() {
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
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

}

package com.zyjr.emergencylending;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bqs.risk.df.android.BqsDF;
import com.bqs.risk.df.android.BqsParams;
import com.gyf.barlibrary.ImmersionBar;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.NoScrollViewPager;
import com.zyjr.emergencylending.ui.account.LoginActivity;
import com.zyjr.emergencylending.ui.home.HomeFragment;
import com.zyjr.emergencylending.ui.my.MyFragment;
import com.zyjr.emergencylending.ui.repayment.RepaymentFragment;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.WYUtils;
import com.zyjr.emergencylending.utils.permission.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tongdun.android.shell.FMAgent;
import cn.tongdun.android.shell.exception.FMException;


/**
 * @author wangyin
 * @date 2017/5/16
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.id_tab_ll_01)
    LinearLayout idTabLl01;
    @BindView(R.id.id_tab_ll_02)
    LinearLayout idTabLl02;
    @BindView(R.id.id_tab_ll_03)
    LinearLayout idTabLl03;
    @BindView(R.id.id_main_viewPager)
    NoScrollViewPager idMainViewPager;
    @BindView(R.id.id_tab_iv_01)
    ImageView idTabIv01;
    @BindView(R.id.id_tab_tv_01)
    TextView idTabTv01;
    @BindView(R.id.id_tab_iv_02)
    ImageView idTabIv02;
    @BindView(R.id.id_tab_tv_02)
    TextView idTabTv02;
    @BindView(R.id.id_tab_iv_03)
    ImageView idTabIv03;
    @BindView(R.id.id_tab_tv_03)
    TextView idTabTv03;
    private List<Fragment> fragments;
    public static int currentPage = 0;
    private static final String TAG = "MainActivity";
    private ImmersionBar mImmersionBar;
    //获取6.0运行时权限列表，第一个参数：是否授权gps，第二个参数：是否授权通讯录，第三个参数：是否授权通话记录
    String[] requestPermissions = BqsDF.getRuntimePermissions(true, true, true);

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initPermission();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true).init();
        ActivityCollector.addActivity(this);
        fragments = new ArrayList<>();

        HomeFragment homeFragment = new HomeFragment();
        RepaymentFragment repaymentFragment = new RepaymentFragment();
        MyFragment myFragment = new MyFragment();
        fragments.add(homeFragment);
        fragments.add(repaymentFragment);
        fragments.add(myFragment);

        idMainViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        idTabLl01.setOnClickListener(this);
        idTabLl02.setOnClickListener(this);
        idTabLl03.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setTabSelection(currentPage);
    }

    private void initPermission() {
        /**
         * 同盾
         */
        //权限申请
        if (Build.VERSION.SDK_INT > 23) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,  //必选
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,  //必选
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_CONTACTS  //通讯录

            }, 100);
        }
        /**
         * 白骑士
         */
        //FMAgent.ENV_SANDBOX表示测试环境，FMAgent.ENV_PRODUCTION表示生产环境
        try {
            FMAgent.init(MainActivity.this, FMAgent.ENV_SANDBOX);
            String blackBox = FMAgent.onEvent(MainActivity.this);
            LogUtils.e("tokenkey", blackBox);
            SPUtils.saveWyString(MainActivity.this, Config.KEY_TONG_DUN, blackBox);
        } catch (FMException e) {
            e.printStackTrace();
        }

        PermissionUtils.requestMultiPermissions(this, requestPermissions, mPermissionGrant);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tab_ll_01:
                currentPage = 0;
                setTabSelection(currentPage);
                break;
            case R.id.id_tab_ll_02:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN)) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {

                    currentPage = 1;
                    setTabSelection(currentPage);
                }

                break;
            case R.id.id_tab_ll_03:
                if (!SPUtils.getBoolean(mContext, Config.KEY_LOGIN)) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    currentPage = 2;
                    setTabSelection(currentPage);
                }
                break;
            default:
        }
    }


    public void setTabSelection(int currentPage) {

        //选中前清除状态
        restView();
        switch (currentPage) {
            case 0://未登录
                idTabIv01.setImageResource(R.mipmap.bottom_loan_on);
                idTabTv01.setTextColor(ContextCompat.getColor(this, R.color.tv_navigate_checked));
                switchPager(currentPage);
                break;
            case 1:
                idTabIv02.setImageResource(R.mipmap.bottom_repayment_on);
                idTabTv02.setTextColor(ContextCompat.getColor(this, R.color.tv_navigate_checked));
                switchPager(currentPage);
                break;
            case 2:
                idTabIv03.setImageResource(R.mipmap.bottom_my_on);
                idTabTv03.setTextColor(ContextCompat.getColor(this, R.color.tv_navigate_checked));
                switchPager(currentPage);
                break;
            default:
        }
    }

    private void switchPager(int currentPage) {
        idMainViewPager.setCurrentItem(currentPage, false);
    }

    /**
     * 重置所有状态
     */
    private void restView() {
        idTabIv01.setImageResource(R.mipmap.bottom_loan_off);
        idTabIv02.setImageResource(R.mipmap.bottom_repayment_off);
        idTabIv03.setImageResource(R.mipmap.bottom_my_off);

        idTabTv01.setTextColor(ContextCompat.getColor(this, R.color.tv_navigate));
        idTabTv02.setTextColor(ContextCompat.getColor(this, R.color.tv_navigate));
        idTabTv03.setTextColor(ContextCompat.getColor(this, R.color.tv_navigate));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG, currentPage);
        LogUtils.e("onSaveInstanceState", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPage = savedInstanceState.getInt(TAG);
        LogUtils.e("onRestoreInstanceState", "onRestoreInstanceState");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return WYUtils.clickBack(keyCode, event, MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.e("onNewIntent", "onNewIntent");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            currentPage = bundle.getInt("index");
        }
    }

    /**
     * 授权结果，该回调不管权限是拒绝还是同意都会进入该回调方法
     */
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, String[] requestPermissions) {
            Constants.authRuntimePermissions = true;

            /**
             * 因为在启动页进行运行时权限授权，所以要在授权结果回调中出发一次初始化
             */
            //2、配置初始化参数
            BqsParams params = new BqsParams();
            params.setPartnerId("haoliwang");//商户编号
            params.setTestingEnv(true);//false是生产环境 true是测试环境
            params.setGatherGps(true);
            params.setGatherContact(true);
            params.setGatherCallRecord(true);
            //3、执行初始化
            BqsDF.initialize(mContext, params);
            //采集通讯录,第一个参数：是否采集通讯录，第二个参数：是否采集通话记录
            BqsDF.commitContactsAndCallRecords(false, false);
            BqsDF.commitLocation();
            //BqsDF.commitLocation(longitude, latitude);

            //4、提交tokenkey到贵公司服务器
            String tokenkey = BqsDF.getTokenKey();
            LogUtils.e("tokenkey", tokenkey);
            SPUtils.saveWyString(mContext, Config.KEY_BAI_QI_SHI, tokenkey);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(requestCode, requestPermissions, grantResults, requestPermissions, mPermissionGrant);
    }
}

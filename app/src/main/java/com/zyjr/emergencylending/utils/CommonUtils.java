package com.zyjr.emergencylending.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;

import com.zyjr.emergencylending.entity.CityModel;
import com.zyjr.emergencylending.entity.DistrictModel;
import com.zyjr.emergencylending.entity.MobileContactBean;
import com.zyjr.emergencylending.entity.ProvinceModel;
import com.zyjr.emergencylending.service.XmlParserHandler;
import com.zyjr.emergencylending.widget.step.StepBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by User on 2017/7/12.
 */

public class CommonUtils {

    /**
     * 格式化电话号码（显示前3后4中间用*代替）
     *
     * @param tel
     * @return
     */
    public static String FormatTel(String tel) {
        if (tel != null && tel.length() == 11) {
            return tel.substring(0, 3) + "****" + tel.substring(7, 11);
        } else {
            return "格式错误";
        }
    }

    /**
     * 地址省-市-区数据的处理
     *
     * @param context
     * @param mProvinceDatas
     * @param mCitisDatasMap
     * @param mDistrictDatasMap
     */
    public static List<ProvinceModel> provinceList = null;
    /**
     * 所有省
     */
    public static String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    public static Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    public static Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    public static void addressDatas(Context context) {
        InputStream input = null;
        try {
            AssetManager asset = context.getAssets();
            input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            // 初始化默认选中的省、市、区
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
//                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 设置订单状态
     */
    public static ArrayList<StepBean> setOrderStatus(int orderIndex) {
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = null;
        StepBean stepBean1 = null;
        StepBean stepBean2 = null;
        StepBean stepBean3 = null;
        StepBean stepBean4 = null;
        StepBean stepBean5 = null;
        if (orderIndex == 0) { // 填写资料
            stepBean0 = new StepBean("填写资料", 0);
            stepBean1 = new StepBean("认证中", -1);
            stepBean2 = new StepBean("审核中", -1);
            stepBean3 = new StepBean("领取金额", -1);
            stepBean4 = new StepBean("放款中", -1);
            stepBean5 = new StepBean("还款中", -1);
        } else if (orderIndex == 1) { // 认证中
            stepBean0 = new StepBean("填写资料", 1);
            stepBean1 = new StepBean("认证中", 0);
            stepBean2 = new StepBean("审核中", -1);
            stepBean3 = new StepBean("领取金额", -1);
            stepBean4 = new StepBean("放款中", -1);
            stepBean5 = new StepBean("还款中", -1);
        } else if (orderIndex == 2) {  // 审核中
            stepBean0 = new StepBean("填写资料", 1);
            stepBean1 = new StepBean("认证中", 1);
            stepBean2 = new StepBean("审核中", 0);
            stepBean3 = new StepBean("领取金额", -1);
            stepBean4 = new StepBean("放款中", -1);
            stepBean5 = new StepBean("还款中", -1);
        } else if (orderIndex == 3) {  // 领取金额
            stepBean0 = new StepBean("填写资料", 1);
            stepBean1 = new StepBean("认证中", 1);
            stepBean2 = new StepBean("审核中", 1);
            stepBean3 = new StepBean("领取金额", 0);
            stepBean4 = new StepBean("放款中", -1);
            stepBean5 = new StepBean("还款中", -1);
        } else if (orderIndex == 4) {  // 放款中
            stepBean0 = new StepBean("填写资料", 1);
            stepBean1 = new StepBean("认证中", 1);
            stepBean2 = new StepBean("审核中", 1);
            stepBean3 = new StepBean("领取金额", 1);
            stepBean4 = new StepBean("放款中", 0);
            stepBean5 = new StepBean("还款中", -1);
        } else if (orderIndex == 5) {  // 还款中
            stepBean0 = new StepBean("填写资料", 1);
            stepBean1 = new StepBean("认证中", 1);
            stepBean2 = new StepBean("审核中", 1);
            stepBean3 = new StepBean("领取金额", 1);
            stepBean4 = new StepBean("放款中", 1);
            stepBean5 = new StepBean("还款中", 1);
        }
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        stepsBeanList.add(stepBean4);
        stepsBeanList.add(stepBean5);
        return (ArrayList<StepBean>) stepsBeanList;
    }


    /**
     * 查询所有联系人
     *
     * @param mContext
     * @return
     */
    public static List<MobileContactBean> queryContactPhoneNumber(Context mContext) {
        List<MobileContactBean> mConnectInfos = new ArrayList<>();
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, null);
        int count = 0;
        try {
//            count = cursor.getCount() < 100 ? cursor.getCount() : 100;
            count = cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            MobileContactBean inof = new MobileContactBean();
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME); // 取得联系人名字
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER); // 取得联系人号码
            String name = cursor.getString(nameFieldColumnIndex);
            String number = StringUtil.removeSpace(cursor.getString(numberFieldColumnIndex));
            inof.setContact_name(name);
            inof.setContact_phone(number);
            mConnectInfos.add(inof);
        }
        if (cursor != null) {
            cursor.close();
        }
        return mConnectInfos;
    }


    /**
     * 弹框,跳转到应用设置详情页面
     */
    public static void jumpAppInfoSetting(final Activity activity, String msg) {
        new AlertDialog.Builder(activity).setMessage(msg)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
                        }
                        activity.startActivity(localIntent);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


}

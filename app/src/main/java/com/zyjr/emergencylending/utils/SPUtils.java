package com.zyjr.emergencylending.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * //实现标记的写入与读取
 *
 * @author wangyin
 */
public class SPUtils {
    /**
     * 安全退出时清除
     */
    private static final String SP_NAME = "wyman";
    /**
     * 卸载应用时清除
     */
    private static final String SP_NAME_WY = "wangyin";
    /**
     * //保存是否登录,用户基本信息
     */
    private static SharedPreferences sp;
    /**
     * //保存是否第一次进入APP
     */
    private static SharedPreferences sp2;

    //保存布尔值
    public static void saveBoolean(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, true).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, false);
    }

    //保存字符串
    public static void saveString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    //保存Long---token
    public static void saveLong(Context context, String key, long value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(Context context, String key, long defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }

    //保存int
    public static void saveInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    //清楚数据
    public static void clear(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().clear().apply();

    }

    //保存导航页
    public static void saveWyBoolean(Context context, String key, boolean value) {
        if (sp2 == null) {
            sp2 = context.getSharedPreferences(SP_NAME_WY, Context.MODE_PRIVATE);
        }
        sp2.edit().putBoolean(key, value).apply();
    }

    public static boolean getWyBoolean(Context context, String key) {
        if (sp2 == null) {
            sp2 = context.getSharedPreferences(SP_NAME_WY, Context.MODE_PRIVATE);
        }
        return sp2.getBoolean(key, false);
    }

    //保存ClientId
    public static void saveWyString(Context context, String key, String value) {
        if (sp2 == null) {
            sp2 = context.getSharedPreferences(SP_NAME_WY, Context.MODE_PRIVATE);
        }
        sp2.edit().putString(key, value).apply();
    }

    public static String getWyString(Context context, String key) {
        if (sp2 == null) {
            sp2 = context.getSharedPreferences(SP_NAME_WY, Context.MODE_PRIVATE);
        }
        return sp2.getString(key, "");
    }
}

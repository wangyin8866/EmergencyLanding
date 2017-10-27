package com.zyjr.emergencylending.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类
 * Created by neil on 2017/10/26
 */
public class ReflectionUtils {

    public static Map<String, String> beanToMap(Object obj) {
        Map<String, String> map = new HashMap<String, String>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object value = fields[i].get(obj);
                if (value != null && !"".equals(value) && !varName.equals("serialVersionUID")) {
//                    LogUtils.d("传入的对象中包含一个如下的变量：" + varName + " = " + value);
                    map.put(varName, value.toString());
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                LogUtils.d(obj.getClass().getSimpleName() + "beanToMap--ex--" + ex.getMessage());
                ex.printStackTrace();
            } catch (IllegalAccessException ex1) {
                LogUtils.d(obj.getClass().getSimpleName() + "beanToMap--ex1--" + ex1.getMessage());
                ex1.printStackTrace();
            }
        }
        return map;
    }
}

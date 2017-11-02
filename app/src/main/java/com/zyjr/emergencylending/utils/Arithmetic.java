package com.zyjr.emergencylending.utils;

import java.text.DecimalFormat;

/**
 * Created by wangyaping
 * 计算的类
 */
public class Arithmetic {
    public static double expenseByWeek(int money, int week) {
        DecimalFormat df = new DecimalFormat("#####0.0");
        Double amount = Double.valueOf(df.format(money * week * 2 * 0.003));
        return amount;
    }

    public static double expenseByDayDouble(int money, int day) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        Double amount = Double.valueOf(df.format(money * day * 0.003));
        return amount;
    }

    public static Integer expenseByDayInt(int money, int day) {
        DecimalFormat df = new DecimalFormat("#####0");
        Integer amount = Integer.valueOf(df.format(money * day * 0.003));
        return amount;
    }

    //减掉费用
    public static double subtraction(int money, double factorage) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        Double amount = Double.valueOf(df.format(money - factorage));
        return amount;
    }

    //本金
    public static double expenseByPrincipal(int money, int week) {
        DecimalFormat df = new DecimalFormat("#####0.0");
        Double amount = Double.valueOf(df.format(Double.valueOf(money) / week));
        return amount;
    }

    //管理费
    public static double expenseByManager(int money, int week) {
        DecimalFormat df = new DecimalFormat("#####0.0");
        Double amount = Double.valueOf(df.format(money * 7 * 0.003));
        return amount;
    }

    //金额加逗号
    public static String addComma(String str) {
        String reverseStr = new StringBuilder(str).reverse().toString();
        String strTemp = "";
        for (int i = 0; i < reverseStr.length(); i++) {
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }
        String resultStr = new StringBuilder(strTemp).reverse().toString();
        return resultStr;
    }


    public static int progressToMoney(int progress, int min, int max) {
        LogUtils.e("借款金额滑动进度:----------->" + progress);
        int money = 0;
        if (progress <= 0) {
            return min;
        } else if (progress > 0 && progress < 50) {
            money = (int) Math.floor(max * progress / 50);
            if (max > 10000) {
                if (money < min) {
                    return min;
                }
                if (money % 1000 != 0) {
                    int a = money / 1000; // 取7
                    a++;
                    money = a * 1000;
                }
            } else {
                if (money <= min) {
                    return min + (int) Math.floor(max * 1 / 50);
                }
            }
            return money;
        } else if (progress >= 50) {
            return max;
        }
        return money;
    }

    public static int progressToWeek11(int progress, int min, int max) {
//        LogUtils.e("借款周期滑动进度:----------->" + progress);
        int week = 0;
        if (progress <= 0) {
            return min;
        } else if (progress > 0 && progress < 50) {
            week = (int) Math.floor(max * progress / 50);
            if (week <= min) {
                if (min == 2) {
                    return 3;
                }
                return min;
            }
            return week;
        } else if (progress >= 50) {
            return max;
        }
        return week;
    }

    /**
     * @param progress
     * @param min
     * @param max
     * @param minType  最小借款周期 1.天；2.周
     * @return
     */
    public static String  progressToWeek(int progress, int min, int max, int minType) {
//        LogUtils.e("借款周期滑动进度:----------->" + progress);
        int week = 0;
        if (progress <= 0) {
            if (minType == 1) {
                return min + "天";
            }
            return min + "周";
        } else if (progress > 0 && progress < 50) {
            if (minType == 1) {
                week = (int) Math.floor(max * progress / 50); // 周
                // 最小是天,最大是周
                if (week * 7 > min) {
                    return week + "周";
                }
                return min + "天";
            } else if (minType == 2) {
                week = (int) Math.floor((max - min) * progress / 50); // 周
                if (min + week <= min) {
                    return min + "周";
                }
                return min + week + "周";
            }
            return week + "周";
        } else if (progress >= 50) {
            return max + "周";
        }
        return week + "周";
    }
}

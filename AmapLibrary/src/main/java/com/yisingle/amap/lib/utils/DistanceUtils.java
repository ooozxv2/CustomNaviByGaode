package com.yisingle.amap.lib.utils;

import java.math.BigDecimal;

public class DistanceUtils {

    /**
     * 将米装换公里
     * 如果小于1000米 则装换为米
     *
     * @param rice
     * @return
     */
    public static String getDistance(int rice) {
        String info = "";
        if (rice > 1000) {
            double ditance = rice * 0.001;
            BigDecimal b = new BigDecimal(ditance);
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            info = f1 + "公里";
        } else {
            info = rice + "米";
        }


        return info;
    }
}
package com.yisingle.amap.lib.utils;

import java.text.SimpleDateFormat;

public class TimeUtils {

    /**
     * 将s转为 xx小时xx分钟xx秒
     * 例如2000秒 转换为  33分钟
     *
     * @param time 秒
     * @return 字符串
     */
    public static String secToTime(int time) {
        int hour = time / 3600;
        int minute = time % 3600 / 60;
        int second = time % 60;

        String hourString = "";
        if (hour != 0) {
            hourString = hour + "小时";
        }
        String minuteString;
        if (minute != 0) {
            minuteString = minute + "分钟";
        } else {
            minuteString = "1分钟";
        }

//        String secondString = second + "秒";
        return hourString + minuteString;
    }

    public static String millis2String(long time) {

        ////初始化Formatter的转换格式。
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        String hm = formatter.format(time);
        return hm;
    }


}
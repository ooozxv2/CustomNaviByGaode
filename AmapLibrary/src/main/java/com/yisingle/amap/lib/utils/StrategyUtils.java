package com.yisingle.amap.lib.utils;

import android.support.annotation.DrawableRes;

import com.yisingle.amap.lib.R;
import com.yisingle.amap.lib.data.StrategyType;

/**
 * @author jikun
 *         Created by jikun on 2018/3/6.
 */

public class StrategyUtils {

    public static String getStrategyName(@StrategyType int strategyType) {
        String name = "";
        switch (strategyType) {
            case StrategyType.DRIVING_MULTIPLE_ROUTES_DEFAULT:
                name = "智能推荐";
                break;
            case StrategyType.DRIVING_MULTIPLE_ROUTES_AVOID_CONGESTION:
                name = "躲避拥堵";
                break;
            case StrategyType.DRIVING_MULTIPLE_ROUTES_AVOID_HIGHSPEED:
                name = "不走高速";
                break;
            case StrategyType.DRIVING_MULTIPLE_ROUTES_PRIORITY_HIGHSPEED:
                name = "高速优先";
                break;
            default:
                break;


        }
        return name;
    }

    public static @DrawableRes
    int getStrategyDrawableId(@StrategyType int strategyType) {
        int drawableId = R.drawable.selected_intelligent_bg;
        switch (strategyType) {
            case StrategyType.DRIVING_MULTIPLE_ROUTES_DEFAULT:
                drawableId = R.drawable.selected_intelligent_bg;
                break;
            case StrategyType.DRIVING_MULTIPLE_ROUTES_AVOID_CONGESTION:
                drawableId = R.drawable.selected_avoid_congestion_bg;
                break;
            case StrategyType.DRIVING_MULTIPLE_ROUTES_AVOID_HIGHSPEED:
                drawableId = R.drawable.selected_avoid_highspeed_bg;
                break;
            case StrategyType.DRIVING_MULTIPLE_ROUTES_PRIORITY_HIGHSPEED:
                drawableId = R.drawable.selected_proiority_high_speed_bg;
                break;
            default:
                break;


        }
        return drawableId;
    }
}

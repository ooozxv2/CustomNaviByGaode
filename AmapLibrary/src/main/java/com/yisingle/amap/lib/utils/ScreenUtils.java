package com.yisingle.amap.lib.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * @author jikun
 *         Created by jikun on 2018/4/4.
 */

public class ScreenUtils {


    public static int getScreenWidth() {
        Resources resources = ContextUtils.getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return width;
    }


}

package com.yisingle.amap.lib.utils;

import android.content.Context;

/**
 * @author jikun
 *         Created by jikun on 2018/4/4.
 */

public class ContextUtils {

    private static Context context;


    public static Context getContext() {
        return context;
    }

    public static void init(Context c) {
        context = c;

    }


}

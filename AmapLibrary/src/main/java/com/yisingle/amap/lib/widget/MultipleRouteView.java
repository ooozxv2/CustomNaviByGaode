package com.yisingle.amap.lib.widget;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.navi.AMapNavi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/4/9.
 */
public class MultipleRouteView {

    private List<SimpleRouteView> simpleRouteViewList;

    private int selectRouteId;

    private AMapNavi aMapNavi;


    public MultipleRouteView() {
        this.simpleRouteViewList = new ArrayList<>();
    }

    public void drawMultipleView(Context context, AMap amap, int[] routeIds, AMapNavi mapNavi) {
        aMapNavi = mapNavi;
        for (int i = 0; i < routeIds.length; i++) {
            boolean isChoose = false;
            SimpleRouteView mapRouteView = new SimpleRouteView();
            mapRouteView.drawView(context, amap, routeIds[i], mapNavi);
            simpleRouteViewList.add(mapRouteView);
            if (i == 0) {
                //刚开始画路线的时候是默认选中第一条线的。
                selectRouteId = routeIds[i];
                mapRouteView.selectRouteOverLay(true);
            }
        }
    }

    /**
     * 通过routeId选中路线
     *
     * @param routeId
     */
    public void selectRouteOverLayByRouteId(int routeId) {
        selectRouteId = routeId;
        if (null != aMapNavi) {
            aMapNavi.selectRouteId(routeId);
        }
        for (SimpleRouteView view : simpleRouteViewList) {
            view.selectRouteOverLay(view.getRouteId() == routeId ? true : false);
        }

    }


    /**
     * 获取当前选中的RouteId
     *
     * @return
     */
    public int getSelectRouteId() {

        return selectRouteId;

    }

    public void cleanMultipleView() {
        for (SimpleRouteView view : simpleRouteViewList) {
            view.cleanView();
        }
        simpleRouteViewList.clear();
        aMapNavi = null;

    }
}

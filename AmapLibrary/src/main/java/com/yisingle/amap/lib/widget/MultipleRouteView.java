package com.yisingle.amap.lib.widget;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviLatLng;

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

    private OnItemClickListener onItemClickListener;


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

        amap.setOnPolylineClickListener(new AMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                if (polyline.getZIndex() == SimpleRouteView.HIGH_Z_INDEX) {

                    return;

                } else {
                    //对点击的路径点进行选中
                    List<NaviLatLng> poltlinenaviLatLngList = new ArrayList<>();
                    List<LatLng> latLngList = polyline.getPoints();
                    for (LatLng latLng : latLngList) {
                        //将点击的路线的点加入到List列表中
                        poltlinenaviLatLngList.add(new NaviLatLng(latLng.latitude, latLng.longitude));
                    }


                    for (int i = 0; i < simpleRouteViewList.size(); i++) {

                        AMapNaviPath naviPath = simpleRouteViewList.get(i).getRouteOverLay().getAMapNaviPath();
                        //获取画在地图路线的所有点
                        List<NaviLatLng> naviLatLngList = naviPath.getCoordList();


                        //默认假设是在点击的Polyline的坐标点 都属于 地图上画的路线其中一条
                        boolean isOnLine = true;

                        for (NaviLatLng naviLatLng : poltlinenaviLatLngList) {
                            //判断点击的坐标是否在 地图上画的路线的其中一条
                            if (!naviLatLngList.contains(naviLatLng)) {
                                //如果不在就为false 跳出这个循环。
                                isOnLine = false;
                                break;
                            }
                        }

                        if (isOnLine) {
                            //如果isOnLine为true  那么就是是在这个线上 那么说明Polyline是在地图上的画的线的一部分。那么可以跳出循环
                            if (null != onItemClickListener) {
                                onItemClickListener.onItemClick(i);
                            }

                            break;
                        }


                    }
                }

            }
        });
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
     * 通过routeId选中路线
     *
     * @param index
     */
    public void selectRouteOverLayByIndex(int index) {

        for (int i = 0; i < simpleRouteViewList.size(); i++) {
            if (i == index) {
                selectRouteId = simpleRouteViewList.get(i).getRouteId();
                selectRouteOverLayByRouteId(selectRouteId);
                break;
            }
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

    public List<SimpleRouteView> getSimpleRouteViewList() {
        return simpleRouteViewList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }
}

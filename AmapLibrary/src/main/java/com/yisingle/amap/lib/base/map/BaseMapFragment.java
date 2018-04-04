package com.yisingle.amap.lib.base.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;

/**
 * @author jikun
 *         Created by jikun on 2018/3/30.
 */

public abstract class BaseMapFragment extends Fragment {

    private TextureMapView textureMapView;


    protected TextureMapView getMapView() {
        return textureMapView;
    }

    protected void initMapView(Bundle savedInstanceState, TextureMapView textureMapView) {
        this.textureMapView = textureMapView;


        if (null != getMapView() && null != getMapView().getMap()) {

            getMapView().getMap().setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                @Override
                public void onMapLoaded() {
                    //地图MapView加载完成后回调 用来UiSetting设置参数
                    afterMapViewLoad();

                }
            });

            //调用TextureMapView.onCreate方法来
            getMapView().onCreate(savedInstanceState);

        }
    }

    /**
     * 地图MapView加载完成后回调
     */
    protected abstract void afterMapViewLoad();

    /**
     * 方法必须重写
     */
    @Override

    public void onResume() {
        super.onResume();
        if (null != getMapView()) {
            getMapView().onResume();
        }

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        if (null != getMapView()) {
            getMapView().onPause();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != getMapView()) {
            getMapView().onSaveInstanceState(outState);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (null != getMapView()) {
            getMapView().onDestroy();
        }
    }


}

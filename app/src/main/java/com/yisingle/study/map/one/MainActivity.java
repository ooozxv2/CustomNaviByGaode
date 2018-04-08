package com.yisingle.study.map.one;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yisingle.amap.lib.GaoDeErrorUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RouteSearch routeSearch;

    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApikey();
        getAndPermission();
        tvInfo = findViewById(R.id.tvInfo);
        routeSearch = new RouteSearch(this);


        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {


                tvInfo.setText(GaoDeErrorUtils.getErrorInfo(i));

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        startConfimGaode();

    }

    public void startConfimGaode() {
        LatLonPoint from = new LatLonPoint(30.537107, 104.06951);
        LatLonPoint to = new LatLonPoint(30.657349, 104.065837);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from, to);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_SINGLE_SHORTEST, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);
        tvInfo.setText("使用地图的路径规划中");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void test(View view) {
        startConfimGaode();
    }


    public void toNavi(View view) {
        Intent intent = new Intent(this, NaviActivity.class);
        startActivity(intent);
    }

    public void getApikey() {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo == null) {
            return;
        }
        String value = applicationInfo.metaData.getString("com.amap.api.v2.apikey");
        Log.e("测试代码", "测试代码Apikey=" + value);
    }

    public void getAndPermission() {
        //定位权限对于地图应用是非常重要的请一定允许
        AndPermission.with(this)
                .permission(
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION,
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_PHONE_STATE
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // TODO what to do.
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String p : permissions) {
                            stringBuilder.append("\n");
                            stringBuilder.append(p);
                        }
                        Log.e("测试代码", "权限信息onGranted\n" + stringBuilder.toString());
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // TODO what to do
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String p : permissions) {
                            stringBuilder.append("\n");
                            stringBuilder.append(p);
                        }
                        Log.e("测试代码", "权限信息onDenied\n" + stringBuilder.toString());
                        Toast.makeText(getApplicationContext(), stringBuilder.toString() + "权限被拒绝", Toast.LENGTH_SHORT).show();
                    }
                }).start();
    }
}

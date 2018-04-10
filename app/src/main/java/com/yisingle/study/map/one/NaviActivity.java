package com.yisingle.study.map.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.navi.model.NaviLatLng;
import com.yisingle.amap.lib.data.NaviActionData;
import com.yisingle.amap.lib.data.StrategyType;
import com.yisingle.amap.lib.fragment.NaviFragment;

/**
 * @author jikun
 * Created by jikun on 2018/3/29.
 */

public class NaviActivity extends AppCompatActivity {

    public static final String KEY="naviData";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);


        NaviActionData naviActionData= getIntent().getParcelableExtra(KEY);


        NaviFragment naviFragment = NaviFragment.newInstance(naviActionData);

        getSupportFragmentManager().beginTransaction().replace(R.id.rlNaviContent, naviFragment).commitAllowingStateLoss();


    }
}

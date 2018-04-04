package com.yisingle.amap.lib.data;

import android.support.annotation.IntDef;

import com.amap.api.navi.enums.PathPlanningStrategy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//添加支持注解的依赖到你的项目中，需要在build.gradle文件中的依赖块中添加：
//dependencies { compile 'com.android.support:support-annotations:24.2.0' }
@IntDef({StrategyType.DRIVING_MULTIPLE_ROUTES_DEFAULT
        , StrategyType.DRIVING_MULTIPLE_ROUTES_AVOID_CONGESTION
        , StrategyType.DRIVING_MULTIPLE_ROUTES_AVOID_HIGHSPEED
        , StrategyType.DRIVING_MULTIPLE_ROUTES_PRIORITY_HIGHSPEED
        , StrategyType.DRIVING_SHORTEST_DISTANCE})
@Retention(RetentionPolicy.SOURCE)
public @interface StrategyType {

    /**
     * 在ui上显示的名称=智能推荐
     * 多路径，时间最短，距离最短（考虑路况）【对应高德地图App不设置任何偏好的策略】
     */
    int DRIVING_MULTIPLE_ROUTES_DEFAULT = PathPlanningStrategy.DRIVING_MULTIPLE_ROUTES_DEFAULT;

    /**
     * 在ui上显示的名称=躲避拥堵
     * 多路径，躲多避拥堵（考虑路况）【对应高德地图App设置避让用户偏好的策略】
     */
    int DRIVING_MULTIPLE_ROUTES_AVOID_CONGESTION = PathPlanningStrategy.DRIVING_MULTIPLE_ROUTES_AVOID_CONGESTION;


    /**
     * 在ui上显示的名称=不走高速
     * 多路径，不走高速【对应高德地图App不走高速的规划偏好】
     */
    int DRIVING_MULTIPLE_ROUTES_AVOID_HIGHSPEED = PathPlanningStrategy.DRIVING_MULTIPLE_ROUTES_AVOID_HIGHSPEED;


    /**
     * 在ui上显示的名称=高速优先
     * 多路径，高速优先【对应高德地图App高速优先的规划偏好】
     */
    int DRIVING_MULTIPLE_ROUTES_PRIORITY_HIGHSPEED = PathPlanningStrategy.DRIVING_MULTIPLE_ROUTES_PRIORITY_HIGHSPEED;


    /**
     * 单路径 距离最短
     */
    int DRIVING_SHORTEST_DISTANCE = PathPlanningStrategy.DRIVING_SHORTEST_DISTANCE;

}
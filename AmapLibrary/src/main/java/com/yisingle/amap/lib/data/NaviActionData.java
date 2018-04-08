package com.yisingle.amap.lib.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.amap.api.navi.model.NaviLatLng;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author jikun
 * Created by jikun on 2018/4/3.
 */

public class NaviActionData implements Parcelable {

    /**
     * 车辆位置
     */
    private NaviLatLng carLatlng;
    /**
     * 乘客设置起点
     */
    private NaviLatLng startLatlng;

    /**
     * 乘客设置终点
     */
    private NaviLatLng endLatlng;


    private @Type
    int type;

    /**
     * 导航策略值
     */
    private @StrategyType
    int strategy;


    /**
     * 算路成功后是否立即导航 这个主要是在  onCalculateRouteSuccess中计算
     * 否 则先在MapView上画线 是则立即导航
     */
    private boolean isNaviRightNow;


    /**
     * 是否是模拟导航  true为模拟导航 和 false为GPS导航
     */
    private boolean isEmulatorNavi = false;

    /**
     * 建立导航去乘客起点的数据
     * 乘客和终点坐标都需要重新传递
     * 是因为有可能在其他功能的时候用到两个坐标。
     *
     * @param carLatlng   当前车辆位置可以为null.
     * @param startLatlng 乘客起点位置不能为null,必须有值。
     * @param endLatlng   乘客起点位置不能为null,必须有值。
     */
    private NaviActionData(@Nullable NaviLatLng carLatlng, @NonNull NaviLatLng startLatlng, @NonNull NaviLatLng endLatlng) {
        this.carLatlng = carLatlng;
        this.startLatlng = startLatlng;
        this.endLatlng = endLatlng;
        this.type = Type.DoNoThing;
        this.strategy = StrategyType.DRIVING_SHORTEST_DISTANCE;
        this.isNaviRightNow = false;
    }

    private NaviActionData() {
        this.type = Type.DoNoThing;
        this.strategy = StrategyType.DRIVING_SHORTEST_DISTANCE;
        this.isNaviRightNow = false;
        this.isEmulatorNavi = false;
    }

    public @StrategyType
    int getStrategy() {
        return strategy;
    }

    public void setStrategy(@StrategyType int strategy) {
        this.strategy = strategy;
    }


    public boolean isNaviRightNow() {
        return isNaviRightNow;
    }

    public void setNaviRightNow(boolean naviRightNow) {
        isNaviRightNow = naviRightNow;
    }


    public static class Builder {


        private NaviActionData data;


        public Builder() {
            data = new NaviActionData();
        }


        public Builder setCarLatlng(@Nullable NaviLatLng carLatlng) {
            data.setCarLatlng(carLatlng);
            return this;
        }

        public Builder setStartLatlng(@NonNull NaviLatLng startLatlng) {
            data.setStartLatlng(startLatlng);
            return this;
        }

        public Builder setEndLatlng(@NonNull NaviLatLng endLatlng) {
            data.setEndLatlng(endLatlng);
            return this;
        }

        public Builder setType(@Type int type) {
            data.setType(type);
            return this;
        }

        public Builder setStrategy(@StrategyType int strategy) {
            data.setStrategy(strategy);
            return this;
        }

        public Builder setEmulatorNavi(boolean emulatorNavi) {
            data.setEmulatorNavi(emulatorNavi);
            return this;
        }

        /**
         * @param naviRightNow 是否在算路成功后立即导航。
         * @return Builder
         */
        public Builder setNaviRightNow(boolean naviRightNow) {
            data.setNaviRightNow(naviRightNow);
            return this;
        }


        /**
         * 建立导航去乘客起点的数据
         *
         * @param carNaviLatLng   当前车辆位置可以为null.
         * @param startNaviLatLng 乘客起点位置不能为null,必须有值。
         */
        public NaviActionData buildStart(@Nullable NaviLatLng carNaviLatLng, @NonNull NaviLatLng startNaviLatLng) {
            data.setCarLatlng(carNaviLatLng);
            data.setStartLatlng(startNaviLatLng);
            data.setType(Type.NaviToStart);
            return data;
        }

        public NaviActionData buildEnd(@Nullable NaviLatLng carNaviLatLng, @NonNull NaviLatLng endNaviLatLng) {
            data.setCarLatlng(carNaviLatLng);
            data.setEndLatlng(endNaviLatLng);
            data.setType(Type.NaviToEnd);
            return data;
        }


        public NaviActionData buildNothing() {
            data.setType(Type.DoNoThing);
            return data;
        }

        public NaviActionData build(@Type int type) {
            data.setType(type);
            return data;
        }
    }


    //添加支持注解的依赖到你的项目中，需要在build.gradle文件中的依赖块中添加：
    //dependencies { compile 'com.android.support:support-annotations:24.2.0' }
    @IntDef({Type.DoNoThing, Type.NaviToStart, Type.NaviToEnd})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int DoNoThing = -1;
        int NaviToStart = 1;
        int NaviToEnd = 2;

    }

    public NaviLatLng getCarLatlng() {
        return carLatlng;
    }

    public void setCarLatlng(NaviLatLng carLatlng) {
        this.carLatlng = carLatlng;
    }

    public NaviLatLng getStartLatlng() {
        return startLatlng;
    }

    public void setStartLatlng(NaviLatLng startLatlng) {
        this.startLatlng = startLatlng;
    }

    public NaviLatLng getEndLatlng() {
        return endLatlng;
    }

    public void setEndLatlng(NaviLatLng endLatlng) {
        this.endLatlng = endLatlng;
    }


    public boolean isEmulatorNavi() {
        return isEmulatorNavi;
    }

    public void setEmulatorNavi(boolean emulatorNavi) {
        isEmulatorNavi = emulatorNavi;
    }

    public @Type
    int getType() {
        return type;
    }

    public void setType(@Type int type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.carLatlng, flags);
        dest.writeParcelable(this.startLatlng, flags);
        dest.writeParcelable(this.endLatlng, flags);
        dest.writeInt(this.type);
        dest.writeInt(this.strategy);
        dest.writeByte(this.isNaviRightNow ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isEmulatorNavi ? (byte) 1 : (byte) 0);
    }

    protected NaviActionData(Parcel in) {
        this.carLatlng = in.readParcelable(NaviLatLng.class.getClassLoader());
        this.startLatlng = in.readParcelable(NaviLatLng.class.getClassLoader());
        this.endLatlng = in.readParcelable(NaviLatLng.class.getClassLoader());
        this.type = in.readInt();
        this.strategy = in.readInt();
        this.isNaviRightNow = in.readByte() != 0;
        this.isEmulatorNavi = in.readByte() != 0;
    }

    public static final Creator<NaviActionData> CREATOR = new Creator<NaviActionData>() {
        @Override
        public NaviActionData createFromParcel(Parcel source) {
            return new NaviActionData(source);
        }

        @Override
        public NaviActionData[] newArray(int size) {
            return new NaviActionData[size];
        }
    };
}

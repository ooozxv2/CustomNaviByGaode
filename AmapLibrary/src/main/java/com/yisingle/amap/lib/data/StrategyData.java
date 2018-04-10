package com.yisingle.amap.lib.data;

import android.support.annotation.DrawableRes;

import com.yisingle.amap.lib.utils.StrategyUtils;

/**
 * @author jikun
 *         Created by jikun on 2018/3/6.
 */

public class StrategyData {


    /**
     * PathPlanningStrategy
     */
    private @StrategyType
    int strate;
    private String strateName;
    private boolean isSelected;

    private @DrawableRes
    int drawableId;

    public StrategyData(@StrategyType int strate) {
        this.strate = strate;
        this.strateName = StrategyUtils.getStrategyName(strate);
        this.isSelected = false;
        this.drawableId = StrategyUtils.getStrategyDrawableId(strate);
    }

    public StrategyData(@StrategyType int strate, boolean isSelected) {
        this.strate = strate;
        this.strateName = StrategyUtils.getStrategyName(strate);
        this.isSelected = isSelected;
        this.drawableId = StrategyUtils.getStrategyDrawableId(strate);
    }

    public @StrategyType
    int getStrate() {
        return strate;
    }

    public void setStrate(@StrategyType int strate) {
        this.strate = strate;
    }

    public String getStrateName() {
        return strateName;
    }

    public void setStrateName(String strateName) {
        this.strateName = strateName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}

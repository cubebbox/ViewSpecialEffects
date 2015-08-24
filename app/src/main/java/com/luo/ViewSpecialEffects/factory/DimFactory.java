package com.luo.ViewSpecialEffects.factory;

import android.view.View;

/**
 * Created by luozi on 2015/8/23.
 */
public class DimFactory {
    public static View BluringBg = null;//模糊背景
    public int DURATION = 1000;//渐入时间
    public int BLUR_RADIUS = 15;//模糊度
    public int OVERLAY_COLOR = 0x33FFFFFF;//覆盖层颜色

    public boolean ClickOutSideExit = false;

    public DimFactory setBluringBg(View bluringBg) {
        BluringBg = bluringBg;
        return this;
    }

    public DimFactory setDURATION(int DURATION) {
        this.DURATION = DURATION;
        return this;
    }

    public DimFactory setBLUR_RADIUS(int BLUR_RADIUS) {
        this.BLUR_RADIUS = BLUR_RADIUS;
        return this;
    }

    public DimFactory setOVERLAY_COLOR(int OVERLAY_COLOR) {
        this.OVERLAY_COLOR = OVERLAY_COLOR;
        return this;
    }

    public DimFactory setClickOutSideExit(boolean clickOutSideExit) {
        ClickOutSideExit = clickOutSideExit;
        return this;
    }
}

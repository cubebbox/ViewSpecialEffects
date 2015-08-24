package com.luo.ViewSpecialEffects.factory;

import android.view.View;

import com.luo.ViewSpecialEffects.data.D;

import java.io.Serializable;

/**
 * Created by luozi on 2015/8/19.
 */
public class SlideFactory implements Serializable {
    public static View BluringBg = null;//模糊背景
    public int duration = 500;
    public int SLIDE_COE = 200;//滑动系数
    public int VELOCITY_COE = 1200;//滑动速率
    public int MOVE_MODE = D.MOVE_LEVEL;//滑动模式
    public int BG_MODE = D.BG_TRANSPARENT;//背景模式
    public int ANGLE_MAX = 15;//最大旋转角度
    public float ALPHA_MAX = 1f;//max alpha
    public boolean IS_DRAG_LEFT = true, IS_DRAG_RIGHT = true;//是否可以左拖动弄，右拖动
    public int OVERLAY_COLOR = 0x66000000;//覆盖层颜色
    public int BLUR_RADIUS = 15;//模糊度

    public SlideFactory setDuration(int duration) {
        this.duration = duration;
        return this;
    }


    /**
     * 滑动系数
     */
    public SlideFactory setSLIDE_COE(int SLIDE_COE) {
        this.SLIDE_COE = SLIDE_COE;
        return this;
    }


    /**
     * 拖动速率
     */
    public SlideFactory setVELOCITY_COE(int VELOCITY_COE) {
        this.VELOCITY_COE = VELOCITY_COE;
        return this;
    }


    /**
     * 移动模式
     */
    public SlideFactory setMOVE_MODE(int MOVE_MODE) {
        this.MOVE_MODE = MOVE_MODE;
        return this;
    }


    /**
     * 背景模式
     */
    public SlideFactory setBG_MODE(int BG_MODE) {
        this.BG_MODE = BG_MODE;
        return this;
    }


    /**
     * 角度
     */
    public SlideFactory setANGLE_MAX(int ANGLE_MAX) {
        this.ANGLE_MAX = ANGLE_MAX;
        return this;
    }


    /**
     * 透明度
     */
    public SlideFactory setALPHA_MAX(float ALPHA_MAX) {
        this.ALPHA_MAX = ALPHA_MAX;
        return this;
    }


    /**
     * is left drag
     */
    public SlideFactory setIS_DRAG_LEFT(boolean IS_DRAG_LEFT) {
        this.IS_DRAG_LEFT = IS_DRAG_LEFT;
        return this;
    }


    /**
     * is right drag
     */
    public SlideFactory setIS_DRAG_RIGHT(boolean IS_DRAG_RIGHT) {
        this.IS_DRAG_RIGHT = IS_DRAG_RIGHT;
        return this;
    }

    /**
     * set over lay color
     */
    public SlideFactory setOVERLAY_COLOR(int OVERLAY_COLOR) {
        this.OVERLAY_COLOR = OVERLAY_COLOR;
        return this;
    }

    /**
     * set blur radius
     */
    public SlideFactory setBLUR_RADIUS(int BLUR_RADIUS) {
        this.BLUR_RADIUS = BLUR_RADIUS;
        return this;
    }
    /**
     * set blurry widget view
     * */
    public  SlideFactory setBluringBg(View bluringBg) {
        BluringBg = bluringBg;
        return this;
    }
}

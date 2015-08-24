package com.luo.ViewSpecialEffects.Interface;

/**
 * Created by luozi on 2015/8/22.
 */
public interface OnActivityChangeListener {

    /**
     * 滚动开始
     */
    void scrollStart();

    /**
     * 滚动中
     */
    void scrolling(float currX, float finalX);

    /**
     * 滚动结束
     */
    void scrollEnd(boolean isExit);
}

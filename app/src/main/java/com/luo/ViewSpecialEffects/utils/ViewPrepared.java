package com.luo.ViewSpecialEffects.utils;

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by luozi on 2015/8/15.
 */
public class ViewPrepared {

    private boolean hasMeasured = false;

    public void asyncPrepare(final View view, final OnPreDrawFinishListener onPreDrawFinishListener) {

        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (hasMeasured == false) {
                    int height = view.getMeasuredHeight();
                    int width = view.getMeasuredWidth();
                    //获取到宽度和高度后，可用于计算
                    if (onPreDrawFinishListener != null)
                        onPreDrawFinishListener.onPreDrawFinish(width, height);
                    hasMeasured = true;
                }
                return true;
            }
        });
    }

    public interface OnPreDrawFinishListener {
        void onPreDrawFinish(int w, int h);
    }

}

package com.luo.ViewSpecialEffects.factory;

import java.io.Serializable;

/**
 * Created by luozi on 2015/8/20.
 */
public class RevealFactory implements Serializable {
    public int duration = 1000;
    public int startX = 0;
    public int startY = 0;
    public int endX = 0;
    public int endY = 0;

    public RevealFactory setStartX(int startX) {
        this.startX = startX;
        return this;
    }

    public RevealFactory setStartY(int startY) {
        this.startY = startY;
        return this;
    }

    public RevealFactory setEndX(int endX) {
        this.endX = endX;
        return this;
    }

    public RevealFactory setEndY(int endY) {
        this.endY = endY;
        return this;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

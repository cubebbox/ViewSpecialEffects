package com.luo.ViewSpecialEffects.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by luozi on 2015/8/21.
 */
public class TestReltive extends RelativeLayout {
    public TestReltive(Context context) {
        super(context);
        init();
    }

    public TestReltive(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestReltive(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}

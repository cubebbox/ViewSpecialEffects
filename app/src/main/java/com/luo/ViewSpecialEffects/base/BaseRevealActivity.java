package com.luo.ViewSpecialEffects.base;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import com.luo.R;
import com.luo.ViewSpecialEffects.factory.RevealFactory;
import com.luo.ViewSpecialEffects.utils.SkipUtils;
import com.luo.ViewSpecialEffects.utils.ViewPrepared;
import com.luo.ViewSpecialEffects.widget.RelativeRevealView;


public abstract class BaseRevealActivity extends Activity {
    protected RelativeRevealView slideGroupView = null;
    protected View slideLayoutView = null;

    // =======object
    private RevealFactory factory = null;
    // =======object

    // =====
    private int screenWidth = 0, screenHeight = 0;
    private boolean IS_ME_EXIT = false;//是否我本界面调用的方法退出
    // =====

    //===
    //===

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //首先获取要初始化的参数
        init();
        setContentView(setView());
        enterActivity();

    }

    private void init() {
        setFactory(SkipUtils.getRevealFactory());
        if (factory == null) factory = new RevealFactory();

        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;// 防止int转换的误差，先记录宽高
    }

    protected void enterActivity() {
        new ViewPrepared().asyncPrepare(slideGroupView, new ViewPrepared.OnPreDrawFinishListener() {
            @Override
            public void onPreDrawFinish(int w, int h) {
                slideGroupView.show(factory.startX, factory.startY, factory.duration, null);
            }
        });

    }

    /**
     * 设置布局
     */
    private View setView() {
        slideGroupView = new RelativeRevealView(this);//init object

        //layout view init
        slideLayoutView = getLayoutInflater().inflate(getLayoutID(), null);

        RelativeLayout.LayoutParams allParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);// params init
        slideGroupView.setLayoutParams(allParams);
        slideLayoutView.setLayoutParams(allParams);


        slideGroupView.addView(slideLayoutView);
        return slideGroupView;
    }

    public abstract int getLayoutID();


    @Override
    public void finish() {
        if (IS_ME_EXIT) {
            super.finish();
            overridePendingTransition(R.anim.keep, R.anim.keep);
        } else {
            exitActivity();
        }
    }

    @Override
    public void onBackPressed() {
        exitActivity();
    }


    /**
     * exit this activity
     */
    protected void exitActivity(int x, int y) {
        factory.setEndX(x);
        factory.setEndY(y);
        //simulate drag right and exit
        slideGroupView.hide(factory.endX, factory.endY, factory.duration, new RelativeRevealView.AnimaFinshListener() {
            @Override
            public void onAnimFinish(Animator animation) {
                IS_ME_EXIT = true;
                finish();
            }
        });
    }

    /**
     * exit this activity
     */
    protected void exitActivity() {
        //simulate drag right and exit
        slideGroupView.hide(factory.duration, new RelativeRevealView.AnimaFinshListener() {
            @Override
            public void onAnimFinish(Animator animation) {
                IS_ME_EXIT = true;
                finish();
            }
        });
    }

    public void setFactory(RevealFactory factory) {
        this.factory = factory;
    }
}

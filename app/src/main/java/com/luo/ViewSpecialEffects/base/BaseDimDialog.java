package com.luo.ViewSpecialEffects.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.luo.R;
import com.luo.ViewSpecialEffects.adapter.AnimFinishAdapter;
import com.luo.ViewSpecialEffects.factory.DimFactory;
import com.luo.ViewSpecialEffects.utils.SkipUtils;
import com.luo.ViewSpecialEffects.utils.ViewPrepared;
import com.luo.ViewSpecialEffects.widget.BlurringView;

/**
 * Created by luozi on 2015/8/23.
 */
public abstract class BaseDimDialog extends Dialog {
    private RelativeLayout groupView = null;
    private BlurringView blurringView = null;
    private View layoutView = null;

    private DimFactory factory = null;

    private boolean IS_ME_EXIT = false, IS_DISMISSING = false;


    public BaseDimDialog(Context context) {
        super(context, R.style.trans_dialog);
    }

    public BaseDimDialog(Context context, int themeResId) {
        super(context, R.style.trans_dialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFactory(SkipUtils.dimFactory);
        init();
        initView();
        setView();
    }

    private void init() {

    }

    private void initView() {
        groupView = new RelativeLayout(getContext());
        groupView.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams groupParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);// params init
        groupView.setLayoutParams(groupParams);


        blurringView = new BlurringView(getContext());
        blurringView.setVisibility(View.GONE);


        layoutView = getLayoutInflater().inflate(getLayoutID(), null);

        new ViewPrepared().asyncPrepare(layoutView, new ViewPrepared.OnPreDrawFinishListener() {
            @Override
            public void onPreDrawFinish(int w, int h) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                blurringView.setVisibility(View.VISIBLE);

                Animation alphaIn = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_in);
                alphaIn.setDuration(factory.DURATION);
                blurringView.startAnimation(alphaIn);

                RelativeLayout.LayoutParams allParams = new RelativeLayout.LayoutParams(w, h);// params init
                blurringView.setLayoutParams(allParams);
                blurringView.setBlurredView(factory.BluringBg);
                blurringView.setBlurRadius(factory.BLUR_RADIUS);
                blurringView.setOverlayColor(factory.OVERLAY_COLOR);
            }
        });

        groupView.addView(blurringView);
        groupView.addView(layoutView);
        setContentView(groupView);
    }


    private void setView() {
        blurringView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        groupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (factory.ClickOutSideExit) {
                    dismiss();
                }
            }
        });
    }

    public abstract int getLayoutID();

    public void setFactory(DimFactory factory) {
        if (factory == null) factory = new DimFactory();
        this.factory = factory;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        if (IS_ME_EXIT) {
            super.dismiss();
        } else {
            exitDialog();
        }
    }

    private void exitDialog() {
        if (IS_DISMISSING) return;
        IS_DISMISSING = true;
        startAnimExit();
        Animation alphaOut = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_out);
        alphaOut.setDuration(factory.DURATION);
        alphaOut.setAnimationListener(new AnimFinishAdapter() {
            @Override
            public void end() {
                IS_ME_EXIT = true;
                dismiss();
            }
        });
        groupView.startAnimation(alphaOut);

    }

    /**
     * 更新背景模糊视图
     */
    protected void updateBgBluringView() {
        blurringView.invalidate();
    }

    protected abstract void startAnimExit();
}

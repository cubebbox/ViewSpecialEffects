package com.luo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.luo.R;
import com.luo.ViewSpecialEffects.Interface.OnViewChangeListener;
import com.luo.ViewSpecialEffects.data.D;
import com.luo.ViewSpecialEffects.factory.DimFactory;
import com.luo.ViewSpecialEffects.factory.RevealFactory;
import com.luo.ViewSpecialEffects.factory.SlideFactory;
import com.luo.ViewSpecialEffects.utils.DensityUtil;
import com.luo.ViewSpecialEffects.utils.ScreenUtils;
import com.luo.ViewSpecialEffects.utils.SkipUtils;
import com.luo.activity.dialog.BgDimDialog;
import com.luo.activity.dialog.DimDialog;

import java.util.Random;


public class MainActivity extends Activity {

    RelativeLayout bgLayout;
    private static ImageView imageView;
    private Button b1, b2, b3, b4, b5, b6;
    private static Button startAnim;

    private static Random mRandom = new Random();
    private static MainActivity mainActivity;

    public static OnViewChangeListener onViewChangeListener = null;
    public static boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        bgLayout = (RelativeLayout) findViewById(R.id.act_main_parent);
        imageView = (ImageView) findViewById(R.id.main_img);
        b1 = (Button) findViewById(R.id.main_b1);
        b2 = (Button) findViewById(R.id.main_b2);
        b3 = (Button) findViewById(R.id.main_b3);
        b4 = (Button) findViewById(R.id.main_b4);
        b5 = (Button) findViewById(R.id.main_b5);
        b6 = (Button) findViewById(R.id.main_b6);
        startAnim = (Button) findViewById(R.id.main_start_anim);

        mainActivity = this;

        //透明背景退出
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideFactory factory = new SlideFactory();
                factory.setDuration(500)
                        .setALPHA_MAX(1f)
                        .setIS_DRAG_LEFT(false)
                        .setIS_DRAG_RIGHT(true)
                        .setMOVE_MODE(D.MOVE_LEVEL)
                        .setBG_MODE(D.BG_TRANSPARENT)
                        .setVELOCITY_COE(1500)//滑动速率，滑动速度超过此速率将会退出activity
                        .setSLIDE_COE(DensityUtil.dip2px(MainActivity.this, 120));

                SkipUtils.gotoActivity(MainActivity.this, TransSlideActivity.class, factory);
            }
        });
        //模糊背景退出
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideFactory factory = new SlideFactory();
                factory.setDuration(500)
                        .setIS_DRAG_LEFT(true)
                        .setIS_DRAG_RIGHT(true)
                        .setMOVE_MODE(D.MOVE_LEVEL)
                        .setBG_MODE(D.BG_BLURRY)
                        .setBLUR_RADIUS(15)
                        .setBluringBg(bgLayout)
                        .setOVERLAY_COLOR(Color.argb(99, 0, 0, 0))
                        .setSLIDE_COE(DensityUtil.dip2px(MainActivity.this, 120));

                SkipUtils.gotoActivity(MainActivity.this, BlurrySlideActivity.class, factory);
            }
        });
        //旋转背景退出
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideFactory factory = new SlideFactory();
                factory.setDuration(1000)
                        .setALPHA_MAX(1f)
                        .setIS_DRAG_LEFT(false)
                        .setIS_DRAG_RIGHT(true)
                        .setMOVE_MODE(D.MOVE_ROTATION)
                        .setBG_MODE(D.BG_TRANSPARENT)
                        .setSLIDE_COE(DensityUtil.dip2px(MainActivity.this, 120));

                SkipUtils.gotoActivity(MainActivity.this, RotationSlideActivity.class, factory);
            }
        });
        //圆形背景退出
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RevealFactory factory = new RevealFactory();
                factory.setStartX((int) v.getX() + (v.getWidth() / 2))
                        .setStartY((int) v.getY() + (v.getHeight() / 2))
                        .setDuration(1000);

                SkipUtils.gotoActivity(MainActivity.this, CircleRevealActivity.class, factory);
            }
        });
        //模糊dialog
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DimFactory factory = new DimFactory();
                factory.setDURATION(500)
                        .setBluringBg(bgLayout)
                        .setBLUR_RADIUS(25)
                        .setClickOutSideExit(true)
                        .setOVERLAY_COLOR(Color.argb(55, 255, 255, 255));

                SkipUtils.showDialog(MainActivity.this, new DimDialog(MainActivity.this), factory);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DimFactory factory = new DimFactory();
                factory.setDURATION(500)
                        .setBluringBg(bgLayout)
                        .setBLUR_RADIUS(20)
                        .setClickOutSideExit(true)
                        .setOVERLAY_COLOR(Color.argb(55, 255, 255, 255));

                SkipUtils.showDialog(MainActivity.this, new BgDimDialog(MainActivity.this), factory);
            }
        });

        startAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    isPlaying = false;
                } else {
                    isPlaying = true;
                }
                startAnim();
            }
        });

        startAnim();
    }


    public static void startAnim() {
        //建议如果底层view不会出现界面更改则关闭刷新视图。
        if (!isPlaying) {
            startAnim.setText("开始动画");
            return;
        } else {
            startAnim.setText("关闭动画");
        }
        ObjectAnimator tx = ObjectAnimator.ofFloat(imageView,
                View.TRANSLATION_X, (mRandom.nextFloat() - 0.5f) * ScreenUtils.getScreenWidth(mainActivity));
        tx.setDuration(500);
        tx.addUpdateListener(listener);
        tx.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startAnim();
            }
        });

        tx.start();
    }

    public static void setOnViewChangeListener(OnViewChangeListener onViewChangeListener) {
        MainActivity.onViewChangeListener = onViewChangeListener;
    }

    private static ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            if (onViewChangeListener != null) onViewChangeListener.onChange();
        }
    };
}

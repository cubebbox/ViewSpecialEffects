package com.luo.ViewSpecialEffects.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.luo.R;
import com.luo.ViewSpecialEffects.Interface.OnActivityChangeListener;
import com.luo.ViewSpecialEffects.data.D;
import com.luo.ViewSpecialEffects.factory.SlideFactory;
import com.luo.ViewSpecialEffects.utils.DensityUtil;
import com.luo.ViewSpecialEffects.utils.SkipUtils;
import com.luo.ViewSpecialEffects.widget.BlurringView;


public abstract class BaseSlideActivity extends Activity {
    protected GroupView slideGroupView = null;
    protected ImageView slideBgView = null;
    protected BlurringView slideBlurringView = null;
    protected View slideLayoutView = null;


    // =======object
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker = null;
    protected SlideFactory factory = null;
    //左阴影
    private Drawable shadowLeft = null;
    //右阴影
    private Drawable shadowRight = null;
    private OnActivityChangeListener onActivityChangeListener = null;
    // =======object

    // =====
    private int screenWidth = 0, screenHeight = 0;
    private int LOOSEN_MODE = 0;//松手模式
    private boolean IS_ME_EXIT = false;//是否我本界面调用的方法退出
    private boolean IS_FINISHING = false;//是否正在推出中
    private int shadow_width = 15;//单位dp
    // =====

    //===
    //===

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //首先获取要初始化的参数
        setFactory(SkipUtils.getSlideFactory());
        init();
        setContentView(setView());
        //统一子控件可父控件的左右拖动参数
        setCanDragLeft(factory.IS_DRAG_LEFT);
        setCanDragRight(factory.IS_DRAG_RIGHT);
    }

    private void init() {
        if (factory == null)
            factory = new SlideFactory();
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;// 防止int转换的误差，先记录宽高

        factory.setSLIDE_COE(DensityUtil.dip2px(this, 120));
        shadow_width = DensityUtil.dip2px(this, shadow_width);
        shadowLeft = getResources().getDrawable(R.drawable.shape_left);
        shadowRight = getResources().getDrawable(R.drawable.shape_right);


        mScroller = new Scroller(this);

    }


    /**
     * 设置布局
     */
    private View setView() {
        slideGroupView = new GroupView(this);//init object

        switch (factory.BG_MODE) {
            case D.BG_TRANSPARENT://bg view init
                slideBgView = new ImageView(this);
                slideBgView.setBackgroundColor(Color.BLACK);
                slideBgView.setAlpha(factory.ALPHA_MAX);
                break;
            case D.BG_BLURRY:
                //如果为模糊背景则加载模糊控件
                slideBlurringView = new BlurringView(this);
                if (factory.BluringBg != null) {
                    slideBlurringView.setBlurredView(factory.BluringBg);
                    slideBlurringView.setBlurRadius(factory.BLUR_RADIUS);
                    slideBlurringView.setOverlayColor(factory.OVERLAY_COLOR);
                }
                break;
        }

        //layout view init
        slideLayoutView = getLayoutInflater().inflate(getLayoutID(), null);

        RelativeLayout.LayoutParams allParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);// params init
        slideGroupView.setLayoutParams(allParams);
        slideLayoutView.setLayoutParams(allParams);
        if (slideBgView != null)
            slideBgView.setLayoutParams(allParams);
        if (slideBlurringView != null)
            slideBlurringView.setLayoutParams(allParams);


        if (slideBlurringView != null)
            slideGroupView.addView(slideBlurringView);
        if (slideBgView != null)
            slideGroupView.addView(slideBgView);

        slideGroupView.addView(slideLayoutView);

        return slideGroupView;
    }

    public abstract int getLayoutID();

    /**
     * 更新模糊视图
     */
    protected void updateBgBluringView() {
        slideBlurringView.invalidate();
    }

    @Override
    public void finish() {
        if (IS_ME_EXIT) {
            super.finish();
            overridePendingTransition(R.anim.keep, R.anim.keep);
        } else {
            if (IS_FINISHING) return;
            slideGroupView.exitActivity();
        }
    }

    public void setFactory(SlideFactory factory) {
        if (factory.BG_MODE == D.BG_BLURRY)
            factory.setALPHA_MAX(1f);
        this.factory = factory;
    }

    /**
     * 滚动开始
     */
    private void scrollStart() {
        if (onActivityChangeListener != null) onActivityChangeListener.scrollStart();
    }

    /**
     * 滚动中
     */
    private void scrolling(float currX, float finalX) {
        if (onActivityChangeListener != null) onActivityChangeListener.scrolling(currX, finalX);
    }

    /**
     * 滚动结束
     */
    private void scrollEnd(boolean isExit) {
        if (onActivityChangeListener != null) onActivityChangeListener.scrollEnd(isExit);
        if (isExit)
            finish();
    }

    @Override
    public void onBackPressed() {
        slideGroupView.exitActivity();
    }

    /**
     * 是否可以左滑动
     */
    protected void setCanDragLeft(boolean isable) {
        slideGroupView.IS_INTERCEPT_DRAG_LEFT = isable;
    }

    /**
     * 是否可以右滑动
     */
    protected void setCanDragRight(boolean isable) {
        slideGroupView.IS_INTERCEPT_DRAG_RIGHT = isable;
    }

    /**
     * 界面动态监听
     */
    public void setOnActivityChangeListener(OnActivityChangeListener onActivityChangeListener) {
        this.onActivityChangeListener = onActivityChangeListener;
    }

    /**
     * ------------------------------底层背景遮挡界面----------------------------------
     */
    class GroupView extends RelativeLayout {
        private ViewDragHelper mDragHelper;

        public GroupView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        public GroupView(Context context, AttributeSet attrs) {
            super(context, attrs, 0);
            init();
        }

        public GroupView(Context context) {
            super(context);
            init();
        }

        private void init() {
            /**
             * @params ViewGroup forParent 必须是一个ViewGroup
             * @params float sensitivity 灵敏度
             * @params Callback cb 回调
             */
            mDragHelper = ViewDragHelper.create(this, 2.0f, new ViewDragCallback());
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

        }

        @Override
        protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
            switch (factory.MOVE_MODE) {
                case D.MOVE_LEVEL:
                    //右阴影
                    shadowRight.setBounds(slideLayoutView.getWidth() + disX, child.getTop(), slideLayoutView.getWidth() + shadow_width + disX
                            , child.getBottom());
                    shadowRight.draw(canvas);

                    //左阴影
                    shadowLeft.setBounds(disX - shadow_width, child.getTop(), disX
                            , child.getBottom());
                    shadowLeft.draw(canvas);
                    break;
                case D.MOVE_ROTATION:
                    break;
            }


            boolean flag = super.drawChild(canvas, child, drawingTime);


            return flag;
        }

        // ================================数据
        private int disX = 0;
        private int lastX = 0;//last(up or loosen) x
        private int DRAGING_DIRECTION = 0;//1.left 2.right
        // ================================数据

        private class ViewDragCallback extends ViewDragHelper.Callback {
            /**
             * 尝试捕获子view，一定要返回true
             *
             * @param View child 尝试捕获的view
             * @param int  pointerId 指示器id？ 这里可以决定哪个子view可以拖动
             */
            @Override
            public boolean tryCaptureView(View view, int pointerId) {
                if (mScroller.computeScrollOffset() && mScroller.isFinished() == false)
                    return false;
                return slideLayoutView == view;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top,
                                              int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return 0;
            }

            /**
             * 处理水平方向上的拖动
             *
             * @param View child 被拖动到view
             * @param int  left 移动到达的x轴的距离
             * @param int  dx 建议的移动的x距离
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (!DIRECTION_JUDGED) return 0;
                invalidate();
                if (left < 0 && factory.IS_DRAG_LEFT) {//允许左拖动
                    disX = left;
                    switch (factory.MOVE_MODE) {
                        case D.MOVE_LEVEL:
                            switch (factory.BG_MODE) {//根据背景模式的不同，选择不同的透明度值
                                case D.BG_TRANSPARENT:
                                    slideBgView.setAlpha((1f - ((float) Math.abs(left) / (float) screenWidth)) * factory.ALPHA_MAX);
                                    break;
                                case D.BG_BLURRY:
                                    slideBlurringView.setAlpha((1f - ((float) Math.abs(left) / (float) screenWidth)) * factory.ALPHA_MAX * 1.5f);
                                    break;
                            }
                            break;
                        case D.MOVE_ROTATION:
                            switch (factory.BG_MODE) {//根据背景模式的不同，选择不同的透明度值
                                case D.BG_TRANSPARENT:
                                    slideBgView.setAlpha((1f - ((float) Math.abs(left) / (float) screenWidth)) * factory.ALPHA_MAX);
                                    break;
                                case D.BG_BLURRY:
                                    slideBlurringView.setAlpha((1f - ((float) Math.abs(left) / (float) screenWidth)) * factory.ALPHA_MAX * 1.5f);
                                    break;
                            }
                            slideLayoutView.setRotation((float) left / (float) screenWidth * factory.ANGLE_MAX);
                            break;
                    }
                    return left;
                }
                if (left >= 0 && factory.IS_DRAG_RIGHT) {//允许右拖动
                    disX = left;
                    switch (factory.MOVE_MODE) {
                        case D.MOVE_LEVEL:
                            switch (factory.BG_MODE) {//根据背景模式的不同，选择不同的透明度值
                                case D.BG_TRANSPARENT:
                                    slideBgView.setAlpha((1f - ((float) Math.abs(left) / (float) screenWidth)) * factory.ALPHA_MAX);
                                    break;
                                case D.BG_BLURRY:
                                    slideBlurringView.setAlpha((1f - ((float) Math.abs(left) / (float) screenWidth)) * factory.ALPHA_MAX * 1.5f);
                                    break;
                            }
                            break;
                        case D.MOVE_ROTATION:
                            switch (factory.BG_MODE) {//根据背景模式的不同，选择不同的透明度值
                                case D.BG_TRANSPARENT:
                                    slideBgView.setAlpha((1f - ((float) Math.abs(left) / (float) screenWidth)) * factory.ALPHA_MAX);
                                    break;
                                case D.BG_BLURRY:
                                    slideBlurringView.setAlpha((1f - ((float) Math.abs(left) / (float) screenWidth)) * factory.ALPHA_MAX * 1.5f);
                                    break;
                            }
                            slideLayoutView.setRotation((float) Math.abs(left) / (float) screenWidth * factory.ANGLE_MAX);
                            break;
                    }
                    return left;
                }
                return 0;

            }

            /**
             * 处理竖直方向上的拖动
             *
             * @param View child 被拖动到view
             * @param int  top 移动到达的y轴的距离
             * @param int  dy 建议的移动的y距离
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                return top;
                return 0;
            }

            /**
             * 当拖拽到状态改变时回调
             *
             * @params 新的状态
             */
            @Override
            public void onViewDragStateChanged(int state) {
                switch (state) {
                    case ViewDragHelper.STATE_DRAGGING: // 正在被拖动
                        if (mScroller.computeScrollOffset()
                                && mScroller.isFinished() == false) {
                            mScroller.abortAnimation();
                        }
                        disX = 0;
                        break;
                    case ViewDragHelper.STATE_IDLE: // view没有被拖拽或者 正在进行fling/snap
                        scrollStart();
                        lastX = disX;
                        if (disX > 0) {
                            DRAGING_DIRECTION = 2;//right
                        } else {
                            DRAGING_DIRECTION = 1;//left
                        }
                        if (getTouchVelocityX() > factory.VELOCITY_COE && disX != 0) {//超过滑动手速速率
                            LOOSEN_MODE = D.LOOSEN_EXIT;//松开之后的模式退出
                            switch (factory.MOVE_MODE) {
                                case D.MOVE_LEVEL:
                                    mScroller.startScroll(0, 0, screenWidth - Math.abs(disX), 0, factory.duration);
                                    break;
                                case D.MOVE_ROTATION:
                                    mScroller.startScroll(0, 0, screenWidth - Math.abs(disX) + (screenWidth / 2), 0, factory.duration);
                                    break;
                            }
                        } else {
                            if (Math.abs(disX) < factory.SLIDE_COE) {//是否超过临界点
                                LOOSEN_MODE = D.LOOSEN_SPRINGBACK;//松开之后的模式回弹
                                switch (factory.MOVE_MODE) {
                                    case D.MOVE_LEVEL:
                                        mScroller.startScroll(0, 0, disX, 0, factory.duration);
                                        break;
                                    case D.MOVE_ROTATION:
                                        mScroller.startScroll(0, 0, disX, 0, factory.duration);
                                        break;
                                }
                            } else {
                                LOOSEN_MODE = D.LOOSEN_EXIT;//松开之后的模式退出
                                switch (factory.MOVE_MODE) {
                                    case D.MOVE_LEVEL:
                                        mScroller.startScroll(0, 0, screenWidth - Math.abs(disX), 0, factory.duration);
                                        break;
                                    case D.MOVE_ROTATION:
                                        mScroller.startScroll(0, 0, screenWidth - Math.abs(disX) + (screenWidth / 2), 0, factory.duration);
                                        break;
                                }

                            }
                        }
                        postInvalidate();
                        break;
                    case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置
                        break;
                }
                super.onViewDragStateChanged(state);
            }
        }

        private float downX = 0, downY = 0, dX = 0, dY = 0;
        private boolean DIRECTION_JUDGED = false, IS_OCCUPY = false, IS_INTERCEPT_DRAG_LEFT = false, IS_INTERCEPT_DRAG_RIGHT = false;//方向判断标记，是否占用touch，是否允许子控件左拖动，是否允许子控件右拖动

        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            float x = event.getX(), y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    DIRECTION_JUDGED = false;
                    IS_OCCUPY = false;
                    downX = x;
                    downY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    dX = x - downX;
                    dY = y - downY;
                    if (!DIRECTION_JUDGED) {
                        //达到判断条件
                        if (Math.abs(dX) >= 30 || Math.abs(dY) >= 30) {
                            DIRECTION_JUDGED = true;
                            if (Math.abs(dX) > Math.abs(dY)) { //横向滑动
                                if (dX >= 0) {
                                    if (IS_INTERCEPT_DRAG_RIGHT) {
                                        IS_OCCUPY = true;
                                    } else {
                                        IS_OCCUPY = false;
                                    }
                                }
                                if (dX < 0) {
                                    if (IS_INTERCEPT_DRAG_LEFT) {
                                        IS_OCCUPY = true;
                                    } else {
                                        IS_OCCUPY = false;
                                    }
                                }
                                return IS_OCCUPY;
                            } else {
                                IS_OCCUPY = false;
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return IS_OCCUPY;
                    }
                case MotionEvent.ACTION_UP:
                    dX = 0;
                    dY = 0;
                    mDragHelper.cancel();
                    return false;
                case MotionEvent.ACTION_CANCEL:
                    mDragHelper.cancel();
                    return false;
            }
            /**
             * 处理拦截到的事件 这个方法会在返回前分发事件
             */
            mDragHelper.processTouchEvent(event);
            addVelocityTrackerEvent(event);
            return false;
            /**
             * 检查是否可以拦截touch事件 如果onInterceptTouchEvent可以return true 则这里return true
             */
//            mDragHelper.shouldInterceptTouchEvent(event);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            /**
             * 处理拦截到的事件 这个方法会在返回前分发事件
             */
            if (IS_FINISHING) return true;
            DIRECTION_JUDGED = true;
            mDragHelper.processTouchEvent(event);
            addVelocityTrackerEvent(event);
            return true;
        }

        /**
         * 添加手速计算时间
         */
        private void addVelocityTrackerEvent(MotionEvent event) {
            if (mVelocityTracker == null) {
                mVelocityTracker = VelocityTracker.obtain();
            }
            mVelocityTracker.addMovement(event);
        }

        /**
         * 获得横向的手速
         */
        private int getTouchVelocityX() {
            if (mVelocityTracker == null)
                return 0;
            mVelocityTracker.computeCurrentVelocity(1000);
            int velocity = (int) mVelocityTracker.getXVelocity();
            return Math.abs(velocity);
        }


        // 是否允许滑动缓存
        private void setScrollCacheEnable(boolean enabled) {
            final int size = getChildCount();
            for (int i = 0; i < size; ++i) {
                final View child = getChildAt(i);
                if (child.getVisibility() != GONE) {
                    child.setDrawingCacheEnabled(enabled);
                }
            }
        }


        @Override
        public void computeScroll() {
            if (mScroller.computeScrollOffset() == true
                    && mScroller.isFinished() == false) {
                float cx = mScroller.getCurrX(), cy = mScroller.getCurrY();// 现在的x和y
                float fx = mScroller.getFinalX(), fy = mScroller.getFinalY();// 最终的x和y
                scrolling(cx, fx);
                disX = (int) slideLayoutView.getX();

                switch (factory.BG_MODE) {//根据背景模式的不同，选择不同的透明度值
                    case D.BG_TRANSPARENT:
                        slideBgView.setAlpha((1f - (((float) Math.abs(disX) + cx) / (float) screenWidth)) * factory.ALPHA_MAX);
                        break;
                    case D.BG_BLURRY:
                        slideBlurringView.setAlpha((1f - (((float) Math.abs(disX) + cx) / (float) screenWidth)) * factory.ALPHA_MAX * 1.5f);
                        break;
                }
                //松手之后的模式退出还是回弹
                switch (LOOSEN_MODE) {
                    case D.LOOSEN_EXIT://退出
                        //拖动模式
                        switch (DRAGING_DIRECTION) {
                            case 1://left
                                slideLayoutView.layout((int) (lastX - cx), 0, slideLayoutView.getWidth() + (int) (lastX - cx), slideLayoutView.getHeight());
                                if (factory.MOVE_MODE == D.MOVE_ROTATION) {
                                    slideLayoutView.setRotation(((float) lastX - cx) / (float) slideLayoutView.getWidth() * factory.ANGLE_MAX);
                                }
//                                disX = (int) (lastX - cx);
                                break;
                            case 2://right
                                slideLayoutView.layout((int) (lastX + cx), 0, slideLayoutView.getWidth() + (int) (lastX + cx), slideLayoutView.getHeight());
                                if (factory.MOVE_MODE == D.MOVE_ROTATION) {
                                    slideLayoutView.setRotation(((float) Math.abs(lastX) + cx) / (float) slideLayoutView.getWidth() * factory.ANGLE_MAX);
                                }
//                                disX = (int) (lastX + cx);
                                break;
                        }

                        break;
                    case D.LOOSEN_SPRINGBACK://回弹
                        slideLayoutView.layout((int) (fx - cx), 0, slideLayoutView.getWidth() + (int) (fx - cx), slideLayoutView.getHeight());
                        if (factory.MOVE_MODE == D.MOVE_ROTATION)//if have rotation
                        {
                            switch (DRAGING_DIRECTION) {
                                case 1://left
                                    slideLayoutView.setRotation(((float) lastX - cx) / (float) slideLayoutView.getWidth() * factory.ANGLE_MAX);
                                    break;
                                case 2://right
                                    slideLayoutView.setRotation(((float) Math.abs(lastX) - cx) / (float) slideLayoutView.getWidth() * factory.ANGLE_MAX);
                                    break;
                            }
                        }
                        disX = (int) (fx - cx);
                        break;
                }
                if (cx == fx) {
                    mScroller.abortAnimation();
                    switch (LOOSEN_MODE) {
                        case D.LOOSEN_EXIT:
                            IS_ME_EXIT = true;
                            scrollEnd(true);
                            break;
                        case D.LOOSEN_SPRINGBACK:
                            scrollEnd(false);
                            break;
                    }

                    disX = 0;
                    LOOSEN_MODE = 0;
                    DRAGING_DIRECTION = 0;

                }
                invalidate();
                postInvalidate();// 激活视图
            }
        }

        /**
         * exit this activity
         */
        public void exitActivity() {
            //simulate drag right and exit
            LOOSEN_MODE = D.LOOSEN_EXIT;//松开之后的模式
            DRAGING_DIRECTION = 2;//right
            IS_ME_EXIT = true;
            IS_FINISHING = true;
            switch (factory.MOVE_MODE) {
                case D.MOVE_LEVEL:
                    mScroller.startScroll(0, 0, screenWidth, 0, factory.duration);
                    break;
                case D.MOVE_ROTATION:
                    mScroller.startScroll(0, 0, screenWidth + screenWidth / 2, 0, factory.duration);
                    break;
            }
            postInvalidate();
        }
    }

}

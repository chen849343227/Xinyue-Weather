package com.chen.xinyueweather.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.chen.xinyueweather.utils.DisplayUtils;

/**
 * author long
 * date 17-10-11
 * desc  ViewPager滑动指示器
 */

public class IndicatorView extends View {

    private int mRadius;   //小球半径
    private int mBallDistance;  //各个小球之间的间距
    private int mBallDistanceView;   //小球左右边界距离View左右边界的距离
    private int mBallCount = 1;   //保存圆球数量
    private int mCurrentBallCount = mBallCount; //当前圆球数量
    private int currentBall = 0;    //当前第几个球是白色的球(选中的是第几个球)
    private float mViewAlpha = 1.0F; //控件透明度   [0,1]
    private float positionOffset = 0.0F;//小球偏移量
    private boolean isAutoHide = true;   //是否自动隐藏(默认自动隐藏)

    private Paint mSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);   //选中小球画笔
    private Paint mUnSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  //未选中小球画笔

    private ValueAnimator mHideViewAnimator;  //隐藏指示器动画
    private ValueAnimator mShowViewAnimator;  //显示指示器动画

    public IndicatorView(Context context) {
        super(context);
        init(context);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 进行一些初始化的操作
     *
     * @param context
     */
    private void init(Context context) {
        //设置小球的半径
        mRadius = DisplayUtils.dp2px(context, 2.0F);  //这里将dp转化为对应的px
        mBallDistance = (mRadius * 4);
        mBallDistanceView = (mRadius * 2);
        //设置画笔
        mUnSelectedPaint.setColor(Color.GRAY);
        mUnSelectedPaint.setAlpha(100);
        mSelectedPaint.setColor(Color.WHITE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //这里需要说明的是： 小球距离View左右边界的距离是小球距离View上下边界的两倍
        //宽
        //mBallDistance * (mBallCount - 1)   多个小球之间的间距
        //mRadius * 2 * mBallCount   小球直径占用的宽度
        //mBallDistanceView * 2   小球距离View左右边界的距离(左右边界)
        //高
        //mRadius * 2 + mBallDistanceView  小球直径加上小球距离View左右边界的距离
        setMeasuredDimension(mBallDistance * (mBallCount - 1) + mRadius * 2 * mBallCount + mBallDistanceView * 2, mRadius * 2 + mBallDistanceView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int distance = mBallDistance;  //小球之间的间距
        int distanceFromCircleCenter = mRadius * 2 + distance;  //两个小球圆心之间的距离
        int count = 0;
        //画灰色的小球(表示未选中)
        while (count < mCurrentBallCount) {
            //mBallDistanceView + mRadius + count * distanceFromCircleCenter
            //第一个小球圆心x就是小球半径加上小球左边界到View左边界的距离，也就是 mBallDistanceView + mRadius
            //第二个小球圆心x就是   第一个小球半径加上小球左边界到View左边界的距离再加上两个小球圆心的距离(distanceFromCircleCenter)，也就是 mBallDistanceView + mRadius + distanceFromCircleCenter
            //以此类推
            canvas.drawCircle(mBallDistanceView + mRadius + count * distanceFromCircleCenter, getHeight() / 2, mRadius, mUnSelectedPaint);
            count += 1;
        }
        //第一个小球的偏移量
        float oneBallOffset = mBallDistanceView + mRadius + currentBall * distanceFromCircleCenter;
        float offset = positionOffset;
        //画白色的小球(表示选中的小球)
        canvas.drawCircle(oneBallOffset + distanceFromCircleCenter * offset, getHeight() / 2, mRadius, mSelectedPaint);
    }

    /**
     * 设置变透明的时间
     * 该方法是设置小球数量之后,触发该View自动隐藏动画
     * @param time 时间
     */
    private void setAlphaTime(Long time) {
        //设置
        postDelayed(() -> startAutoHideAnimation(0), time);
    }

    /**
     * 设置圆球的数量
     *
     * @param count 圆球数量
     */
    public void setCount(int count) {
        mBallCount = count;
        if (mCurrentBallCount != mBallCount) {
            requestLayout();
            mCurrentBallCount = mBallCount;
        }
        mViewAlpha = 1.0F;
        if (isAutoHide) {
            setAlphaTime(0L);
        }
    }

    /**
     * 开始自动隐藏的动画
     *
     * @param state 滑动的状态
     */
    private void startAutoHideAnimation(int state) {
        //状态为1表示开始滑动
        if (state == 1) {
            if ((mHideViewAnimator != null) && (mHideViewAnimator.isStarted())) {
                mHideViewAnimator.cancel();
            }
            mShowViewAnimator = ValueAnimator.ofFloat(new float[]{mViewAlpha, 1.0F});
            mShowViewAnimator.setDuration((long) (500.0F * (1.0F - mViewAlpha)));
            mShowViewAnimator.addUpdateListener(animation -> {
                mViewAlpha = (Float) animation.getAnimatedValue();
                setAlpha(mViewAlpha);
            });
            mShowViewAnimator.start();
        }
        //状态为0表示滑动完成
        while (state != 0) {
            return;
        }
        if ((mShowViewAnimator != null) && (mShowViewAnimator.isStarted())) {
            mShowViewAnimator.cancel();
        }
        mHideViewAnimator = ValueAnimator.ofFloat(new float[]{1.0F, 0.0F});
        mHideViewAnimator.setDuration(2000L);
        mHideViewAnimator.addUpdateListener(animation -> {
            mViewAlpha = (Float) animation.getAnimatedValue();
            setAlpha(mViewAlpha);
        });
        mHideViewAnimator.start();
    }

    /**
     * 设置ViewPager滑动监听
     *
     * @param mViewPager ViewPager控件
     */
    public void addOnPageChangeListener(ViewPager mViewPager) {
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    /**
     * 设置ViewPager滑动监听
     * @param mViewPager ViewPager控件
     * @param isAutoHide 是否自动隐藏该控件
     */
    public void addOnPageChangeListener(ViewPager mViewPager, boolean isAutoHide) {
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        this.isAutoHide = isAutoHide;
    }

    /**
     * ViewPage页面滑动监听
     */
    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentBall = position;
            //更新小球的移动距离
            IndicatorView.this.positionOffset = positionOffset;
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (isAutoHide) {
                startAutoHideAnimation(state);
            }
        }
    };
}

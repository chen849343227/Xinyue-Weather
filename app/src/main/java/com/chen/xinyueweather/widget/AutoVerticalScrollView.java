package com.chen.xinyueweather.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.chen.xinyueweather.R;
import com.orhanobut.logger.Logger;

import java.util.List;



public class AutoVerticalScrollView extends AppCompatTextView implements View.OnClickListener {

    private final static int DEFAULT_SWITCH_DURATION = 500; //默认切换时间
    private final static int DEFAULT_INTERVAL_DURATION = 2000; //间隔时间
    private Context mContext;
    private List<String> mTextList; //要显示的文字

    private int contentSize;
    private String curSlideOutStr; //当前滑出的文本
    private String curSlideInStr;  //当前滑进的文本
    private float textBaseY;
    private int currentIndex;  //当前文本的索引

    private int mSwitchDuration = DEFAULT_SWITCH_DURATION;
    private int mIntervalDuration = DEFAULT_INTERVAL_DURATION;
    private int mSwitchOrientation = 0;

    private float currentAnimatedValue = 0.0f;
    private ValueAnimator mAnimator;

    private int verticalOffset = 0;
    private int mWidth;
    private int mHeight;
    private int paddingLeft = 0;
    private int paddingBottom = 0;
    private int paddingTop = 0;

    private Paint mPaint;


    public AutoVerticalScrollView(Context context) {
        this(context, null);
    }

    public AutoVerticalScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoVerticalScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AutoVerticalScrollView);
        try {
            mSwitchDuration = array.getInt(R.styleable.AutoVerticalScrollView_switch_duration, DEFAULT_SWITCH_DURATION);
            mIntervalDuration = array.getInt(R.styleable.AutoVerticalScrollView_interval_duration, DEFAULT_INTERVAL_DURATION);
            mSwitchOrientation = array.getInt(R.styleable.AutoVerticalScrollView_switch_orientation, 0);
        } finally {
            array.recycle();
        }
        init();
    }

    private void init() {
        mPaint = new Paint();
       mPaint.setColor(getCurrentTextColor());
        setOnClickListener(this);

        mAnimator = ObjectAnimator.ofFloat(0.0f, 1.0f)
                .setDuration(mSwitchDuration);
        mAnimator.setStartDelay(mIntervalDuration); //间隔时间
        mAnimator.addUpdateListener(valueAnimator -> {
            currentAnimatedValue = (float) valueAnimator.getAnimatedValue();
            if (currentAnimatedValue < 1.0f) {
                invalidate();
            }
        });

        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentIndex = (++currentIndex) % contentSize;
                if (callbackListener != null) {
                    callbackListener.showNext(currentIndex);
                }
                curSlideOutStr = mTextList.get(currentIndex);
                curSlideInStr = mTextList.get((currentIndex + 1) % contentSize);
                mAnimator.setStartDelay(mIntervalDuration);
                mAnimator.start();
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);

        @SuppressLint("DrawAllocation")
        Rect bounds = new Rect();
        if (contentSize <= 0) {
            return;
        }
        String text = mTextList.get(0);
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        int textHeight = bounds.height();
        Logger.d("onMeasure height is " + mHeight);

        paddingLeft = getPaddingLeft();
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();
        mHeight = textHeight + paddingBottom + paddingTop;

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //计算文字高度
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        //计算文字的baseline
        textBaseY = mHeight - (mHeight - fontHeight) / 2 - fontMetrics.bottom;

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (contentSize < 0) {
            return;
        }

        //直接使用mHeight控制文本绘制，会因为text的baseline的问题不能居中显示
        verticalOffset = Math.round(2 * textBaseY * (0.5f - currentAnimatedValue));
        Log.d("viclee", "verticalOffset is " + verticalOffset);
        if (mSwitchOrientation == 0) {//向上滚动切换
            if (verticalOffset > 0) {
                canvas.drawText(curSlideOutStr, paddingLeft, verticalOffset, mPaint);
            } else {
                canvas.drawText(curSlideInStr, paddingLeft, 2 * textBaseY + verticalOffset, mPaint);
            }
        } else {
            if (verticalOffset > 0) {//向下滚动切换
                canvas.drawText(curSlideOutStr, paddingLeft, 2 * textBaseY - verticalOffset, mPaint);
            } else {
                canvas.drawText(curSlideInStr, paddingLeft, -verticalOffset, mPaint);
            }
        }
    }

    /**
     * 设置需要循环显示的文本
     *
     * @param content 数据源
     */
    public void setTextContent(List<String> content) {
        if (content == null || content.size() == 0) {
            return;
        }
        mTextList = content;
        contentSize = mTextList.size();
        curSlideOutStr = mTextList.get(0);
        if (contentSize > 1) {
            curSlideInStr = mTextList.get(1);
        } else {
            curSlideInStr = mTextList.get(0);
        }
        if (contentSize > 1) {
            mAnimator.start();
        }
    }

    public void setTextContent(String content) {
        if (content != null || !content.equals("")) {
            return;
        }
        mTextList.add(0, content);
        contentSize = mTextList.size();
        curSlideOutStr = mTextList.get(0);
        if (contentSize > 1) {
            curSlideInStr = mTextList.get(1);
        } else {
            curSlideInStr = mTextList.get(0);
        }
        if (contentSize > 1) {
            mAnimator.start();
        }
    }

    @Override
    public void onClick(View view) {
        if (callbackListener != null) {
            callbackListener.onClick(currentIndex);
        }
    }

    private CallbackListener callbackListener;

    public interface CallbackListener {
        void showNext(int index);

        void onClick(int index);
    }

    public void setOnCallbackListener(CallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }
}

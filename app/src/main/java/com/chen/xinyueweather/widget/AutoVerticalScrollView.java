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
import com.chen.xinyueweather.utils.DisplayUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


public class AutoVerticalScrollView extends AppCompatTextView implements View.OnClickListener {

    private static final String TAG = "AutoVerticalScrollView";

    //默认切换时间
    private final static int DEFAULT_SWITCH_DURATION = 500;
    //间隔时间
    private final static int DEFAULT_INTERVAL_DURATION = 2000;
    //默认文字大小
    private final static int DEFAULT_TEXT_SIZE = 12;
    //默认宽度
    private final static int DEFAULT_VIEW_WIDTH = 200;
    //默认高度
    private final static int DEFAULT_VIEW_HIGH = 200;

    private Context mContext;
    //文字集合
    private List<String> mTextList;
    //列表的数量
    private int mTextListSize;
    //文字大小
    private float mTextSize;

    private String mCurSlideOutStr; //当前滑出的文本
    private String mCurSlideInStr;  //当前滑进的文本
    private float textBaseY;
    private int mCurrentIndex = 0;  //当前文本的索引

    private int mSwitchDuration = DEFAULT_SWITCH_DURATION;
    private int mIntervalDuration = DEFAULT_INTERVAL_DURATION;
    private int mSwitchOrientation = 0;

    private ValueAnimator mAnimator;
    private float currentAnimatedValue = 0.0f;
    private boolean isShowAnimation = false;

    private int verticalOffset = 0;
    private int mHeight;

    private int paddingTop = 0;
    private int paddingBottom = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;

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
            mTextSize = array.getDimension(R.styleable.AutoVerticalScrollView_text_size, DisplayUtils.sp2px(context, DEFAULT_TEXT_SIZE));
        } finally {
            array.recycle();
        }
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(getCurrentTextColor());
        mPaint.setTextSize(mTextSize);
        setOnClickListener(this);
        mAnimator = ObjectAnimator.ofFloat(0.0f, 1.0f).setDuration(mSwitchDuration);
        startAnimation();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        @SuppressLint("DrawAllocation")
        Rect bounds = new Rect();
        if (mTextListSize <= 0) {
            return;
        }
        String text = mTextList.get(mCurrentIndex);
        Log.e(TAG, text);
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        int textHeight = bounds.height();

        paddingLeft = getPaddingLeft();
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();

        mHeight = textHeight + paddingBottom + paddingTop;
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

        //计算文字宽度
        float fontWidth = mPaint.measureText(text);
        //计算文字的baseline
        textBaseY = mHeight / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            widthSpecSize = (int) (fontWidth + paddingLeft + paddingRight);
            heightSpecSize = mHeight;
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            widthSpecSize = (int) (fontWidth + paddingLeft + paddingRight);
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            heightSpecSize = mHeight;
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }

        // setMeasuredDimension((int) (getPaint().measureText(text) + paddingLeft + paddingRight), mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTextListSize < 0) {
            return;
        }

    /*    paddingLeft = getPaddingLeft();
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();

        float fontWidth = mPaint.measureText(mTextList.get(0));

        float x = (getWidth() - fontWidth) / 2;
        //文字的y轴坐标
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        textBaseY = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;*/

        //直接使用mHeight控制文本绘制，会因为text的baseline的问题不能居中显示
        verticalOffset = Math.round(2 * textBaseY * (0.5f - currentAnimatedValue));
        //Logger.e("mTextListSize = " + mTextListSize);
        if (mTextListSize <= 1) {
            if (mCurSlideOutStr != null) {
                canvas.drawText(mCurSlideOutStr, getWidth() / 2, verticalOffset, mPaint);
            }
        } else {
            if (mSwitchOrientation == 0) {//向上滚动切换
                if (verticalOffset > 0) {
                    canvas.drawText(mCurSlideOutStr, getWidth() / 2, verticalOffset, mPaint);
                } else {
                    canvas.drawText(mCurSlideInStr, getWidth() / 2, 2 * textBaseY + verticalOffset, mPaint);
                }
            } else {
                if (verticalOffset > 0) {//向下滚动切换
                    canvas.drawText(mCurSlideOutStr, getWidth() / 2, 2 * textBaseY - verticalOffset, mPaint);
                } else {
                    canvas.drawText(mCurSlideInStr, getWidth() / 2, -verticalOffset, mPaint);
                }
            }
        }
    }

    /**
     * 设置需要循环显示的文本
     *
     * @param content 数据源
     */
    public void setTextContent(List<String> content) {
        Logger.e("content size = " + content.size());
        if (content == null || content.size() == 0) {
            return;
        }
        mTextList = content;
        mTextListSize = mTextList.size();
        mCurSlideOutStr = mTextList.get(0);
        if (mTextListSize > 1) {
            mCurSlideInStr = mTextList.get(1);
        } else {
            mCurSlideInStr = mTextList.get(0);
        }
        if (mTextListSize > 1) {
            mAnimator.start();
        }
    }

    public void setTextContent(String content) {
        if (content == null || content.equals("")) {
            return;
        }
        mTextList = new ArrayList<>();
        mTextList.add(0, content);
        mTextListSize = mTextList.size();
        mCurSlideOutStr = mTextList.get(0);
        if (mTextListSize > 1) {
            mCurSlideInStr = mTextList.get(1);
        } else {
            mCurSlideInStr = mTextList.get(0);
        }
        if (mTextListSize > 1) {
            mAnimator.start();
            isShowAnimation = true;
        }
    }

    @Override
    public void onClick(View view) {
        if (isShowAnimation) {
            return;
        }
        if (callbackListener != null) {
            callbackListener.onClick(mCurrentIndex);
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

    /**
     * 开始动画
     */
    private void startAnimation() {
        isShowAnimation = true;
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
                isShowAnimation = false;
                mCurrentIndex = (++mCurrentIndex) % mTextListSize;
                if (callbackListener != null) {
                    callbackListener.showNext(mCurrentIndex);
                }
                mCurSlideOutStr = mTextList.get(mCurrentIndex);
                mCurSlideInStr = mTextList.get((mCurrentIndex + 1) % mTextListSize);
                mAnimator.setStartDelay(mIntervalDuration);
                mAnimator.start();
            }
        });
    }
}

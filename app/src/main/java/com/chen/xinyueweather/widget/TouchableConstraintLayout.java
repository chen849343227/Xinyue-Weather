package com.chen.xinyueweather.widget;


import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TouchableConstraintLayout extends ConstraintLayout {

    public TouchableConstraintLayout(Context context) {
        this(context, null);
    }

    public TouchableConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchableConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != MotionEvent.ACTION_DOWN) {
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(getScaleX(), 1.0F);
                valueAnimator.addUpdateListener(animation -> {

                });
                valueAnimator.start();
            }
        } else {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(getScaleX(), 0.96F);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(animation -> {

            });
            valueAnimator.start();
        }
        return super.onTouchEvent(motionEvent);
    }
}

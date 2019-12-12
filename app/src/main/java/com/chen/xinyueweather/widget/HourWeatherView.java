package com.chen.xinyueweather.widget;

import java.util.List;

import com.chen.xinyueweather.utils.DisplayUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;

public class HourWeatherView extends View {
    private static final String TAG = "HourWeatherView";

    public HourWeatherView(Context context) {
        this(context, null);
    }

    public HourWeatherView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HourWeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float j = DisplayUtils.applyDimension(context, 30.0f);
    }

    // public void setHourDataList(List<Hour>) {
    //
    // }

}

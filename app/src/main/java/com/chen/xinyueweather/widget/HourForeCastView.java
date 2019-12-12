package com.chen.xinyueweather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.chen.xinyueweather.dao.bean.Weather3HourDetailInfo;
import com.chen.xinyueweather.utils.DateTimeUtil;
import com.chen.xinyueweather.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;



public class HourForeCastView extends View {

    public HourForeCastView(Context context) {
        super(context);
        this.context = context;
    }

    public HourForeCastView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public HourForeCastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = ScreenUtil.getScreenWidth(context);
        height = width / 2 - getFitSize(20);
        widthAvg = getFitSize(200);
        radius = getFitSize(8);
        setMeasuredDimension((int) leftRight + (int) widthAvg * 7, (int) height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hourForeCasts.size() == 0) {
            return;
        }
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0);
        paint.setTextSize(ScreenUtil.getSp(context, 13));
        paint.setTextAlign(Paint.Align.CENTER);

        float weatherDetallPadding = getFitSize(50);
        float weatherTimePadding = getFitSize(100);
        float linePaddingBottom = getFitSize(200);
        float lineHigh = getFitSize(180);

        float lineAvg = lineHigh / getMaxMinDelta();

        float paddingLeft = 0;

        // 解决path过大无法绘制，分成三段
        Path tempPath = new Path();
        Path tempPath2 = new Path();
        Path tempPath3 = new Path();

        int i = 1;
        for (Weather3HourDetailInfo foreCast : hourForeCasts) {
            paddingLeft = leftRight / 2 + (i - 1 + 0.5f) * widthAvg;
            if (i == 1) {
                tempPath.moveTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getHighestTemperature()) - tempL) * lineAvg));
            } else if (i > 1 && i <= 10) {
                tempPath.lineTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getHighestTemperature()) - tempL) * lineAvg));
                tempPath2.moveTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getHighestTemperature()) - tempL) * lineAvg));
            } else if (i > 10 && i <= 20) {
                tempPath2.lineTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getHighestTemperature()) - tempL) * lineAvg));
                tempPath3.moveTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getHighestTemperature()) - tempL) * lineAvg));
            } else {
                tempPath3.lineTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getHighestTemperature()) - tempL) * lineAvg));
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(getFitSize(2));
            canvas.drawCircle(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getHighestTemperature()) - tempL) * lineAvg), radius, paint);
            paint.setStrokeWidth(0);
            paint.setStyle(Paint.Style.STROKE);

            canvas.drawText(Integer.parseInt(foreCast.getHighestTemperature()) + "°", paddingLeft, height - (getFitSize(20) + linePaddingBottom + (Integer.parseInt(foreCast.getHighestTemperature()) - tempL) * lineAvg), paint);

            //文字
            canvas.drawText(foreCast.getWeather(), paddingLeft, height - weatherDetallPadding, paint);
            canvas.drawText(DateTimeUtil.getHourByDate(foreCast.getStartTime()), paddingLeft, height - weatherTimePadding, paint);
            //这里除掉最后一条数据
            if (i == 7) {
                break;
            }
            i++;
        }
        paint.setStrokeWidth(getFitSize(3));
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(tempPath, paint);
        canvas.drawPath(tempPath2, paint);
        canvas.drawPath(tempPath3, paint);
    }


    private int getMaxMinDelta() {
        if (hourForeCasts.size() > 0) {
            tempL = Integer.parseInt(hourForeCasts.get(0).getHighestTemperature());
            tempH = Integer.parseInt(hourForeCasts.get(0).getHighestTemperature());
            for (Weather3HourDetailInfo hourForeCast : hourForeCasts) {
                if (Integer.parseInt(hourForeCast.getHighestTemperature()) > tempH) {
                    tempH = Integer.parseInt(hourForeCast.getHighestTemperature());
                }
                if (Integer.parseInt(hourForeCast.getHighestTemperature()) < tempL) {
                    tempL = Integer.parseInt(hourForeCast.getHighestTemperature());
                }
            }
            return tempH - tempL;
        }
        return 0;
    }

    public void setHourForeCasts(List<Weather3HourDetailInfo> hourForeCasts) {
        this.hourForeCasts.clear();
        this.hourForeCasts.addAll(hourForeCasts);
        this.invalidate();
    }

    private float getFitSize(float orgSize) {
        return orgSize * width * 1.0f / 1080;
    }


    private final static String TAG = "HourForeCastView";
    Paint paint = new Paint();
    float widthAvg;
    private float height, width;
    private List<Weather3HourDetailInfo> hourForeCasts = new ArrayList<>();
    private int tempH, tempL;
    private Context context;
    private float radius = 0;
    private float leftRight = 0;
}
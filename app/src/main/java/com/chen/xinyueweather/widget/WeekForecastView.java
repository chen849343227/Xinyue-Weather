package com.chen.xinyueweather.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.chen.xinyueweather.dao.bean.Weather;
import com.chen.xinyueweather.utils.DateTimeUtil;
import com.chen.xinyueweather.utils.ScreenUtil;
import com.chen.xinyueweather.utils.WeatherIconUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @author along
 * @date Created:17-11-9
 * @Description
 */
public class WeekForecastView extends View {

    public WeekForecastView(Context context) {
        super(context);
        this.context = context;
    }

    public WeekForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public WeekForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = ScreenUtil.getScreenWidth(context);
        height = width - getFitSize(20);
        leftRight = getFitSize(30);
        radius = getFitSize(8);
        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (foreCasts.size() == 0) {
            return;
        }
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(ScreenUtil.getSp(context, 13));

        drawWeatherDetail(canvas);

    }

    @SuppressLint("SimpleDateFormat")
    private void drawWeatherDetail(Canvas canvas) {

        float weekPaddingBottom = getFitSize(200);
        float weekInfoPaddingBottom = getFitSize(40);
        float linePaddingBottom = getFitSize(330);
        float tempPaddingTop = getFitSize(20);
        float tempPaddingBottom = getFitSize(45);

        //获取每个天气所占空间
        float lineHigh = getFitSize(320);
        float widthAvg = (width - leftRight) / foreCasts.size();
        float heightAvg = lineHigh / maxMinDelta;

        Matrix matrix = new Matrix();
        matrix.postScale(0.45f, 0.45f); //长和宽放大缩小的比例

        Path pathTempHigh = new Path();
        Path pathTempLow = new Path();

        float paddingLeft = 0;
        int i = 1;
        List<PointF> mPointHs = new ArrayList<>();
        List<PointF> mPointLs = new ArrayList<>();
        for (Weather foreCast : foreCasts) {
            paddingLeft = leftRight / 2 + (i - 1 + 0.5f) * widthAvg;
            if (type == TYPE_LINE) {
                if (i == 1) {
                    pathTempHigh.moveTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getTemp_day_c()) - tempL) * heightAvg));
                    pathTempLow.moveTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getTemp_night_c()) - tempL) * heightAvg));
                } else {
                    pathTempHigh.lineTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getTemp_day_c()) - tempL) * heightAvg));
                    pathTempLow.lineTo(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getTemp_night_c()) - tempL) * heightAvg));
                }
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(getFitSize(2));
                canvas.drawCircle(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getTemp_day_c()) - tempL) * heightAvg), radius, paint);
                canvas.drawCircle(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getTemp_night_c()) * heightAvg)), radius, paint);
            } else {
                PointF pointFH = new PointF(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getTemp_day_c()) - tempL) * heightAvg));
                mPointHs.add(pointFH);
                PointF pointFL = new PointF(paddingLeft, height - (linePaddingBottom + (Integer.parseInt(foreCast.getTemp_night_c()) - tempL) * heightAvg));
                mPointLs.add(pointFL);
            }

            paint.setStrokeWidth(0);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawText(Integer.parseInt(foreCast.getTemp_day_c()) + "°", paddingLeft, height - (linePaddingBottom + tempPaddingTop + (Integer.parseInt(foreCast.getTemp_day_c()) - tempL) * heightAvg), paint);
            canvas.drawText(Integer.parseInt(foreCast.getTemp_night_c()) + "°", paddingLeft, height - (linePaddingBottom - tempPaddingBottom + (Integer.parseInt(foreCast.getTemp_night_c()) - tempL) * heightAvg), paint);

            //星期
            try {
                canvas.drawText(DateTimeUtil.getWeekOfDate(new SimpleDateFormat("yyyy-MM-dd").parse(foreCast.getDate())), paddingLeft, height - (weekPaddingBottom), paint);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //天气图标
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), WeatherIconUtil.getWeatherIconID(foreCast.getWeather()));
            Bitmap bitmapDisplay = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            canvas.drawBitmap(bitmapDisplay,
                    paddingLeft - bitmapDisplay.getWidth() / 2, height - getFitSize(8) - ((weekPaddingBottom - weekInfoPaddingBottom) / 2 + weekInfoPaddingBottom) - bitmapDisplay.getHeight() / 2, paint);
            bitmap.recycle();
            bitmapDisplay.recycle();
            //天气描述
            canvas.drawText(foreCast.getWeather(), paddingLeft, height - (weekInfoPaddingBottom), paint);
            i++;
        }
        paint.setStrokeWidth(getFitSize(3));
        paint.setStyle(Paint.Style.STROKE);

        if (type == TYPE_LINE) {
            canvas.drawPath(pathTempHigh, paint);
            canvas.drawPath(pathTempLow, paint);
        } else {
            drawBezier(canvas, mPointHs, pathTempHigh);
            drawBezier(canvas, mPointLs, pathTempLow);
        }
    }

    private void drawBezier(Canvas canvas, List<PointF> pointFs, Path path) {
        path.reset();
        for (int i = 0; i < pointFs.size(); i++) {
            PointF pointCur = pointFs.get(i);
            PointF pointPre = null;
            if (i > 0) {
                pointPre = pointFs.get(i - 1);
            }
            if (i == 0) {
                path.moveTo(pointCur.x, pointCur.y);
            } else {
                path.quadTo(pointPre.x, pointPre.y, (pointPre.x + pointCur.x) / 2, (pointPre.y + pointCur.y) / 2);
            }
        }
        path.lineTo(pointFs.get(pointFs.size() - 1).x, pointFs.get(pointFs.size() - 1).y);

        canvas.drawPath(path, paint);
    }


    private int getMaxMinDelta() {
        if (foreCasts.size() > 0) {
            tempH = Integer.parseInt(foreCasts.get(0).getTemp_day_c());
            tempL = Integer.parseInt(foreCasts.get(0).getTemp_night_c());
            for (Weather weekForeCast : foreCasts) {
                if (Integer.parseInt(foreCasts.get(0).getTemp_day_c()) > tempH) {
                    tempH = Integer.parseInt(weekForeCast.getTemp_day_c());
                }
                if (Integer.parseInt(weekForeCast.getTemp_night_c()) < tempL) {
                    tempL = Integer.parseInt(weekForeCast.getTemp_night_c());
                }
            }
            return tempH - tempL;
        }
        return 0;
    }


    public void setForeCasts(List<Weather> foreCasts) {
        this.foreCasts.clear();
        this.foreCasts.addAll(foreCasts);
        maxMinDelta = getMaxMinDelta();
        this.invalidate();
    }

    private float getFitSize(float orgSize) {
        return orgSize * width * 1.0f / 1080;
    }


    private final static String TAG = "ForeCastView";
    private float height, width;
    private Paint paint = new Paint();
    private Context context;
    private List<Weather> foreCasts = new ArrayList<>();
    private float maxMinDelta;
    private int tempH, tempL;
    private float radius = 0;
    private float leftRight;

    private static final int TYPE_LINE = 0;
    private static final int TYPE_BESSEL = 1;
    private int type = TYPE_BESSEL;
}

package com.chen.xinyueweather.module.home;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.R;
import com.chen.xinyueweather.dao.bean.Alarm;
import com.chen.xinyueweather.dao.bean.Aqi;
import com.chen.xinyueweather.dao.bean.BaseWeatherBean;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.dao.bean.IndexesBean;
import com.chen.xinyueweather.dao.bean.RealWeather;
import com.chen.xinyueweather.injector.components.DaggerContentComponent;
import com.chen.xinyueweather.injector.modules.ContentModule;
import com.chen.xinyueweather.module.base.BaseFragment;
import com.chen.xinyueweather.utils.Constant;
import com.chen.xinyueweather.utils.ScreenUtil;
import com.chen.xinyueweather.utils.ToastUtils;
import com.chen.xinyueweather.widget.AqiView;
import com.chen.xinyueweather.widget.AutoVerticalScrollView;
import com.chen.xinyueweather.widget.HourForeCastView;
import com.chen.xinyueweather.widget.SunRiseView;
import com.chen.xinyueweather.widget.WeekForecastView;
import com.chen.xinyueweather.widget.WindmillView;
import com.chen.xinyueweather.widget.weather.SkyView;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * @author along
 * @date Created:17-11-7
 * @Description
 */
public class ContentFragment extends BaseFragment<IContentPresenter> implements IContentView, View.OnTouchListener {
    private static final String TAG = "ContentFragment";

    @BindView(R.id.tv_real_type)
    TextView mTvRealType;
    @BindView(R.id.tv_updateTime)
    TextView mTvUpdateTime;
    AutoVerticalScrollView mTvRTTemp;
//    @BindView(R.id.tv_RTTemp)
//    AutoVerticalScrollView mTvRTTemp;
    @BindView(R.id.tv_degree)
    TextView mTvDegree;
    @BindView(R.id.tv_aqi)
    TextView mTvAqi;
    @BindView(R.id.weekForecast)
    WeekForecastView mWeekForecast;
    @BindView(R.id.first_show_rl)
    RelativeLayout mFirstShowRl;
    @BindView(R.id.hourForecast)
    HourForeCastView mHourForecast;
    @BindView(R.id.windViewBig)
    WindmillView mWindViewBig;
    @BindView(R.id.windViewSmall)
    WindmillView mWindViewSmall;
    @BindView(R.id.tv_windDire)
    TextView mTvWindDire;
    @BindView(R.id.tv_windSpeed)
    TextView mTvWindSpeed;
    @BindView(R.id.pb_humidity)
    ProgressBar mPbHumidity;
    @BindView(R.id.tv_humidity)
    TextView mTvHumidity;
    @BindView(R.id.view_aqi)
    AqiView mViewAqi;
    @BindView(R.id.tv_aqiQuality)
    TextView mTvAqiQuality;
    @BindView(R.id.tv_pm25)
    TextView mTvPm25;
    @BindView(R.id.tv_pm10)
    TextView mTvPm10;
    @BindView(R.id.tv_so2)
    TextView mTvSo2;
    @BindView(R.id.tv_no2)
    TextView mTvNo2;
    @BindView(R.id.ll_aqi)
    LinearLayout mLlAqi;
    @BindView(R.id.view_sun)
    SunRiseView mViewSun;
    @BindView(R.id.ll_livingIndex)
    LinearLayout mLlLivingIndex;
    @BindView(R.id.content_main)
    ScrollView mContentMain;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;


    @BindView(R.id.livingIndex_0)
    View livingIndex_0;
    @BindView(R.id.livingIndex_1)
    View livingIndex_1;
    @BindView(R.id.livingIndex_2)
    View livingIndex_2;
    @BindView(R.id.livingIndex_3)
    View livingIndex_3;
    @BindView(R.id.livingIndex_4)
    View livingIndex_4;
    @BindView(R.id.livingIndex_5)
    View livingIndex_5;

    private View[] mViews;
    private ImageView mIvIcon;
    private TextView mTvNameAndValue;
    private TextView mTvDetails;
    private SkyView mBackgroundView;

    private String mCityName;
    private String mWeather;
    private String mTemperature;
    private String weatherId;

    /**
     * @param weatherId
     * @return
     */
    public static ContentFragment newInstance(String weatherId) {
        ContentFragment mFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("weather", weatherId);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            weatherId = getArguments().getString("weather");
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initInjector() {
        DaggerContentComponent.builder()
                .applicationComponent(getAppComponent())
                .contentModule(new ContentModule(ContentFragment.this, weatherId))
                .build()
                .inject(ContentFragment.this);
    }

    @Override
    protected void initViews() {
         mTvRTTemp = (AutoVerticalScrollView) getActivity().findViewById(R.id.tv_RTTemp);
        /*初始化下拉刷新颜色*/
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        mRefresh.setColorSchemeColors(typedValue.data);
        mRefresh.setRefreshing(true);
        mContentMain.setOnTouchListener(this);
        mFirstShowRl.setVisibility(View.INVISIBLE);
        mViews = new View[]{livingIndex_0, livingIndex_1, livingIndex_2, livingIndex_3, livingIndex_4, livingIndex_5};
        TypedArray actionbarSizeTypedArray = getActivity().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int h = (int) actionbarSizeTypedArray.getDimension(0, 0);
        mFirstShowRl.getLayoutParams().height = ScreenUtil.getScreenHeight(getActivity()) - h - ScreenUtil.getStatusBarHeight(getActivity());
        // mLvLivingIndex.setAdapter(mZhiShuAdapter);

        new Thread(() -> {
            while (true) {
                try {
                    mWindViewBig.refreshView();
                    mWindViewSmall.refreshView();
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(false);
        mRefresh.setOnRefreshListener(() -> mPresenter.getData(true));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void loadWeather(BaseWeatherBean weathersBean) {
        for (int i = 0; i < AndroidApplication.mCityManages.size(); i++) {
            if (AndroidApplication.mCityManages.get(i).getWeatherId().equals(weatherId)) {
                CityManage cityManage = new CityManage();
                mCityName = weathersBean.getCity();
                mWeather = weathersBean.getRealtime().getWeather();
                mTemperature = weathersBean.getRealtime().getTemp() + "°";
                cityManage.setAreaName(mCityName);
                cityManage.setTemperature(mTemperature);
                cityManage.setWeather(mWeather);
                cityManage.setWeatherId(weatherId);
                mPresenter.insert(cityManage);
                AndroidApplication.mCityManages.get(i).setAreaName(mCityName);
                AndroidApplication.mCityManages.get(i).setWeather(mWeather);
                AndroidApplication.mCityManages.get(i).setTemperature(mTemperature);
                break;
            }
        }
        //     mBackgroundView.setWeather(mWeather);
        //设置实时天气
        RealWeather realWeather = weathersBean.getRealtime();
        //Alarm
        List<?> alarm = weathersBean.getAlarms();
        if (alarm.size() > 0) {
            List<String> alarmName = new ArrayList<>();
            for (int i = 0; i < alarm.size(); i++) {
                alarmName.add(i, ((Alarm) alarm.get(i)).getAlarmTypeDesc());
            }
            //  mTvRTTemp
            mTvRTTemp.setTextContent(alarmName);
            mTvRTTemp.setOnCallbackListener(new AutoVerticalScrollView.CallbackListener() {
                @Override
                public void showNext(int index) {
                    Toast.makeText(mContext, alarmName.get(index), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClick(int index) {
                    Toast.makeText(mContext, alarmName.get(index), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //aqi
        Aqi aqi = weathersBean.getPm25();

        mTvRealType.setText(realWeather.getWeather());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        mTvUpdateTime.setText(String.format(getResources().getString(R.string.activity_home_refresh_time), simpleDateFormat.format(new Date(System.currentTimeMillis()))));
        mTvRTTemp.setTextContent(realWeather.getTemp());
        mTvDegree.setText("°");
        mTvAqi.setText(aqi.getQuality() + " " + aqi.getAqi());

        //周报&&时报
        mWeekForecast.setForeCasts(weathersBean.getWeathers());
        mHourForecast.setHourForeCasts(weathersBean.getWeatherDetailsInfo().getWeather3HoursDetailsInfos());
        //windForecastView.setForeCasts(weatherInfo.getWeekForeCasts());

        //风速湿度
        mWindViewBig.setWindSpeedDegree(Integer.parseInt(realWeather.getWS().replace("级", "")));
        mWindViewSmall.setWindSpeedDegree(Integer.parseInt(realWeather.getWS().replace("级", "")));

        mTvWindDire.setText(realWeather.getWD());
        mTvWindSpeed.setText(realWeather.getWS());
        mPbHumidity.setProgress(Integer.parseInt(realWeather.getSD()));
        mTvHumidity.setText(realWeather.getSD());
        //空气
        mViewAqi.setProgressAndLabel(Integer.parseInt(aqi.getAqi()), "空气" + aqi.getQuality());
        mTvPm25.setText(aqi.getPm25() + " μg/m³");
        mTvPm10.setText(aqi.getPm10() + " μg/m³");
        mTvSo2.setText(aqi.getSo2() + " μg/m³");
        mTvNo2.setText(aqi.getNo2() + " μg/m³");
        //日出
        mViewSun.setSunRiseDownTime(weathersBean.getWeathers().get(0).getSun_rise_time(), weathersBean.getWeathers().get(0).getSun_down_time());
        //指数
        for (int i = 0; i < weathersBean.getIndexes().size(); i++) {
            IndexesBean bean = weathersBean.getIndexes().get(i);
            mIvIcon = (ImageView) mViews[i].findViewById(R.id.iv_icon);
            mIvIcon.setImageResource(Constant.ZHISHU.get(bean.getName()));
            mTvNameAndValue = (TextView) mViews[i].findViewById(R.id.tv_nameAndValue);
            mTvNameAndValue.setText(bean.getName() + " " + bean.getLevel());
            mTvDetails = (TextView) mViews[i].findViewById(R.id.tv_details);
            mTvDetails.setText(bean.getContent());
        }
        mContentMain.smoothScrollTo(0, 0);
    }

    @Override
    public void setRefresh(boolean isRefresh) {
        mRefresh.setRefreshing(isRefresh);
        mFirstShowRl.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNetError() {
        mRefresh.setRefreshing(false);
        ToastUtils.showToast("天气信息获取失败");
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mRefresh.isRefreshing();
    }
}

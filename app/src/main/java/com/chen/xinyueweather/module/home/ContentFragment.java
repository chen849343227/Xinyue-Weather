package com.chen.xinyueweather.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chen.xinyueweather.R;
import com.chen.xinyueweather.module.base.BaseFragment;
import com.chen.xinyueweather.widget.AqiView;
import com.chen.xinyueweather.widget.HourForecastView;
import com.chen.xinyueweather.widget.MyListView;
import com.chen.xinyueweather.widget.SunRiseView;
import com.chen.xinyueweather.widget.WeekForecastView;
import com.chen.xinyueweather.widget.WindmillView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author along
 * @date Created:17-11-7
 * @Description
 */
public class ContentFragment extends BaseFragment {

    @BindView(R.id.tv_real_type)
    TextView mTvRealType;
    @BindView(R.id.tv_updateTime)
    TextView mTvUpdateTime;
    @BindView(R.id.tv_RTTemp)
    TextView mTvRTTemp;
    @BindView(R.id.tv_degree)
    TextView mTvDegree;
    @BindView(R.id.tv_aqi)
    TextView mTvAqi;
    @BindView(R.id.weekForecast)
    WeekForecastView mWeekForecast;
    @BindView(R.id.first_show_rl)
    RelativeLayout mFirstShowRl;
    @BindView(R.id.hourForecast)
    HourForecastView mHourForecast;
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
    @BindView(R.id.lv_livingIndex)
    MyListView mLvLivingIndex;
    @BindView(R.id.ll_livingIndex)
    LinearLayout mLlLivingIndex;
    @BindView(R.id.content_main)
    ScrollView mContentMain;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

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

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

}

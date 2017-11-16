package com.chen.xinyueweather.module.home;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.R;
import com.chen.xinyueweather.dao.bean.Aqi;
import com.chen.xinyueweather.dao.bean.BaseWeatherBean;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.dao.bean.IndexesBean;
import com.chen.xinyueweather.dao.bean.RealWeather;
import com.chen.xinyueweather.dao.bean.WeathersBean;
import com.chen.xinyueweather.injector.components.DaggerContentComponent;
import com.chen.xinyueweather.injector.modules.ContentModule;
import com.chen.xinyueweather.module.base.BaseFragment;
import com.chen.xinyueweather.utils.Constant;
import com.chen.xinyueweather.utils.ScreenUtil;
import com.chen.xinyueweather.utils.ToastUtils;
import com.chen.xinyueweather.widget.AqiView;
import com.chen.xinyueweather.widget.HourForeCastView;
import com.chen.xinyueweather.widget.MyListView;
import com.chen.xinyueweather.widget.SunRiseView;
import com.chen.xinyueweather.widget.WeekForecastView;
import com.chen.xinyueweather.widget.WindmillView;
import com.chen.xinyueweather.widget.weather.SkyView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author along
 * @date Created:17-11-7
 * @Description
 */
public class ContentFragment extends BaseFragment<IContentPresenter> implements IContentView {

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

    View[] mViews;
    ImageView mIvIcon;
    TextView mTvNameAndValue;
    TextView mTvDetails;


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
        /**初始化下拉刷新颜色*/
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        mRefresh.setColorSchemeColors(typedValue.data);
        mRefresh.setRefreshing(true);
        mFirstShowRl.setVisibility(View.INVISIBLE);
        mViews = new View[]{livingIndex_0, livingIndex_1, livingIndex_2, livingIndex_3, livingIndex_4, livingIndex_5};
        TypedArray actionbarSizeTypedArray = getActivity().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int h = (int) actionbarSizeTypedArray.getDimension(0, 0);
        mFirstShowRl.getLayoutParams().height = ScreenUtil.getScreenHeight(getActivity()) - h - ScreenUtil.getStatusBarHeight(getActivity());
        // mLvLivingIndex.setAdapter(mZhiShuAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mWindViewBig.refreshView();
                    mWindViewSmall.refreshView();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData(true);
            }
        });
    }

    @Override
    public void loadWeather(BaseWeatherBean weathersBean) {
        for (int i = 0; i < AndroidApplication.mCityManages.size(); i++) {
            if (AndroidApplication.mCityManages.get(i).getWeatherId().equals(weatherId)) {
                AndroidApplication.mCityManages.get(i).setAreaName(weathersBean.getCity());
                AndroidApplication.mCityManages.get(i).setWeather(weathersBean.getRealtime().getWeather());
                AndroidApplication.mCityManages.get(i).setTemperature(weathersBean.getRealtime().getTemp() + "°");
            }
        }
        //设置实时天气
        RealWeather realWeather = weathersBean.getRealtime();
        //aqi
        Aqi aqi = weathersBean.getPm25();

        mTvRealType.setText(realWeather.getWeather());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        mTvUpdateTime.setText(String.format(getResources().getString(R.string.activity_home_refresh_time), simpleDateFormat.format(new Date(System.currentTimeMillis()))));
        mTvRTTemp.setText(realWeather.getTemp());
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
        ToastUtils.showToast("天气信息获取失败");
    }


}

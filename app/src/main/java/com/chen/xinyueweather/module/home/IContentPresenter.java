package com.chen.xinyueweather.module.home;

import com.chen.xinyueweather.dao.bean.Alarm;
import com.chen.xinyueweather.dao.bean.Aqi;
import com.chen.xinyueweather.dao.bean.IndexesBean;
import com.chen.xinyueweather.dao.bean.RealWeather;
import com.chen.xinyueweather.dao.bean.Weather3HoursDetailsInfosBean;
import com.chen.xinyueweather.dao.bean.WeatherDetailsInfoBean;
import com.chen.xinyueweather.dao.bean.WeathersBean;
import com.chen.xinyueweather.module.base.IBasePresenter;
import com.chen.xinyueweather.module.base.ILocalPresenter;

import java.util.List;

/**
 * author long
 * date 17-11-10
 * desc
 */

public interface IContentPresenter<T> extends ILocalPresenter<T> {


    /**
     * 天气预警
     */

    void insertNewAlarm(List<?> alarms);

    List<Alarm> queryAlarmsByAreaId(String areaId);

    void deleteAlarmsByAreaId(String areaId);

    /**
     * 实时天气
     */

    void insertRealWeather(RealWeather realWeather);

    RealWeather queryRealWeatherByAreaId(String areaId);

    void deleteRealWeatherByAreaId(String areaId);

    /**
     * 一周预报
     */

    void insertWeekForecast(List<WeathersBean> bean);

    List<WeathersBean> queryWeekForecastByAreaId(String areaId);

    void deleteWeekForecastByAreaId(String areaId);


    /**
     * 三小时预报
     */
    void insertThreeHourForecast(WeatherDetailsInfoBean bean);

    List<Weather3HoursDetailsInfosBean> queryThreeHourForecastByAreaId(String areaId);

    void deleteThreeHourForecastByAreaId(String areaId);

    /**
     * 空气质量
     */

    void insertAqi(Aqi aqi);

    Aqi queryAqiByAreaId(String areaId);

    void deleteAqiByAreaId(String areaId);

    /**
     * 生活指数
     */

    void insertZhishu(List<IndexesBean> bean);

    List<IndexesBean> queryZhishuByAreaId(String areaId);

    void deleteZhishuByAreaId(String areaId);


}

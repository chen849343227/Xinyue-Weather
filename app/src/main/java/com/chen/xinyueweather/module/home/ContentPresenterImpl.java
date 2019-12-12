package com.chen.xinyueweather.module.home;


import android.util.Log;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.api.RetrofitService;
import com.chen.xinyueweather.dao.bean.Alarm;
import com.chen.xinyueweather.dao.bean.Aqi;
import com.chen.xinyueweather.dao.bean.BaseResponse;
import com.chen.xinyueweather.dao.bean.BaseWeather;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.dao.bean.Index;
import com.chen.xinyueweather.dao.bean.RealWeather;
import com.chen.xinyueweather.dao.bean.Weather;
import com.chen.xinyueweather.dao.bean.Weather3HourDetailInfo;
import com.chen.xinyueweather.dao.bean.WeatherDetailInfo;
import com.chen.xinyueweather.dao.greendao.AlarmDao;
import com.chen.xinyueweather.dao.greendao.AqiDao;
import com.chen.xinyueweather.dao.greendao.DaoSession;
import com.chen.xinyueweather.dao.greendao.IndexDao;
import com.chen.xinyueweather.dao.greendao.RealWeatherDao;
import com.chen.xinyueweather.dao.greendao.Weather3HourDetailInfoDao;
import com.chen.xinyueweather.dao.greendao.WeatherDao;
import com.chen.xinyueweather.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author along
 * @date Created:17-11-14
 * @Description
 */
public class ContentPresenterImpl implements IContentPresenter<CityManage> {

    private final IContentView mView;

    private final DaoSession mDao;

    private String mWeatherId;

    public ContentPresenterImpl(IContentView view, DaoSession dao, String weatherId) {
        mView = view;
        mDao = dao;
        mWeatherId = weatherId;
    }

    @Override
    public void getData(boolean isRefresh) {
        // mWeatherId = "101200801";
        if (isRefresh) {
            getDataFromNet();
        } else {
            getDataFromLocal();
        }
    }

    @Override
    public void update(List list) {

    }

    @Override
    public void insert(CityManage data) {
        Logger.e(data.toString());
        mDao.getCityManageDao().update(data);
    }

    @Override
    public void delete(CityManage data) {

    }


    private void getDataFromNet() {
        ToastUtils.showToast("Get Data From NetWork");
        RetrofitService.getWeatherFromNet(mWeatherId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Func1<BaseResponse, Observable<BaseWeather>>) baseResponse -> {
                    if ("200".equals(baseResponse.getCode())) {
                        return Observable.just((BaseWeather) baseResponse.getValue().get(0));
                    } else {
                        return Observable.error(new Exception("天气信息获取失败"));
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(baseWeather -> {
                    Logger.e(Thread.currentThread().getName());
                    //天气预警
                    insertNewAlarm(baseWeather.getAlarms());
                    insertRealWeather(baseWeather.getRealtime());
                    insertWeekForecast(baseWeather.getWeathers());
                    insertThreeHourForecast(baseWeather.getWeatherDetailsInfo());
                    insertAqi(baseWeather.getPm25());
                    insertZhishu(baseWeather.getIndexes());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.bindToLife())
                .subscribe(new Observer<BaseWeather>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        mView.setRefresh(false);
                        mView.showNetError();
                        ToastUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseWeather baseWeather) {
                        mView.loadWeather(baseWeather);
                        mView.setRefresh(false);
                    }
                });
    }

    private void getDataFromLocal() {
        ToastUtils.showToast("Get Data From Database");
        BaseWeather baseWeather = new BaseWeather();
        //数据库里面没有存储城市名称,所以要从全局变量里面拿城市名称（by匹配天气ID）
        for (CityManage cityManage : AndroidApplication.mCityManages) {
            if (cityManage.getWeatherId().equals(mWeatherId)) {
                baseWeather.setCity(cityManage.getAreaName());
            }
        }
        //如果数据库里面没有数据，就从网络去获取
        if (queryAqiByAreaId(mWeatherId) == null) {
            getDataFromNet();
            return;
        }
        Log.d("long", "queryAlarmsByAreaId size " + String.valueOf(queryAlarmsByAreaId(mWeatherId).size()));
        baseWeather.setAlarms(queryAlarmsByAreaId(mWeatherId));
        baseWeather.setPm25(queryAqiByAreaId(mWeatherId));
        baseWeather.setRealtime(queryRealWeatherByAreaId(mWeatherId));
        baseWeather.setWeathers(queryWeekForecastByAreaId(mWeatherId));
        WeatherDetailInfo weatherDetailInfo = new WeatherDetailInfo();
        weatherDetailInfo.setWeather3HourDetailInfo(queryThreeHourForecastByAreaId(mWeatherId));
        baseWeather.setWeatherDetailsInfo(weatherDetailInfo);
        baseWeather.setIndexes(queryZhishuByAreaId(mWeatherId));
        mView.loadWeather(baseWeather);
        mView.setRefresh(false);
    }


    @Override
    public void insertNewAlarm(List<Alarm> alarms) {
        Log.e("long", alarms.size() + "alarms size");
        //deleteAlarmsByAreaId(mWeatherId);
        if (alarms.size() > 0) {
            mDao.getAlarmDao().deleteAll();
            Alarm alarm;
            for (int i = 0; i < alarms.size(); i++) {
                alarm = alarms.get(i);
                alarm.setAreaId(mWeatherId);
                mDao.getAlarmDao().insert(alarm);
            }
        }
    }

    @Override
    public List<Alarm> queryAlarmsByAreaId(String areaId) {
        return mDao.getAlarmDao().queryBuilder().where(AlarmDao.Properties.AreaId.eq(areaId)).list();
    }

    @Override
    public void deleteAlarmsByAreaId(String areaId) {
        List<Alarm> alarmList = queryAlarmsByAreaId(areaId);
        if (alarmList.size() > 0) {
            Alarm alarm;
            for (int i = 0; i < alarmList.size(); i++) {
                alarm = alarmList.get(i);
                mDao.getAlarmDao().delete(alarm);
            }
        }
    }

    @Override
    public void insertRealWeather(RealWeather realWeather) {
        if (queryRealWeatherByAreaId(mWeatherId) != null) {
            realWeather.setAreaId(mWeatherId);
            mDao.getRealWeatherDao().update(realWeather);
        } else {
            realWeather.setAreaId(mWeatherId);
            mDao.getRealWeatherDao().insert(realWeather);
        }
    }

    @Override
    public RealWeather queryRealWeatherByAreaId(String areaId) {
        return mDao.getRealWeatherDao().queryBuilder().where(RealWeatherDao.Properties.AreaId.eq(areaId)).unique();
    }

    @Override
    public void deleteRealWeatherByAreaId(String areaId) {

    }

    @Override
    public void insertWeekForecast(List<Weather> bean) {
        List<Weather> queryWeathersList = queryWeekForecastByAreaId(mWeatherId);
        //   Logger.e("queryWeathersList size: " + queryWeathersList.size());
        //   Logger.e("bean size: " + bean.size());
        if (queryWeathersList.size() > 0) {
            Weather weather;
            if (queryWeathersList.size() < bean.size()) {
                int index = 0;
                for (int i = 0; i < queryWeathersList.size(); i++) {
                    weather = bean.get(i);
                    weather.setAreaId(mWeatherId);
                    weather.setId(queryWeathersList.get(i).getId());
                    mDao.getWeatherDao().update(weather);
                    index = i;
                }
                // index == queryWeathersList.size()  sure
            /*    for (int i = index; i < bean.size(); i++) {
                    weather = bean.get(i);
                    weather.setAreaId(mWeatherId);
                    mDao.getWeatherDao().insert(weather);
                }*/
            } else {
                for (int i = 0; i < bean.size(); i++) {
                    weather = bean.get(i);
                    weather.setAreaId(mWeatherId);
                    weather.setId(queryWeathersList.get(i).getId());
                    mDao.getWeatherDao().update(weather);
                }
            }
        } else {
            for (Weather weather : bean) {
                weather.setAreaId(mWeatherId);
                mDao.getWeatherDao().insert(weather);
            }
        }
    }

    @Override
    public List<Weather> queryWeekForecastByAreaId(String areaId) {
        return mDao.getWeatherDao().queryBuilder().where(WeatherDao.Properties.AreaId.eq(areaId)).list();
    }

    @Override
    public void deleteWeekForecastByAreaId(String areaId) {

    }

    @Override
    public void insertThreeHourForecast(WeatherDetailInfo bean) {
        List<Weather3HourDetailInfo> weather3HourDetailInfoList = queryThreeHourForecastByAreaId(mWeatherId);
        if (weather3HourDetailInfoList.size() > 0) {
            Weather3HourDetailInfo weather3HourDetailInfo;
            for (int i = 0; i < bean.getWeather3HourDetailInfo().size(); i++) {
                weather3HourDetailInfo = bean.getWeather3HourDetailInfo().get(i);
                weather3HourDetailInfo.setAreaId(mWeatherId);
                weather3HourDetailInfo.setId(weather3HourDetailInfoList.get(i).getId());
                mDao.getWeather3HourDetailInfoDao().update(weather3HourDetailInfo);
            }
        } else {
            for (Weather3HourDetailInfo weather3HourDetailInfo : bean.getWeather3HourDetailInfo()) {
                weather3HourDetailInfo.setAreaId(mWeatherId);
                mDao.getWeather3HourDetailInfoDao().insert(weather3HourDetailInfo);
            }
        }
    }

    @Override
    public List<Weather3HourDetailInfo> queryThreeHourForecastByAreaId(String areaId) {
        return mDao.getWeather3HourDetailInfoDao().queryBuilder().where(Weather3HourDetailInfoDao.Properties.AreaId.eq(areaId)).list();
    }

    @Override
    public void deleteThreeHourForecastByAreaId(String areaId) {

    }

    @Override
    public void insertAqi(Aqi aqi) {
        if (queryAqiByAreaId(mWeatherId) != null) {
            aqi.setAreaId(mWeatherId);
            mDao.getAqiDao().update(aqi);
        } else {
            aqi.setAreaId(mWeatherId);
            mDao.getAqiDao().insert(aqi);
        }
    }

    @Override
    public Aqi queryAqiByAreaId(String areaId) {
        return mDao.getAqiDao().queryBuilder().where(AqiDao.Properties.AreaId.eq(areaId)).unique();
    }

    @Override
    public void deleteAqiByAreaId(String areaId) {

    }

    @Override
    public void insertZhishu(List<Index> bean) {
        List<Index> indexList = queryZhishuByAreaId(mWeatherId);
        if (indexList.size() > 0) {
            Index index;
            for (int i = 0; i < bean.size(); i++) {
                index = bean.get(i);
                index.setAreaId(mWeatherId);
                index.setId(indexList.get(i).getId());
                mDao.getIndexDao().update(index);
            }
        } else {
            for (Index index : bean) {
                index.setAreaId(mWeatherId);
                mDao.getIndexDao().insert(index);
            }
        }
    }


    @Override
    public List<Index> queryZhishuByAreaId(String areaId) {
        return mDao.getIndexDao().queryBuilder().where(IndexDao.Properties.AreaId.eq(mWeatherId)).list();
    }

    @Override
    public void deleteZhishuByAreaId(String areaId) {

    }


}
package com.chen.xinyueweather.module.home;


import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.api.RetrofitService;
import com.chen.xinyueweather.dao.bean.Alarm;
import com.chen.xinyueweather.dao.bean.Aqi;
import com.chen.xinyueweather.dao.bean.BaseResponse;
import com.chen.xinyueweather.dao.bean.BaseWeatherBean;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.dao.bean.IndexesBean;
import com.chen.xinyueweather.dao.bean.RealWeather;
import com.chen.xinyueweather.dao.bean.Weather3HoursDetailsInfosBean;
import com.chen.xinyueweather.dao.bean.WeatherDetailsInfoBean;
import com.chen.xinyueweather.dao.bean.WeathersBean;
import com.chen.xinyueweather.dao.greendao.AlarmDao;
import com.chen.xinyueweather.dao.greendao.AqiDao;
import com.chen.xinyueweather.dao.greendao.DaoSession;
import com.chen.xinyueweather.dao.greendao.IndexesBeanDao;
import com.chen.xinyueweather.dao.greendao.RealWeatherDao;
import com.chen.xinyueweather.dao.greendao.Weather3HoursDetailsInfosBeanDao;
import com.chen.xinyueweather.dao.greendao.WeathersBeanDao;
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
                .flatMap((Func1<BaseResponse, Observable<BaseWeatherBean>>) baseResponse -> {
                    if ("200".equals(baseResponse.getCode())) {
                        return Observable.just((BaseWeatherBean) baseResponse.getValue().get(0));
                    } else {
                        return Observable.error(new Exception("天气信息获取失败"));
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(baseWeatherBean -> {
                    Logger.e(Thread.currentThread().getName());
                    //天气预警
                    insertNewAlarm(baseWeatherBean.getAlarms());
                    insertRealWeather(baseWeatherBean.getRealtime());
                    insertWeekForecast(baseWeatherBean.getWeathers());
                    insertThreeHourForecast(baseWeatherBean.getWeatherDetailsInfo());
                    insertAqi(baseWeatherBean.getPm25());
                    insertZhishu(baseWeatherBean.getIndexes());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.bindToLife())
                .subscribe(new Observer<BaseWeatherBean>() {
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
                    public void onNext(BaseWeatherBean baseWeatherBean) {
                        mView.loadWeather(baseWeatherBean);
                        mView.setRefresh(false);
                    }
                });
    }

    private void getDataFromLocal() {
        ToastUtils.showToast("Get Data From Database");
        BaseWeatherBean baseWeatherBean = new BaseWeatherBean();
        //数据库里面没有存储城市名称,所以要从全局变量里面拿城市名称（by匹配天气ID）
        for (CityManage cityManage : AndroidApplication.mCityManages) {
            if (cityManage.getWeatherId().equals(mWeatherId)) {
                baseWeatherBean.setCity(cityManage.getAreaName());
            }
        }
        //如果数据库里面没有数据，就从网络去获取
        if (queryAqiByAreaId(mWeatherId) == null) {
            getDataFromNet();
            return;
        }
        baseWeatherBean.setPm25(queryAqiByAreaId(mWeatherId));
        baseWeatherBean.setRealtime(queryRealWeatherByAreaId(mWeatherId));
        baseWeatherBean.setWeathers(queryWeekForecastByAreaId(mWeatherId));
        WeatherDetailsInfoBean weatherDetailsInfoBean = new WeatherDetailsInfoBean();
        weatherDetailsInfoBean.setWeather3HoursDetailsInfos(queryThreeHourForecastByAreaId(mWeatherId));
        baseWeatherBean.setWeatherDetailsInfo(weatherDetailsInfoBean);
        baseWeatherBean.setIndexes(queryZhishuByAreaId(mWeatherId));
        mView.loadWeather(baseWeatherBean);
        mView.setRefresh(false);
    }


    @Override
    public void insertNewAlarm(List<?> alarms) {
        deleteAlarmsByAreaId(mWeatherId);
        if (alarms.size() > 0) {
            Alarm alarm;
            for (int i = 0; i < alarms.size(); i++) {
                alarm = (Alarm) alarms.get(i);
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
    public void insertWeekForecast(List<WeathersBean> bean) {
        List<WeathersBean> queryWeathersList = queryWeekForecastByAreaId(mWeatherId);
        //   Logger.e("queryWeathersList size: " + queryWeathersList.size());
        //   Logger.e("bean size: " + bean.size());
        if (queryWeathersList.size() > 0) {
            WeathersBean weathersBean;
            if (queryWeathersList.size() < bean.size()) {
                int index = 0;
                for (int i = 0; i < queryWeathersList.size(); i++) {
                    weathersBean = bean.get(i);
                    weathersBean.setAreaId(mWeatherId);
                    weathersBean.setId(queryWeathersList.get(i).getId());
                    mDao.getWeathersBeanDao().update(weathersBean);
                    index = i;
                }
                // index == queryWeathersList.size()  sure
            /*    for (int i = index; i < bean.size(); i++) {
                    weathersBean = bean.get(i);
                    weathersBean.setAreaId(mWeatherId);
                    mDao.getWeathersBeanDao().insert(weathersBean);
                }*/
            } else {
                for (int i = 0; i < bean.size(); i++) {
                    weathersBean = bean.get(i);
                    weathersBean.setAreaId(mWeatherId);
                    weathersBean.setId(queryWeathersList.get(i).getId());
                    mDao.getWeathersBeanDao().update(weathersBean);
                }
            }
        } else {
            for (WeathersBean weathersBean : bean) {
                weathersBean.setAreaId(mWeatherId);
                mDao.getWeathersBeanDao().insert(weathersBean);
            }
        }
    }

    @Override
    public List<WeathersBean> queryWeekForecastByAreaId(String areaId) {
        return mDao.getWeathersBeanDao().queryBuilder().where(WeathersBeanDao.Properties.AreaId.eq(areaId)).list();
    }

    @Override
    public void deleteWeekForecastByAreaId(String areaId) {

    }

    @Override
    public void insertThreeHourForecast(WeatherDetailsInfoBean bean) {
        List<Weather3HoursDetailsInfosBean> weather3HoursDetailsInfosBeanList = queryThreeHourForecastByAreaId(mWeatherId);
        if (weather3HoursDetailsInfosBeanList.size() > 0) {
            Weather3HoursDetailsInfosBean weather3HoursDetailsInfosBean;
            for (int i = 0; i < bean.getWeather3HoursDetailsInfos().size(); i++) {
                weather3HoursDetailsInfosBean = bean.getWeather3HoursDetailsInfos().get(i);
                weather3HoursDetailsInfosBean.setAreaId(mWeatherId);
                weather3HoursDetailsInfosBean.setId(weather3HoursDetailsInfosBeanList.get(i).getId());
                mDao.getWeather3HoursDetailsInfosBeanDao().update(weather3HoursDetailsInfosBean);
            }
        } else {
            for (Weather3HoursDetailsInfosBean weather3HoursDetailsInfosBean : bean.getWeather3HoursDetailsInfos()) {
                weather3HoursDetailsInfosBean.setAreaId(mWeatherId);
                mDao.getWeather3HoursDetailsInfosBeanDao().insert(weather3HoursDetailsInfosBean);
            }
        }
    }

    @Override
    public List<Weather3HoursDetailsInfosBean> queryThreeHourForecastByAreaId(String areaId) {
        return mDao.getWeather3HoursDetailsInfosBeanDao().queryBuilder().where(Weather3HoursDetailsInfosBeanDao.Properties.AreaId.eq(areaId)).list();
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
    public void insertZhishu(List<IndexesBean> bean) {
        List<IndexesBean> indexesBeanList = queryZhishuByAreaId(mWeatherId);
        if (indexesBeanList.size() > 0) {
            IndexesBean indexesBean;
            for (int i = 0; i < bean.size(); i++) {
                indexesBean = bean.get(i);
                indexesBean.setAreaId(mWeatherId);
                indexesBean.setId(indexesBeanList.get(i).getId());
                mDao.getIndexesBeanDao().update(indexesBean);
            }
        } else {
            for (IndexesBean indexesBean : bean) {
                indexesBean.setAreaId(mWeatherId);
                mDao.getIndexesBeanDao().insert(indexesBean);
            }
        }
    }


    @Override
    public List<IndexesBean> queryZhishuByAreaId(String areaId) {
        return mDao.getIndexesBeanDao().queryBuilder().where(IndexesBeanDao.Properties.AreaId.eq(mWeatherId)).list();
    }

    @Override
    public void deleteZhishuByAreaId(String areaId) {

    }


}
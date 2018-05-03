package com.chen.xinyueweather.module.home;


import com.chen.xinyueweather.api.RetrofitService;
import com.chen.xinyueweather.dao.bean.Aqi;
import com.chen.xinyueweather.dao.bean.BaseResponse;
import com.chen.xinyueweather.dao.bean.BaseWeatherBean;
import com.chen.xinyueweather.dao.bean.IndexesBean;
import com.chen.xinyueweather.dao.bean.RealWeather;
import com.chen.xinyueweather.dao.bean.Weather3HoursDetailsInfosBean;
import com.chen.xinyueweather.dao.bean.WeatherDetailsInfoBean;
import com.chen.xinyueweather.dao.bean.WeathersBean;
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
public class ContentPresenterImpl implements IContentPresenter {

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
    public void insert(Object data) {

    }

    @Override
    public void delete(Object data) {

    }

    @Override
    public void update(List list) {

    }


    private void getDataFromNet() {
        ToastUtils.showToast("网络获取数据");
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
                    //
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
        ToastUtils.showToast("数据库获取数据");
        BaseWeatherBean baseWeatherBean = new BaseWeatherBean();
        //   baseWeatherBean.setCityid();
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
    }

    @Override
    public void getAlarmByAreaId(String areaId) {

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
        if (queryWeekForecastByAreaId(mWeatherId) != null) {
            for (WeathersBean weathersBean : bean) {
                weathersBean.setAreaId(mWeatherId);
                mDao.getWeathersBeanDao().update(weathersBean);
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
        if (queryThreeHourForecastByAreaId(mWeatherId) != null) {
            for (Weather3HoursDetailsInfosBean weather3HoursDetailsInfosBean : bean.getWeather3HoursDetailsInfos()) {
                weather3HoursDetailsInfosBean.setAreaId(mWeatherId);
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
        if (queryZhishuByAreaId(mWeatherId) != null) {
            for (IndexesBean indexesBean : bean) {
                indexesBean.setAreaId(mWeatherId);
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
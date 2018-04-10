package com.chen.xinyueweather.module.home;


import com.chen.xinyueweather.AndroidApplication;
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
import com.chen.xinyueweather.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
            getDataFormNet(mWeatherId);
        } else {
            getDataFromLocal(mWeatherId);
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



    private void getDataFormNet(String weatherId) {
        RetrofitService.getWeatherFromNet(mWeatherId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<BaseResponse, Observable<BaseWeatherBean>>() {
                    @Override
                    public Observable<BaseWeatherBean> call(BaseResponse baseResponse) {
                        Logger.e(Thread.currentThread().getName());
                        if ("200".equals(baseResponse.getCode())) {
                            return Observable.just((BaseWeatherBean) baseResponse.getValue().get(0));
                        } else {
                            mView.setRefresh(false);
                            mView.showNetError();
                            return Observable.error(new Exception("天气信息获取失败"));
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<BaseWeatherBean>() {
                    @Override
                    public void call(BaseWeatherBean baseWeatherBean) {
                        Logger.e(Thread.currentThread().getName());
                        //天气预警
                        insertNewAlarm(baseWeatherBean.getAlarms());
                        insertRealWeather(baseWeatherBean.getRealtime());
                        insertWeekForecast(baseWeatherBean.getWeathers());
                        insertThreeHourForecast(baseWeatherBean.getWeatherDetailsInfo());
                        insertAqi(baseWeatherBean.getPm25());
                        insertZhishu(baseWeatherBean.getIndexes());
                    }
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
                        ToastUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseWeatherBean baseWeatherBean) {
                        mView.loadWeather(baseWeatherBean);
                        mView.setRefresh(false);
                    }
                });
    }

    private BaseWeatherBean getDataFromLocal(String weatherId) {
       /* if (mDao.getAqiDao().queryBuilder().where(AqiDao.Properties.AreaId.eq(weatherId))) {
        }*/
       return null;
    }





    @Override
    public void insertNewAlarm(List<?> alarms) {

    }

    @Override
    public void getAlarmByAreaId(String areaId) {

    }

    @Override
    public void insertRealWeather(RealWeather realWeather) {

    }

    @Override
    public RealWeather queryRealWeatherByAreaId(String areaId) {
        return null;
    }

    @Override
    public void deleteRealWeatherByAreaId(String areaId) {

    }

    @Override
    public void insertWeekForecast(List<WeathersBean> bean) {

    }

    @Override
    public List<WeathersBean> queryWeekForecastByAreaId(String areaId) {
        return null;
    }

    @Override
    public void deleteWeekForecastByAreaId(String areaId) {

    }

    @Override
    public void insertThreeHourForecast(WeatherDetailsInfoBean bean) {

    }

    @Override
    public List<Weather3HoursDetailsInfosBean> queryThreeHourForecastByAreaId(String areaId) {
        return null;
    }

    @Override
    public void deleteThreeHourForecastByAreaId(String areaId) {

    }

    @Override
    public void insertAqi(Aqi aqi) {

    }

    @Override
    public Aqi queryAqiByAreaId(String areaId) {
        return null;
    }

    @Override
    public void deleteAqiByAreaId(String areaId) {

    }

    @Override
    public void insertZhishu(List<IndexesBean> bean) {

    }

    @Override
    public List<IndexesBean> queryZhishuByAreaId(String areaId) {
        return null;
    }

    @Override
    public void deleteZhishuByAreaId(String areaId) {

    }
}
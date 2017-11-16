package com.chen.xinyueweather.module.city;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.api.RetrofitService;
import com.chen.xinyueweather.dao.CityDao;
import com.chen.xinyueweather.dao.bean.City;
import com.chen.xinyueweather.dao.bean.CityManage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author long
 * date 17-10-14
 * desc
 */

public class SearchCityPresenterImpl implements ISearchCityPresenter {

    private ISearchCityView mView;
    private CityDao mCityDao;

    public SearchCityPresenterImpl(ISearchCityView view, CityDao dao) {
        mView = view;
        mCityDao = dao;
    }

    @Override
    public void getData(boolean isRefresh) {
        mView.loadData(mCityDao.getAllProvinceName());
    }


    @Override
    public void queryProvinces() {
        mView.loadData(mCityDao.getAllProvinceName());
    }

    @Override
    public void queryCitiesByProvinceName(String provinceName) {
        mView.loadData(mCityDao.getAllCityNameByProvince(provinceName));
    }

    @Override
    public void queryAreasByCityName(String cityName) {
        mView.loadData(mCityDao.getAllAreaNameByCity(cityName));
    }

    @Override
    public City queryCityByArea(String areaName) {
        return mCityDao.getCityByArea(areaName);
    }

    @Override
    public Observable<List<City>> search(String str) {
        return Observable.just(mCityDao.listCityByArea(str));
    }

    @Override
    public void initHotCities() {
        //初始化热门城市
        for (int i = 0; i < AndroidApplication.HOT_CITYS.size(); i++) {
            AndroidApplication.sCityList.add(mCityDao.getCityByArea(AndroidApplication.HOT_CITYS.get(i)));
        }
    }

    @Override
    public void location(String str) {
        RetrofitService.getLocationInfo(str, "CN")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String str = responseBody.string();
                            com.orhanobut.logger.Logger.e(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .compose(mView.bindToLife())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                    }
                });

    }
}

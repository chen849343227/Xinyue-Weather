package com.chen.xinyueweather.module.city;

import com.chen.xinyueweather.dao.bean.City;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.module.base.IBasePresenter;

import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * @date Created:17-11-3
 * @author along
 * @Description
 */
public interface ISearchCityPresenter extends IBasePresenter {

    void queryProvinces();

    void queryCitiesByProvinceName(String provinceName);

    void queryAreasByCityName(String cityName);

    City queryCityByArea(String areaName);

    Observable<List<City>> search(String str);

    void initHotCities();

    void location(String str);

}

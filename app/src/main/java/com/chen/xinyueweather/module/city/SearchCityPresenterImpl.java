package com.chen.xinyueweather.module.city;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.api.RetrofitService;
import com.chen.xinyueweather.dao.CityDao;
import com.chen.xinyueweather.dao.bean.BaseLocationBean;
import com.chen.xinyueweather.dao.bean.City;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.utils.ToastUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
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
    private Gson gson;

    public SearchCityPresenterImpl(ISearchCityView view, CityDao dao) {
        mView = view;
        mCityDao = dao;
        gson = new Gson();
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
        //Location, request the server to get the location information and parse.
        RetrofitService.getLocationInfo(str, "CN")
                .subscribeOn(Schedulers.io())
                .flatMap((Func1<ResponseBody, Observable<BaseLocationBean>>) responseBody -> {
                    String str1;
                    BaseLocationBean baseLocationBean = null;
                    try {
                        str1 = responseBody.string();
                        baseLocationBean = gson.fromJson(str1, BaseLocationBean.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (baseLocationBean != null) {
                        if (baseLocationBean.getStatus().equals("OK")) {
                            return Observable.just(baseLocationBean);
                        } else {
                            return Observable.error(new Exception("定位失败，请稍后再试！"));
                        }
                    }
                    return Observable.error(new Exception("网络连接错误，请检查网络连接！"));
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(baseLocationBean -> {
                    List<BaseLocationBean.AddressBean.AddressComponentsBean> addressComponentsBeans = baseLocationBean.getResults().get(1).getAddress_components();
                    City city;
                    for (int i = 0; i < addressComponentsBeans.size(); i++) {
                        String areaName = addressComponentsBeans.get(i).getShort_name();
                        Logger.e(areaName);
                        //这里获取到的字符可能是两位，也可能是三位的，统一对三位的进行截取。因为数据库里面都是2位的，不然获取不到数据。
                        if (areaName.length() != 2) {
                            areaName = areaName.substring(0, 2);
                        }
                        city = queryCityByArea(areaName);
                        if (city.getWeatherId() != null) {
                            CityManage cityManage = new CityManage();
                            cityManage.setAreaName(city.getAreaName());
                            cityManage.setWeatherId(city.getWeatherId());
                            cityManage.setTemperature("");
                            cityManage.setWeather("");
                            mView.onLocationSuccess(cityManage);
                            break;
                        }
                    }
                   /* String areaName =.get(0).getShort_name();
                    String areaName1 = baseLocationBean.getResults().get(1).getAddress_components().get(1).getShort_name();
                    String areaName2 = baseLocationBean.getResults().get(1).getAddress_components().get(2).getShort_name();
                    Logger.e(city.toString());
                    if () {

                    } else {
                        ToastUtils.showToast("定位失败，请稍后重试！");
                    }*/
                })
                .compose(mView.bindToLife())
                .subscribe(new Observer<BaseLocationBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        ToastUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseLocationBean baseLocationBean) {
                        String provinceName = baseLocationBean.getResults().get(1).getAddress_components().get(2).getShort_name();
                        String cityName = baseLocationBean.getResults().get(1).getAddress_components().get(1).getShort_name();
                        String areaName = baseLocationBean.getResults().get(1).getAddress_components().get(0).getShort_name();
                        Logger.e("定位成功：" + provinceName + " " + cityName + " " + areaName);
                        ToastUtils.showToast("定位成功：" + provinceName + " " + cityName + " " + areaName);
                    }
                });
    }
}

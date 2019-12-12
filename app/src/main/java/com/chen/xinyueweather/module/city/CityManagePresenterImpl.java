package com.chen.xinyueweather.module.city;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.dao.greendao.CityManageDao;
import com.chen.xinyueweather.module.base.ILocalRxBusPresenter;
import com.chen.xinyueweather.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * author long
 * date 17-10-13
 * desc 城市管理
 */

public class CityManagePresenterImpl implements ILocalRxBusPresenter<CityManage> {
    private static final String TAG = "CityManagePresenterImpl";

    private final ICityManageView mView;
    private final CityManageDao mDao;
    private final RxBus mRxBus;

    public CityManagePresenterImpl(ICityManageView view, CityManageDao dao, RxBus rxbus) {
        mDao = dao;
        mView = view;
        mRxBus = rxbus;
    }

    @Override
    public void getData(boolean isRefresh) {
        mDao.queryBuilder()
                .rx()
                .list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cityManageList -> {
                    mView.showData(cityManageList);
                    mRxBus.post(cityManageList.size());
                });
    }

    @Override
    public void insert(CityManage data) {
        Logger.e(data.toString());
        mDao.rx()
                .insert(data)
                .subscribeOn(Schedulers.io())
                .subscribe(cityManage -> mRxBus.post(AndroidApplication.mCityManages.size()));
    }

    @Override
    public void delete(CityManage data) {
        mDao.rx()
                .delete(data)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        mRxBus.post(AndroidApplication.mCityManages.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        AndroidApplication.mCityManages.remove(data);
                    }
                });
    }

    @Override
    public void update(List<CityManage> list) {
        Observable.from(list)
                .doOnSubscribe(mDao::deleteAll)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CityManage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CityManage cityManage) {
                        mDao.save(cityManage);
                    }
                });
        mDao.deleteAll();
        AndroidApplication.mCityManages.clear();
    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {
        Subscription subscription = mRxBus.doSubscribe(eventType, action, throwable -> Logger.e(throwable.toString()));
        mRxBus.addSubscription(this, subscription);
    }

    @Override
    public void unregisterRxBus() {
        mRxBus.unSubscribe(this);
    }
}

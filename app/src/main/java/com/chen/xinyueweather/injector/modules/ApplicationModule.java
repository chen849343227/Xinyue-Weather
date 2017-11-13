package com.chen.xinyueweather.injector.modules;

import android.content.Context;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.dao.CityDao;
import com.chen.xinyueweather.dao.greendao.DaoSession;
import com.chen.xinyueweather.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * author long
 * date 17-10-10
 * desc
 */
@Module
public class ApplicationModule {
    private final AndroidApplication mApplication;
    private final DaoSession mDaoSession;
    private final RxBus mRxBus;
    private final CityDao mCityDao;

    public ApplicationModule(AndroidApplication application, DaoSession daoSession, RxBus rxBus,CityDao cityDao) {
        mApplication = application;
        mDaoSession = daoSession;
        mRxBus = rxBus;
        mCityDao = cityDao;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    RxBus provideRxBus() {
        return mRxBus;
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession() {
        return mDaoSession;
    }

    @Provides
    @Singleton
    CityDao provideCityDao(){
        return mCityDao;
    }
}

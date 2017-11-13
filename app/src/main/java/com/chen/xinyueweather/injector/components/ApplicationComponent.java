package com.chen.xinyueweather.injector.components;

import android.content.Context;

import com.chen.xinyueweather.dao.CityDao;
import com.chen.xinyueweather.dao.greendao.DaoSession;
import com.chen.xinyueweather.injector.modules.ApplicationModule;
import com.chen.xinyueweather.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * author long
 * date 17-10-10
 * desc
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

//    void inject(BaseActivity baseActivity);

    // provide
    Context getContext();
    RxBus getRxBus();
    DaoSession getDaoSession();
    CityDao getCityDao();
}
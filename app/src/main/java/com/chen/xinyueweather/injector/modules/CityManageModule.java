package com.chen.xinyueweather.injector.modules;

import android.content.Context;

import com.chen.xinyueweather.adapter.ChooseLocationCityAdapter;
import com.chen.xinyueweather.adapter.CityManageAdapter;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.dao.greendao.DaoSession;
import com.chen.xinyueweather.injector.scope.ActivityScope;
import com.chen.xinyueweather.module.base.IBasePresenter;
import com.chen.xinyueweather.module.base.ILocalPresenter;
import com.chen.xinyueweather.module.base.ILocalRxBusPresenter;
import com.chen.xinyueweather.module.city.CityManageActivity;
import com.chen.xinyueweather.module.city.CityManagePresenterImpl;
import com.chen.xinyueweather.module.city.SearchCityActivity;
import com.chen.xinyueweather.rxbus.RxBus;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author along
 * @date Created:17-10-18
 * @Description
 */
@Module
public class CityManageModule {

    private CityManageActivity mView;

    private List<CityManage> mList;

    public CityManageModule(CityManageActivity view, List<CityManage> list) {
        mView = view;
        mList = list;
    }

    @ActivityScope
    @Provides
    public ILocalRxBusPresenter providePresenter(DaoSession dao, RxBus rxbus) {
        return new CityManagePresenterImpl(mView, dao.getCityManageDao(), rxbus);
    }

    @ActivityScope
    @Provides
    public CityManageAdapter provideViewAdapter() {
        return new CityManageAdapter(mView, mList);
    }


}

package com.chen.xinyueweather.injector.modules;

import com.chen.xinyueweather.adapter.AlarmInfoAdapter;
import com.chen.xinyueweather.adapter.CityManageAdapter;
import com.chen.xinyueweather.dao.bean.Alarm;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.dao.greendao.DaoSession;
import com.chen.xinyueweather.injector.scope.ActivityScope;
import com.chen.xinyueweather.module.base.IBasePresenter;
import com.chen.xinyueweather.module.base.ILocalRxBusPresenter;
import com.chen.xinyueweather.module.city.CityManageActivity;
import com.chen.xinyueweather.module.city.CityManagePresenterImpl;
import com.chen.xinyueweather.module.home.AlarmInfoActivity;
import com.chen.xinyueweather.module.home.AlarmInfoPresenterImpl;
import com.chen.xinyueweather.rxbus.RxBus;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmInfoModule {
    private AlarmInfoActivity mView;

    private List<Alarm> mList;

    public AlarmInfoModule(AlarmInfoActivity view, List<Alarm> list) {
        mView = view;
        mList = list;
    }

    @ActivityScope
    @Provides
    public IBasePresenter providePresenter() {
        return new AlarmInfoPresenterImpl(mView);
    }

    @ActivityScope
    @Provides
    public AlarmInfoAdapter provideViewAdapter() {
        return new AlarmInfoAdapter(mView, mList);
    }
}

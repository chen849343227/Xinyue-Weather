package com.chen.xinyueweather.module.home;

import com.chen.xinyueweather.module.base.IBasePresenter;

public class AlarmInfoPresenterImpl implements IBasePresenter{

    private IAlarmInfoView mView;

    public AlarmInfoPresenterImpl(IAlarmInfoView view) {
        mView = view;
    }

    @Override
    public void getData(boolean isRefresh) {
    }
}

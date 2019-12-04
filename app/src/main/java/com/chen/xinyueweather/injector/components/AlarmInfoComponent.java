package com.chen.xinyueweather.injector.components;

import com.chen.xinyueweather.injector.modules.AlarmInfoModule;
import com.chen.xinyueweather.injector.scope.ActivityScope;
import com.chen.xinyueweather.module.home.AlarmInfoActivity;

import dagger.Component;


@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = AlarmInfoModule.class)
public interface AlarmInfoComponent {
    void inject(AlarmInfoActivity activity);

}

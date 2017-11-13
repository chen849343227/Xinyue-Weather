package com.chen.xinyueweather.injector.components;

import com.chen.xinyueweather.injector.modules.CityManageModule;
import com.chen.xinyueweather.injector.scope.ActivityScope;
import com.chen.xinyueweather.module.city.CityManageActivity;

import dagger.Component;

/**
 * author long
 * date 17-10-18
 * desc
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = CityManageModule.class)
public interface CityManageComponent {
    void inject(CityManageActivity activity);
}

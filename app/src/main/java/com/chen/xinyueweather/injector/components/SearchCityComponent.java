package com.chen.xinyueweather.injector.components;

import com.chen.xinyueweather.injector.modules.SearchCityModule;
import com.chen.xinyueweather.injector.scope.ActivityScope;
import com.chen.xinyueweather.module.city.SearchCityActivity;

import dagger.Component;

/**
 * author long
 * date 17-10-14
 * desc
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = SearchCityModule.class)
public interface SearchCityComponent {
    void inject(SearchCityActivity activity);
}

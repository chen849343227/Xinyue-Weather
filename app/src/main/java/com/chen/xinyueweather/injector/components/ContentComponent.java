package com.chen.xinyueweather.injector.components;

import com.chen.xinyueweather.injector.modules.ContentModule;
import com.chen.xinyueweather.injector.scope.FragmentScope;
import com.chen.xinyueweather.module.home.ContentFragment;

import dagger.Component;

/**
 * @date Created:17-11-10
 * @author along
 * @Description
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = ContentModule.class)
public interface ContentComponent {
    void inject(ContentFragment fragment);
}

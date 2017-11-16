package com.chen.xinyueweather.injector.modules;

import com.chen.xinyueweather.dao.greendao.DaoSession;
import com.chen.xinyueweather.injector.scope.FragmentScope;
import com.chen.xinyueweather.module.home.ContentFragment;
import com.chen.xinyueweather.module.home.ContentPresenterImpl;
import com.chen.xinyueweather.module.home.IContentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * author long
 * date 17-11-10
 * desc
 */
@Module
public class ContentModule {

    private final ContentFragment mView;


    private final String mWeatherId;

    public ContentModule(ContentFragment view ,String weatherId) {
        this.mView = view;
        this.mWeatherId = weatherId;
    }

    @FragmentScope
    @Provides
    public IContentPresenter providePresenter(DaoSession dao) {
        return new ContentPresenterImpl(mView, dao, mWeatherId);
    }





}

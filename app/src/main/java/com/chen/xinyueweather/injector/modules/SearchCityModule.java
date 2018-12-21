package com.chen.xinyueweather.injector.modules;

import android.content.Context;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.adapter.ChooseLocationCityAdapter;
import com.chen.xinyueweather.adapter.SearchCityAdapter;
import com.chen.xinyueweather.dao.CityDao;
import com.chen.xinyueweather.injector.scope.ActivityScope;
import com.chen.xinyueweather.module.base.IBasePresenter;
import com.chen.xinyueweather.module.city.ISearchCityPresenter;
import com.chen.xinyueweather.module.city.SearchCityActivity;
import com.chen.xinyueweather.module.city.SearchCityPresenterImpl;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * author long
 * date 17-10-14
 * desc
 */
@Module
public class SearchCityModule {

    private SearchCityActivity mView;

    private List<String> mList;

    public SearchCityModule(SearchCityActivity view, List<String> mList) {
        mView = view;
        this.mList = mList;
    }

    @ActivityScope
    @Provides
    public ISearchCityPresenter providePresenter(CityDao cityDao) {
        return new SearchCityPresenterImpl(mView, cityDao);
    }

    @ActivityScope
    @Provides
    public SearchCityAdapter provideSearchAdapter(Context context) {
        return new SearchCityAdapter(context, AndroidApplication.sCityList);
    }

    @ActivityScope
    @Provides
    public ChooseLocationCityAdapter provideLocationCityAdapter(Context context) {
        return new ChooseLocationCityAdapter(context, mList);
    }

}

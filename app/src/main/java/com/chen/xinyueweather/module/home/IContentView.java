package com.chen.xinyueweather.module.home;

import com.chen.xinyueweather.dao.bean.BaseWeather;
import com.chen.xinyueweather.module.base.IBaseView;

/**
 * author long
 * date 17-11-13
 * desc
 */

public interface IContentView extends IBaseView {

    void loadWeather(BaseWeather weathersBean);

    void setRefresh(boolean isRefresh);
}

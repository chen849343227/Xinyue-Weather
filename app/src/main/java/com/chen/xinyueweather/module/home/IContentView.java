package com.chen.xinyueweather.module.home;

import com.chen.xinyueweather.dao.bean.BaseWeatherBean;
import com.chen.xinyueweather.dao.bean.WeathersBean;
import com.chen.xinyueweather.module.base.IBaseView;

/**
 * author long
 * date 17-11-13
 * desc
 */

public interface IContentView extends IBaseView {

    void loadWeather(BaseWeatherBean weathersBean);

    void setRefresh(boolean isRefresh);
}

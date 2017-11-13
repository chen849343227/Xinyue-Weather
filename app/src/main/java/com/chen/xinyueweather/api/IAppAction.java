package com.chen.xinyueweather.api;

import com.chen.xinyueweather.dao.bean.BaseResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.chen.xinyueweather.api.Params.weather;

/**
 * @date Created:17-10-18
 * @author along
 * @Description
 */
public interface IAppAction {


    /** 101200801
     * http://aider.meizu.com/app/weather/listWeather?cityIds=${WeatherId}
     * @param cityId  天气Id
     * @return
     */
    @GET(weather)
    Observable<BaseResponse> getWeatherFromNet(@Query("cityIds") String cityId);


}

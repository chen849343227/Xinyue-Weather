package com.chen.xinyueweather.api;

import com.chen.xinyueweather.dao.bean.BaseResponse;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.chen.xinyueweather.api.Params.getLocation;
import static com.chen.xinyueweather.api.Params.weather;

/**
 * @author along
 * @date Created:17-10-18
 * @Description
 */
public interface IAppAction {


    /**
     * 101200801
     * http://aider.meizu.com/app/weather/listWeather?cityIds=${WeatherId}
     *
     * @param cityId 天气Id
     * @return
     */
    @GET(weather)
    Observable<BaseResponse> getWeatherFromNet(@Query("cityIds") String cityId);

    @GET(getLocation)
    Observable<ResponseBody> getAreaFronNet(@Query("latlng") String latlng, @Query("language") String language);
}

package com.chen.xinyueweather.dao.bean;

import java.util.List;

/**
 * author long
 * date 17-10-13
 * desc  天气详细信息实体类
 */
public class BaseWeatherBean {
    /**
     * alarms : []
     * city : 北京
     * cityid : 101010100
     * indexes : [{"abbreviation":"pp","alias":"","content":"建议用露质面霜打底，水质无油粉底霜，透明粉饼，粉质胭脂。","level":"控油","name":"化妆指数"},{"abbreviation":"gm","alias":"","content":"感冒容易发生，少去人群密集的场所有利于降低感冒的几率。","level":"易发","name":"感冒指数"},{"abbreviation":"xc","alias":"","content":"洗车后，只能保持1天车辆清洁，不太适宜洗车。","level":"较不适宜","name":"洗车指数"},{"abbreviation":"ct","alias":"","content":"天气偏凉，可以穿上最时尚的那件大衣来凹造型，搭一条围巾时尚指数爆表。","level":"凉","name":"穿衣指数"},{"abbreviation":"uv","alias":"","content":"辐射弱，涂擦SPF8-12防晒护肤品。","level":"最弱","name":"紫外线强度指数"},{"abbreviation":"yd","alias":"","content":"天气较好，且紫外线辐射不强，适宜户外运动。","level":"适宜","name":"运动指数"}]
     * pm25 : {"advice":"0","aqi":"72","citycount":606,"cityrank":25,"co":"7","color":"0","level":"0","no2":"24","o3":"18","pm10":"52","pm25":"71","quality":"良","so2":"1","timestamp":"","upDateTime":"2017-10-13 14:00:00"}
     * provinceName : 北京市
     * realtime : {"img":"0","sD":"46","sendibleTemp":"19","temp":"19","time":"2017-10-13 15:00:00","wD":"西风","wS":"2级","weather":"晴","ziwaixian":"N/A"}
     * weatherDetailsInfo : {"publishTime":"2017-10-13 15:00:00","weather3HoursDetailsInfos":[{"endTime":"2017-10-13 19:00:00","highestTemperature":"19","img":"0","isRainFall":"","lowerestTemperature":"19","precipitation":"0","startTime":"2017-10-13 16:00:00","wd":"","weather":"晴","ws":""},{"endTime":"2017-10-13 22:00:00","highestTemperature":"13","img":"0","isRainFall":"","lowerestTemperature":"13","precipitation":"0","startTime":"2017-10-13 19:00:00","wd":"","weather":"晴","ws":""},{"endTime":"2017-10-14 01:00:00","highestTemperature":"10","img":"1","isRainFall":"","lowerestTemperature":"10","precipitation":"0","startTime":"2017-10-13 22:00:00","wd":"","weather":"少云","ws":""},{"endTime":"2017-10-14 04:00:00","highestTemperature":"10","img":"1","isRainFall":"","lowerestTemperature":"10","precipitation":"0","startTime":"2017-10-14 01:00:00","wd":"","weather":"少云","ws":""},{"endTime":"2017-10-14 07:00:00","highestTemperature":"11","img":"1","isRainFall":"","lowerestTemperature":"11","precipitation":"0","startTime":"2017-10-14 04:00:00","wd":"","weather":"少云","ws":""},{"endTime":"2017-10-14 10:00:00","highestTemperature":"12","img":"1","isRainFall":"","lowerestTemperature":"12","precipitation":"0","startTime":"2017-10-14 07:00:00","wd":"","weather":"少云","ws":""},{"endTime":"2017-10-14 13:00:00","highestTemperature":"14","img":"2","isRainFall":"","lowerestTemperature":"14","precipitation":"0","startTime":"2017-10-14 10:00:00","wd":"","weather":"阴","ws":""}]}
     * weathers : [{"date":"2017-10-13","img":"0","sun_down_time":"17:39","sun_rise_time":"06:22","temp_day_c":"19","temp_day_f":"66.2","temp_night_c":"5","temp_night_f":"41.0","wd":"","weather":"晴","week":"星期五","ws":""},{"date":"2017-10-14","img":"2","sun_down_time":"17:39","sun_rise_time":"06:22","temp_day_c":"13","temp_day_f":"55.4","temp_night_c":"10","temp_night_f":"50.0","wd":"","weather":"阴","week":"星期六","ws":""},{"date":"2017-10-15","img":"2","sun_down_time":"17:39","sun_rise_time":"06:22","temp_day_c":"16","temp_day_f":"60.8","temp_night_c":"8","temp_night_f":"46.4","wd":"","weather":"阴","week":"星期日","ws":""},{"date":"2017-10-16","img":"0","sun_down_time":"17:39","sun_rise_time":"06:22","temp_day_c":"17","temp_day_f":"62.6","temp_night_c":"7","temp_night_f":"44.6","wd":"","weather":"晴","week":"星期一","ws":""},{"date":"2017-10-17","img":"1","sun_down_time":"17:39","sun_rise_time":"06:22","temp_day_c":"15","temp_day_f":"59.0","temp_night_c":"10","temp_night_f":"50.0","wd":"","weather":"多云","week":"星期二","ws":""},{"date":"2017-10-18","img":"2","sun_down_time":"17:39","sun_rise_time":"06:22","temp_day_c":"16","temp_day_f":"60.8","temp_night_c":"8","temp_night_f":"46.4","wd":"","weather":"阴","week":"星期三","ws":""},{"date":"2017-10-12","img":"7","sun_down_time":"17:39","sun_rise_time":"06:22","temp_day_c":"14","temp_day_f":"57.2","temp_night_c":"6","temp_night_f":"42.8","wd":"","weather":"小雨","week":"星期四","ws":""}]
     */

    private String city;
    private int cityid;
    private Aqi pm25;
    private String provinceName;
    private RealWeather realtime;
    private WeatherDetailsInfoBean weatherDetailsInfo;
    private List<?> alarms;
    private List<IndexesBean> indexes;
    private List<WeathersBean> weathers;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public Aqi getPm25() {
        return pm25;
    }

    public void setPm25(Aqi pm25) {
        this.pm25 = pm25;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public RealWeather getRealtime() {
        return realtime;
    }

    public void setRealtime(RealWeather realtime) {
        this.realtime = realtime;
    }

    public WeatherDetailsInfoBean getWeatherDetailsInfo() {
        return weatherDetailsInfo;
    }

    public void setWeatherDetailsInfo(WeatherDetailsInfoBean weatherDetailsInfo) {
        this.weatherDetailsInfo = weatherDetailsInfo;
    }

    public List<?> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<?> alarms) {
        this.alarms = alarms;
    }

    public List<IndexesBean> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<IndexesBean> indexes) {
        this.indexes = indexes;
    }

    public List<WeathersBean> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<WeathersBean> weathers) {
        this.weathers = weathers;
    }

}

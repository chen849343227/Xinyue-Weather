package com.chen.xinyueweather.dao.bean;

import java.util.List;

/**
 * author long
 * date 17-10-13
 * desc
 */
public class WeatherDetailInfo {
    /**
     * publishTime : 2017-10-13 15:00:00
     * weather3HourDetailInfos : [{"endTime":"2017-10-13 19:00:00","highestTemperature":"19","img":"0","isRainFall":"","lowerestTemperature":"19","precipitation":"0","startTime":"2017-10-13 16:00:00","wd":"","weather":"晴","ws":""},{"endTime":"2017-10-13 22:00:00","highestTemperature":"13","img":"0","isRainFall":"","lowerestTemperature":"13","precipitation":"0","startTime":"2017-10-13 19:00:00","wd":"","weather":"晴","ws":""},{"endTime":"2017-10-14 01:00:00","highestTemperature":"10","img":"1","isRainFall":"","lowerestTemperature":"10","precipitation":"0","startTime":"2017-10-13 22:00:00","wd":"","weather":"少云","ws":""},{"endTime":"2017-10-14 04:00:00","highestTemperature":"10","img":"1","isRainFall":"","lowerestTemperature":"10","precipitation":"0","startTime":"2017-10-14 01:00:00","wd":"","weather":"少云","ws":""},{"endTime":"2017-10-14 07:00:00","highestTemperature":"11","img":"1","isRainFall":"","lowerestTemperature":"11","precipitation":"0","startTime":"2017-10-14 04:00:00","wd":"","weather":"少云","ws":""},{"endTime":"2017-10-14 10:00:00","highestTemperature":"12","img":"1","isRainFall":"","lowerestTemperature":"12","precipitation":"0","startTime":"2017-10-14 07:00:00","wd":"","weather":"少云","ws":""},{"endTime":"2017-10-14 13:00:00","highestTemperature":"14","img":"2","isRainFall":"","lowerestTemperature":"14","precipitation":"0","startTime":"2017-10-14 10:00:00","wd":"","weather":"阴","ws":""}]
     */

    private String publishTime;
    private List<Weather3HourDetailInfo> weather3HourDetailInfos;

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public List<Weather3HourDetailInfo> getWeather3HourDetailInfo() {
        return weather3HourDetailInfos;
    }

    public void setWeather3HourDetailInfo(List<Weather3HourDetailInfo> weather3HourDetailInfos) {
        this.weather3HourDetailInfos = weather3HourDetailInfos;
    }

}

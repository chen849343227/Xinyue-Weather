package com.chen.xinyueweather.dao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author long
 * date 17-10-13
 * desc 三小时预报
 */
@Entity
public class Weather3HourDetailInfo {


    /**
     * endTime : 2017-10-13 19:00:00
     * highestTemperature : 19
     * img : 0
     * isRainFall :
     * lowerestTemperature : 19
     * precipitation : 0
     * startTime : 2017-10-13 16:00:00
     * wd :
     * weather : 晴
     * ws :
     */
    @Id(autoincrement = true)
    private Long id;
    private String areaId;
    private String endTime;
    private String highestTemperature;
    private String img;
    private String isRainFall;
    private String lowerestTemperature;
    private String precipitation;
    private String startTime;
    private String wd;
    private String weather;
    private String ws;


    public Weather3HourDetailInfo() {
    }




    @Generated(hash = 1373767997)
    public Weather3HourDetailInfo(Long id, String areaId, String endTime,
            String highestTemperature, String img, String isRainFall,
            String lowerestTemperature, String precipitation, String startTime,
            String wd, String weather, String ws) {
        this.id = id;
        this.areaId = areaId;
        this.endTime = endTime;
        this.highestTemperature = highestTemperature;
        this.img = img;
        this.isRainFall = isRainFall;
        this.lowerestTemperature = lowerestTemperature;
        this.precipitation = precipitation;
        this.startTime = startTime;
        this.wd = wd;
        this.weather = weather;
        this.ws = ws;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHighestTemperature() {
        return highestTemperature;
    }

    public void setHighestTemperature(String highestTemperature) {
        this.highestTemperature = highestTemperature;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIsRainFall() {
        return isRainFall;
    }

    public void setIsRainFall(String isRainFall) {
        this.isRainFall = isRainFall;
    }

    public String getLowerestTemperature() {
        return lowerestTemperature;
    }

    public void setLowerestTemperature(String lowerestTemperature) {
        this.lowerestTemperature = lowerestTemperature;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }
}

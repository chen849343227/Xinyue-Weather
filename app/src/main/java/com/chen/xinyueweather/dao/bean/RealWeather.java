package com.chen.xinyueweather.dao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author long
 * date 17-10-13
 * desc 实时天气
 */
@Entity
public class RealWeather {
    /**
     * img : 0
     * sD : 46
     * sendibleTemp : 19
     * temp : 19
     * time : 2017-10-13 15:00:00
     * wD : 西风
     * wS : 2级
     * weather : 晴
     * ziwaixian : N/A
     */

    @Id
    private String areaId;
    private String img;
    private String sD;
    private String sendibleTemp;
    private String temp;
    private String time;
    private String wD;
    private String wS;
    private String weather;
    private String ziwaixian;

    public RealWeather() {
    }

    @Generated(hash = 1840501534)
    public RealWeather(String areaId, String img, String sD, String sendibleTemp,
            String temp, String time, String wD, String wS, String weather,
            String ziwaixian) {
        this.areaId = areaId;
        this.img = img;
        this.sD = sD;
        this.sendibleTemp = sendibleTemp;
        this.temp = temp;
        this.time = time;
        this.wD = wD;
        this.wS = wS;
        this.weather = weather;
        this.ziwaixian = ziwaixian;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSD() {
        return sD;
    }

    public void setSD(String sD) {
        this.sD = sD;
    }

    public String getSendibleTemp() {
        return sendibleTemp;
    }

    public void setSendibleTemp(String sendibleTemp) {
        this.sendibleTemp = sendibleTemp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWD() {
        return wD;
    }

    public void setWD(String wD) {
        this.wD = wD;
    }

    public String getWS() {
        return wS;
    }

    public void setWS(String wS) {
        this.wS = wS;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getZiwaixian() {
        return ziwaixian;
    }

    public void setZiwaixian(String ziwaixian) {
        this.ziwaixian = ziwaixian;
    }
}

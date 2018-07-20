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
     * sd : 46
     * sendibleTemp : 19
     * temp : 19
     * time : 2017-10-13 15:00:00
     * wd : 西风
     * ws : 2级
     * weather : 晴
     * ziwaixian : N/A
     */

    @Id
    private String areaId;
    private String img;
    private String sd;
    private String sendibleTemp;
    private String temp;
    private String time;
    private String wd;
    private String ws;
    private String weather;
    private String ziwaixian;

    public RealWeather() {
    }



    @Generated(hash = 749938091)
    public RealWeather(String areaId, String img, String sd, String sendibleTemp,
            String temp, String time, String wd, String ws, String weather,
            String ziwaixian) {
        this.areaId = areaId;
        this.img = img;
        this.sd = sd;
        this.sendibleTemp = sendibleTemp;
        this.temp = temp;
        this.time = time;
        this.wd = wd;
        this.ws = ws;
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
        return sd;
    }

    public void setSD(String sD) {
        this.sd = sD;
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
        return wd;
    }

    public void setWD(String wD) {
        this.wd = wD;
    }

    public String getWS() {
        return ws;
    }

    public void setWS(String wS) {
        this.ws = wS;
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

    @Override
    public String toString() {
        return "RealWeather{" +
                "areaId='" + areaId + '\'' +
                ", img='" + img + '\'' +
                ", sd='" + sd + '\'' +
                ", sendibleTemp='" + sendibleTemp + '\'' +
                ", temp='" + temp + '\'' +
                ", time='" + time + '\'' +
                ", wd='" + wd + '\'' +
                ", ws='" + ws + '\'' +
                ", weather='" + weather + '\'' +
                ", ziwaixian='" + ziwaixian + '\'' +
                '}';
    }



    public String getWs() {
        return this.ws;
    }



    public void setWs(String ws) {
        this.ws = ws;
    }



    public String getWd() {
        return this.wd;
    }



    public void setWd(String wd) {
        this.wd = wd;
    }



    public String getSd() {
        return this.sd;
    }



    public void setSd(String sd) {
        this.sd = sd;
    }
}

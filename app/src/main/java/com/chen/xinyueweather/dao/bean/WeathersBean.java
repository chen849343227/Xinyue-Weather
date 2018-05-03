package com.chen.xinyueweather.dao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author long
 * date 17-10-13
 * desc  单个天气实体
 */
@Entity
public class WeathersBean {
    /**
     * date : 2017-10-13
     * img : 0
     * sun_down_time : 17:39
     * sun_rise_time : 06:22
     * temp_day_c : 19
     * temp_day_f : 66.2
     * temp_night_c : 5
     * temp_night_f : 41.0
     * wd :
     * weather : 晴
     * week : 星期五
     * ws :
     */
    @Id
    private String areaId;
    private String date;
    private String img;
    private String sun_down_time;
    private String sun_rise_time;
    private String temp_day_c;
    private String temp_day_f;
    private String temp_night_c;
    private String temp_night_f;
    private String wd;
    private String weather;
    private String week;
    private String ws;

    public WeathersBean() {
    }



    @Generated(hash = 547938529)
    public WeathersBean(String areaId, String date, String img,
            String sun_down_time, String sun_rise_time, String temp_day_c,
            String temp_day_f, String temp_night_c, String temp_night_f, String wd,
            String weather, String week, String ws) {
        this.areaId = areaId;
        this.date = date;
        this.img = img;
        this.sun_down_time = sun_down_time;
        this.sun_rise_time = sun_rise_time;
        this.temp_day_c = temp_day_c;
        this.temp_day_f = temp_day_f;
        this.temp_night_c = temp_night_c;
        this.temp_night_f = temp_night_f;
        this.wd = wd;
        this.weather = weather;
        this.week = week;
        this.ws = ws;
    }



    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSun_down_time() {
        return sun_down_time;
    }

    public void setSun_down_time(String sun_down_time) {
        this.sun_down_time = sun_down_time;
    }

    public String getSun_rise_time() {
        return sun_rise_time;
    }

    public void setSun_rise_time(String sun_rise_time) {
        this.sun_rise_time = sun_rise_time;
    }

    public String getTemp_day_c() {
        return temp_day_c;
    }

    public void setTemp_day_c(String temp_day_c) {
        this.temp_day_c = temp_day_c;
    }

    public String getTemp_day_f() {
        return temp_day_f;
    }

    public void setTemp_day_f(String temp_day_f) {
        this.temp_day_f = temp_day_f;
    }

    public String getTemp_night_c() {
        return temp_night_c;
    }

    public void setTemp_night_c(String temp_night_c) {
        this.temp_night_c = temp_night_c;
    }

    public String getTemp_night_f() {
        return temp_night_f;
    }

    public void setTemp_night_f(String temp_night_f) {
        this.temp_night_f = temp_night_f;
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

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }
}

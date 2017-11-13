package com.chen.xinyueweather.dao.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


/**
 * @author along
 * @date Created:17-11-2
 * @Description
 */
@Entity
public class CityManage implements Parcelable {

    @Id(autoincrement = true)
    private Long tabId;
    private String areaName;
    private String weather;
    private int temperature;
    private String weatherId;

    public CityManage() {
    }

    protected CityManage(Parcel in) {
        areaName = in.readString();
        weather = in.readString();
        temperature = in.readInt();
        weatherId = in.readString();
    }

    @Generated(hash = 477472687)
    public CityManage(Long tabId, String areaName, String weather, int temperature,
            String weatherId) {
        this.tabId = tabId;
        this.areaName = areaName;
        this.weather = weather;
        this.temperature = temperature;
        this.weatherId = weatherId;
    }

   


    public static final Creator<CityManage> CREATOR = new Creator<CityManage>() {
        @Override
        public CityManage createFromParcel(Parcel in) {
            return new CityManage(in);
        }

        @Override
        public CityManage[] newArray(int size) {
            return new CityManage[size];
        }
    };

    public Long getTabId() {
        return this.tabId;
    }

    public void setTabId(Long tabId) {
        this.tabId = tabId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(areaName);
        dest.writeString(weather);
        dest.writeInt(temperature);
        dest.writeString(weatherId);
    }

    @Override
    public String toString() {
        return "CityManage{" +
                ", areaName='" + areaName + '\'' +
                ", weather='" + weather + '\'' +
                ", temperature=" + temperature +
                ", weatherId=" + weatherId +
                '}';
    }


}

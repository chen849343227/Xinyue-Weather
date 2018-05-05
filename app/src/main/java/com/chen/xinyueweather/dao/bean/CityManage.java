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

    @Id
    private String weatherId;
    private String areaName;
    private String weather;
    private String temperature;



    public CityManage() {
    }

    protected CityManage(Parcel in) {
        areaName = in.readString();
        weather = in.readString();
        temperature = in.readString();
        weatherId = in.readString();
    }

    @Generated(hash = 436006638)
    public CityManage(String weatherId, String areaName, String weather,
            String temperature) {
        this.weatherId = weatherId;
        this.areaName = areaName;
        this.weather = weather;
        this.temperature = temperature;
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

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
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
        dest.writeString(temperature);
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

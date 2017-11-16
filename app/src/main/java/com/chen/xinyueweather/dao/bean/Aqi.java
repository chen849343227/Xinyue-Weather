package com.chen.xinyueweather.dao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author long
 * date 17-10-13
 * desc 空气质量指数
 */
@Entity
public class Aqi {
    /**
     * advice : 0
     * aqi : 72
     * citycount : 606
     * cityrank : 25
     * co : 7
     * color : 0
     * level : 0
     * no2 : 24
     * o3 : 18
     * pm10 : 52
     * pm25 : 71
     * quality : 良
     * so2 : 1
     * timestamp :
     * upDateTime : 2017-10-13 14:00:00
     */

    private String areaId;
    private String advice;
    private String aqi;
    private int citycount;
    private int cityrank;
    private String co;
    private String color;
    private String level;
    private String no2;
    private String o3;
    private String pm10;
    private String pm25;
    private String quality;
    private String so2;
    private String timestamp;
    private String upDateTime;

    public Aqi() {
    }



    @Generated(hash = 5613955)
    public Aqi(String areaId, String advice, String aqi, int citycount,
            int cityrank, String co, String color, String level, String no2,
            String o3, String pm10, String pm25, String quality, String so2,
            String timestamp, String upDateTime) {
        this.areaId = areaId;
        this.advice = advice;
        this.aqi = aqi;
        this.citycount = citycount;
        this.cityrank = cityrank;
        this.co = co;
        this.color = color;
        this.level = level;
        this.no2 = no2;
        this.o3 = o3;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.quality = quality;
        this.so2 = so2;
        this.timestamp = timestamp;
        this.upDateTime = upDateTime;
    }



    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public int getCitycount() {
        return citycount;
    }

    public void setCitycount(int citycount) {
        this.citycount = citycount;
    }

    public int getCityrank() {
        return cityrank;
    }

    public void setCityrank(int cityrank) {
        this.cityrank = cityrank;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(String upDateTime) {
        this.upDateTime = upDateTime;
    }
}

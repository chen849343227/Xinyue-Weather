package com.chen.xinyueweather.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chen.xinyueweather.dao.bean.City;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author along
 * @date Created:17-10-18
 * @Description
 */
public class CityDao {

    private Context context;
    private DBOpenHelper dbOpenHelper;

    public CityDao(Context context) {
        this.context = context;
        dbOpenHelper = new DBOpenHelper(context);
    }


    /**
     * 得到所有省的名称
     *
     * @return
     */
    public List<String> getAllProvinceName() {
        List<String> allProvinceName = null;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select distinct province_name from weathers", null);
        if (cursor != null) {
            allProvinceName = new ArrayList<>();
            while (cursor.moveToNext()) {
                allProvinceName.add(cursor.getString(cursor.getColumnIndex("province_name")));
            }
            cursor.close();
        }
        return allProvinceName;
    }

    /**
     * 通过省得到所有的城市的名称
     *
     * @return
     */
    public List<String> getAllCityNameByProvince(String provinceName) {
        List<String> allCityName = null;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select distinct city_name from weathers where province_name = '" + provinceName + "'", null);
        if (cursor != null) {
            allCityName = new ArrayList<>();
            while (cursor.moveToNext()) {
                allCityName.add(cursor.getString(cursor.getColumnIndex("city_name")));
            }
            cursor.close();
        }
        Logger.e(allCityName.toString());
        return allCityName;
    }

    /**
     * 通过城市得到当前地区的名称
     *
     * @param cityName
     * @return
     */
    public List<String> getAllAreaNameByCity(String cityName) {
        List<String> allAreaName = null;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select area_name from weathers where city_name = '" + cityName + "'", null);
        if (cursor != null) {
            allAreaName = new ArrayList<>();
            while (cursor.moveToNext()) {
                allAreaName.add(cursor.getString(cursor.getColumnIndex("area_name")));
            }
            cursor.close();
        }
        Logger.e(allAreaName.toString());
        return allAreaName;
    }

    /**
     * 通过地区名来获取当前城市信息
     *
     * @param areaName 地区名
     * @return
     */
    public City getCityByArea(String areaName) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from weathers where area_name = '" + areaName + "'", null);
        City city = null;
        if (cursor != null) {
            city = new City();
            while (cursor.moveToNext()) {
                city.setAreaName(cursor.getString(cursor.getColumnIndex("area_name")));
                city.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setWeatherId(cursor.getString(cursor.getColumnIndex("weather_id")));
                city.setId(cursor.getLong(cursor.getColumnIndex("id")));
            }
            cursor.close();
        }
        return city;
    }

    /**
     * 通过地区名来获取城市信息
     *
     * @param areaName 地区名
     * @return
     */
    public List<City> listCityByArea(String areaName) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from weathers where area_name LIKE '" + "%" + areaName + "%" + "'", null);
        Logger.e("select * from weathers where area_name LIKE '" + "%" + areaName + "%" + "'");
        List<City> cities = null;
        City city;
        if (cursor != null) {
            cities = new ArrayList<>();
            while (cursor.moveToNext()) {
                city = new City();
                city.setAreaName(cursor.getString(cursor.getColumnIndex("area_name")));
                city.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setWeatherId(cursor.getString(cursor.getColumnIndex("weather_id")));
                city.setId(cursor.getLong(cursor.getColumnIndex("id")));
                cities.add(city);
            }
            cursor.close();
        }
        return cities;
    }

    public City getCityByWeatherID(String weatherId) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from weathers where weatherId ='" + weatherId + "'", null);
        City city = new City();
        if (cursor.moveToNext()) {
            city.setAreaName(cursor.getString(cursor.getColumnIndex("area_name")));
            city.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            city.setWeatherId(cursor.getString(cursor.getColumnIndex("weather_id")));
            city.setId(cursor.getLong(cursor.getColumnIndex("id")));
            cursor.close();
        } else {
            cursor.close();
            return null;
        }

        return city;
    }

}

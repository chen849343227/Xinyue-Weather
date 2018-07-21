package com.chen.xinyueweather.dao.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.chen.xinyueweather.dao.bean.Alarm;
import com.chen.xinyueweather.dao.bean.Aqi;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.dao.bean.IndexesBean;
import com.chen.xinyueweather.dao.bean.RealWeather;
import com.chen.xinyueweather.dao.bean.Weather3HoursDetailsInfosBean;
import com.chen.xinyueweather.dao.bean.WeathersBean;

import com.chen.xinyueweather.dao.greendao.AlarmDao;
import com.chen.xinyueweather.dao.greendao.AqiDao;
import com.chen.xinyueweather.dao.greendao.CityManageDao;
import com.chen.xinyueweather.dao.greendao.IndexesBeanDao;
import com.chen.xinyueweather.dao.greendao.RealWeatherDao;
import com.chen.xinyueweather.dao.greendao.Weather3HoursDetailsInfosBeanDao;
import com.chen.xinyueweather.dao.greendao.WeathersBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig alarmDaoConfig;
    private final DaoConfig aqiDaoConfig;
    private final DaoConfig cityManageDaoConfig;
    private final DaoConfig indexesBeanDaoConfig;
    private final DaoConfig realWeatherDaoConfig;
    private final DaoConfig weather3HoursDetailsInfosBeanDaoConfig;
    private final DaoConfig weathersBeanDaoConfig;

    private final AlarmDao alarmDao;
    private final AqiDao aqiDao;
    private final CityManageDao cityManageDao;
    private final IndexesBeanDao indexesBeanDao;
    private final RealWeatherDao realWeatherDao;
    private final Weather3HoursDetailsInfosBeanDao weather3HoursDetailsInfosBeanDao;
    private final WeathersBeanDao weathersBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        alarmDaoConfig = daoConfigMap.get(AlarmDao.class).clone();
        alarmDaoConfig.initIdentityScope(type);

        aqiDaoConfig = daoConfigMap.get(AqiDao.class).clone();
        aqiDaoConfig.initIdentityScope(type);

        cityManageDaoConfig = daoConfigMap.get(CityManageDao.class).clone();
        cityManageDaoConfig.initIdentityScope(type);

        indexesBeanDaoConfig = daoConfigMap.get(IndexesBeanDao.class).clone();
        indexesBeanDaoConfig.initIdentityScope(type);

        realWeatherDaoConfig = daoConfigMap.get(RealWeatherDao.class).clone();
        realWeatherDaoConfig.initIdentityScope(type);

        weather3HoursDetailsInfosBeanDaoConfig = daoConfigMap.get(Weather3HoursDetailsInfosBeanDao.class).clone();
        weather3HoursDetailsInfosBeanDaoConfig.initIdentityScope(type);

        weathersBeanDaoConfig = daoConfigMap.get(WeathersBeanDao.class).clone();
        weathersBeanDaoConfig.initIdentityScope(type);

        alarmDao = new AlarmDao(alarmDaoConfig, this);
        aqiDao = new AqiDao(aqiDaoConfig, this);
        cityManageDao = new CityManageDao(cityManageDaoConfig, this);
        indexesBeanDao = new IndexesBeanDao(indexesBeanDaoConfig, this);
        realWeatherDao = new RealWeatherDao(realWeatherDaoConfig, this);
        weather3HoursDetailsInfosBeanDao = new Weather3HoursDetailsInfosBeanDao(weather3HoursDetailsInfosBeanDaoConfig, this);
        weathersBeanDao = new WeathersBeanDao(weathersBeanDaoConfig, this);

        registerDao(Alarm.class, alarmDao);
        registerDao(Aqi.class, aqiDao);
        registerDao(CityManage.class, cityManageDao);
        registerDao(IndexesBean.class, indexesBeanDao);
        registerDao(RealWeather.class, realWeatherDao);
        registerDao(Weather3HoursDetailsInfosBean.class, weather3HoursDetailsInfosBeanDao);
        registerDao(WeathersBean.class, weathersBeanDao);
    }
    
    public void clear() {
        alarmDaoConfig.clearIdentityScope();
        aqiDaoConfig.clearIdentityScope();
        cityManageDaoConfig.clearIdentityScope();
        indexesBeanDaoConfig.clearIdentityScope();
        realWeatherDaoConfig.clearIdentityScope();
        weather3HoursDetailsInfosBeanDaoConfig.clearIdentityScope();
        weathersBeanDaoConfig.clearIdentityScope();
    }

    public AlarmDao getAlarmDao() {
        return alarmDao;
    }

    public AqiDao getAqiDao() {
        return aqiDao;
    }

    public CityManageDao getCityManageDao() {
        return cityManageDao;
    }

    public IndexesBeanDao getIndexesBeanDao() {
        return indexesBeanDao;
    }

    public RealWeatherDao getRealWeatherDao() {
        return realWeatherDao;
    }

    public Weather3HoursDetailsInfosBeanDao getWeather3HoursDetailsInfosBeanDao() {
        return weather3HoursDetailsInfosBeanDao;
    }

    public WeathersBeanDao getWeathersBeanDao() {
        return weathersBeanDao;
    }

}

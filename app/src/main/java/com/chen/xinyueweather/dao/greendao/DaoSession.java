package com.chen.xinyueweather.dao.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.chen.xinyueweather.dao.bean.RealWeather;
import com.chen.xinyueweather.dao.bean.Aqi;
import com.chen.xinyueweather.dao.bean.WeathersBean;
import com.chen.xinyueweather.dao.bean.IndexesBean;
import com.chen.xinyueweather.dao.bean.Weather3HoursDetailsInfosBean;
import com.chen.xinyueweather.dao.bean.CityManage;

import com.chen.xinyueweather.dao.greendao.RealWeatherDao;
import com.chen.xinyueweather.dao.greendao.AqiDao;
import com.chen.xinyueweather.dao.greendao.WeathersBeanDao;
import com.chen.xinyueweather.dao.greendao.IndexesBeanDao;
import com.chen.xinyueweather.dao.greendao.Weather3HoursDetailsInfosBeanDao;
import com.chen.xinyueweather.dao.greendao.CityManageDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig realWeatherDaoConfig;
    private final DaoConfig aqiDaoConfig;
    private final DaoConfig weathersBeanDaoConfig;
    private final DaoConfig indexesBeanDaoConfig;
    private final DaoConfig weather3HoursDetailsInfosBeanDaoConfig;
    private final DaoConfig cityManageDaoConfig;

    private final RealWeatherDao realWeatherDao;
    private final AqiDao aqiDao;
    private final WeathersBeanDao weathersBeanDao;
    private final IndexesBeanDao indexesBeanDao;
    private final Weather3HoursDetailsInfosBeanDao weather3HoursDetailsInfosBeanDao;
    private final CityManageDao cityManageDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        realWeatherDaoConfig = daoConfigMap.get(RealWeatherDao.class).clone();
        realWeatherDaoConfig.initIdentityScope(type);

        aqiDaoConfig = daoConfigMap.get(AqiDao.class).clone();
        aqiDaoConfig.initIdentityScope(type);

        weathersBeanDaoConfig = daoConfigMap.get(WeathersBeanDao.class).clone();
        weathersBeanDaoConfig.initIdentityScope(type);

        indexesBeanDaoConfig = daoConfigMap.get(IndexesBeanDao.class).clone();
        indexesBeanDaoConfig.initIdentityScope(type);

        weather3HoursDetailsInfosBeanDaoConfig = daoConfigMap.get(Weather3HoursDetailsInfosBeanDao.class).clone();
        weather3HoursDetailsInfosBeanDaoConfig.initIdentityScope(type);

        cityManageDaoConfig = daoConfigMap.get(CityManageDao.class).clone();
        cityManageDaoConfig.initIdentityScope(type);

        realWeatherDao = new RealWeatherDao(realWeatherDaoConfig, this);
        aqiDao = new AqiDao(aqiDaoConfig, this);
        weathersBeanDao = new WeathersBeanDao(weathersBeanDaoConfig, this);
        indexesBeanDao = new IndexesBeanDao(indexesBeanDaoConfig, this);
        weather3HoursDetailsInfosBeanDao = new Weather3HoursDetailsInfosBeanDao(weather3HoursDetailsInfosBeanDaoConfig, this);
        cityManageDao = new CityManageDao(cityManageDaoConfig, this);

        registerDao(RealWeather.class, realWeatherDao);
        registerDao(Aqi.class, aqiDao);
        registerDao(WeathersBean.class, weathersBeanDao);
        registerDao(IndexesBean.class, indexesBeanDao);
        registerDao(Weather3HoursDetailsInfosBean.class, weather3HoursDetailsInfosBeanDao);
        registerDao(CityManage.class, cityManageDao);
    }
    
    public void clear() {
        realWeatherDaoConfig.clearIdentityScope();
        aqiDaoConfig.clearIdentityScope();
        weathersBeanDaoConfig.clearIdentityScope();
        indexesBeanDaoConfig.clearIdentityScope();
        weather3HoursDetailsInfosBeanDaoConfig.clearIdentityScope();
        cityManageDaoConfig.clearIdentityScope();
    }

    public RealWeatherDao getRealWeatherDao() {
        return realWeatherDao;
    }

    public AqiDao getAqiDao() {
        return aqiDao;
    }

    public WeathersBeanDao getWeathersBeanDao() {
        return weathersBeanDao;
    }

    public IndexesBeanDao getIndexesBeanDao() {
        return indexesBeanDao;
    }

    public Weather3HoursDetailsInfosBeanDao getWeather3HoursDetailsInfosBeanDao() {
        return weather3HoursDetailsInfosBeanDao;
    }

    public CityManageDao getCityManageDao() {
        return cityManageDao;
    }

}

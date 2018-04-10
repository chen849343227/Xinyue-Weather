package com.chen.xinyueweather.module.city;

import com.chen.xinyueweather.dao.bean.City;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.module.base.IBaseView;

import java.util.List;

/**
 * @date Created:17-10-18
 * @author along
 * @Description
 */
public interface ISearchCityView extends IBaseView{
    /**
     * 显示所有的数据
     * @param locations
     */
    void loadData(List<String> locations);

    /**
     * 显示定位信息
     * @param str
     */
    void showToast(String str);

    /**
     * 定位成功回调
     * @param cityManage
     */
    void onLocationSuccess(CityManage cityManage);
}

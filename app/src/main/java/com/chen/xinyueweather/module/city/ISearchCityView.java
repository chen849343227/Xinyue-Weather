package com.chen.xinyueweather.module.city;

import com.chen.xinyueweather.dao.bean.City;

import java.util.List;

/**
 * @date Created:17-10-18
 * @author along
 * @Description
 */
public interface ISearchCityView {
    /**
     * 显示所有的数据
     * @param locations
     */
    void loadData(List<String> locations);
}

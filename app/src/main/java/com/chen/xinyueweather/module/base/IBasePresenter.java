package com.chen.xinyueweather.module.base;

/**
 * author long
 * date 17-10-10
 * desc
 */

public interface IBasePresenter {


    /**
     * 获取网络数据，更新界面
     * @param isRefresh 新增参数，用来判断是否为下拉刷新调用，下拉刷新的时候不应该再显示加载界面和异常界面
     */
    void getData(boolean isRefresh);

}
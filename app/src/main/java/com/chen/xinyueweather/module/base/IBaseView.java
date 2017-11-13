package com.chen.xinyueweather.module.base;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * author long
 * date 17-10-10
 * desc
 */

public interface IBaseView {

    /**
     * 显示网络错误
     */
    void showNetError();

    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();
}

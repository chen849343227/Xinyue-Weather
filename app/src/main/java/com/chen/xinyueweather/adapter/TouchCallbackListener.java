package com.chen.xinyueweather.adapter;

/**
 * Created by long on 17-5-12.
 */

public interface TouchCallbackListener {
    /**
     * 长按拖拽时的回调
     *
     * @param fromPosition 拖拽前的位置
     * @param toPosition   拖拽后的位置
     */
    void onItemMove(int fromPosition, int toPosition);

    /**
     * 滑动时的回调
     *
     * @param position 滑动的位置
     */
    void onItemSwipe(int position);
}

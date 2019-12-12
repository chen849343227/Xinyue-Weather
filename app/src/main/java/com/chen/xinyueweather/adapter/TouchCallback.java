package com.chen.xinyueweather.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

/**
 * Created by long on 17-5-12.
 */

public class TouchCallback extends ItemTouchHelper.Callback {

    /**
     * 自定义的监听接口
     */
    private TouchCallbackListener mListener;

    public TouchCallback(TouchCallbackListener listener) {
        this.mListener = listener;
    }

    /**
     * 定义列表可以怎么滑动（上下左右）
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //上下滑动
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //左右滑动
        int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        //使用此方法生成标志返回
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    /**
     * 拖拽移动时调用的方法
     *
     * @param recyclerView 控件
     * @param viewHolder   移动之前的条目
     * @param target       移动之后的条目
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 滑动时调用的方法
     *
     * @param viewHolder 滑动的条目
     * @param direction  方向
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListener.onItemSwipe(viewHolder.getAdapterPosition());
    }

    /**
     * 是否允许长按拖拽
     *
     * @return true or false
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * 是否允许滑动
     *
     * @return true or false
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }



}

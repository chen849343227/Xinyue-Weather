package com.chen.xinyueweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.xinyueweather.R;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.module.home.HomeActivity;
import com.chen.xinyueweather.widget.CircleTextView;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;

/**
 * @author along
 * @date Created:17-11-13
 * @Description
 */
public class CityManageAdapter extends RecyclerView.Adapter<CityManageAdapter.ViewHolder> {
    private static final String TAG = "CityManageAdapter";

    private LayoutInflater mInflater;

    private Context mContext;

    private List<CityManage> mList;

    public CityManageAdapter(Context context, List<CityManage> list) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        for (CityManage manage : list) {
            com.orhanobut.logger.Logger.e(manage.toString());
        }
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_location_manage, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.mCircleText.setText(mList.get(position).getAreaName().substring(0, 1));
            holder.mTvArea.setText(mList.get(position).getAreaName());
            if (!"".equals(mList.get(position).getWeather()) && mList.get(position).getWeather() != null) {
                holder.mTvType.setText(mList.get(position).getWeather());
            }
            if (!"".equals(mList.get(position).getTemperature()) && mList.get(position).getTemperature() != null) {
                holder.mTvTemp.setText(mList.get(position).getTemperature());
            }
            holder.mRelativeLayout.setOnClickListener(v -> {
                //String weatherId = mList.get(position).getWeatherId();
                HomeActivity.launch(mContext, position);
            });
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mRelativeLayout;
        CircleTextView mCircleText;
        TextView mTvArea;
        TextView mTvType;
        TextView mTvTemp;

        ViewHolder(View itemView) {
            super(itemView);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout);
            mCircleText = (CircleTextView) itemView.findViewById(R.id.circle_text);
            mTvArea = (TextView) itemView.findViewById(R.id.tv_area);
            mTvType = (TextView) itemView.findViewById(R.id.tv_type);
            mTvTemp = (TextView) itemView.findViewById(R.id.tv_temp);
        }
    }

}

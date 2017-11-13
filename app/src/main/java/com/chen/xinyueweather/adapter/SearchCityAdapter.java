package com.chen.xinyueweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chen.xinyueweather.R;
import com.chen.xinyueweather.dao.bean.City;
import com.chen.xinyueweather.widget.CircleTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date Created:17-10-18
 * @author along
 * @Description
 */
public class SearchCityAdapter extends BaseAdapter {

    private Context mContext;

    private List<City> mCityList;

    public SearchCityAdapter(Context context, List<City> mCityList) {
        this.mContext = context;
        this.mCityList = mCityList;
    }

    @Override
    public int getCount() {
        return mCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_location, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvArea.setText(mCityList.get(position).getAreaName());
        holder.mTvCity.setText(mCityList.get(position).getCityName());
        holder.mTvProvince.setText(mCityList.get(position).getProvinceName());
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_area)
        TextView mTvArea;
        @BindView(R.id.tv_city)
        TextView mTvCity;
        @BindView(R.id.tv_province)
        TextView mTvProvince;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

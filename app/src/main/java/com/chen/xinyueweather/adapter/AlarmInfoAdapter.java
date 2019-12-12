package com.chen.xinyueweather.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.xinyueweather.R;
import com.chen.xinyueweather.dao.bean.Alarm;
import com.orhanobut.logger.Logger;

import java.util.List;

public class AlarmInfoAdapter extends RecyclerView.Adapter<AlarmInfoAdapter.ViewHolder> {

    private static final String TAG = "AlarmInfoAdapter";
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Alarm> mList;

    public AlarmInfoAdapter(Context context, List<Alarm> list) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public AlarmInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.item_alarm_info, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmInfoAdapter.ViewHolder viewHolder, int i) {
        Logger.e("i = " + i);
        viewHolder.mAlarmTitle.setText(mList.get(i).getAlarmTypeDesc() + "预警");
        viewHolder.mAlarmContent.setText(mList.get(i).getAlarmContent());
        viewHolder.mAlarmStatus.setText("状态：预警中");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mAlarmTitle;
        TextView mAlarmContent;
        TextView mAlarmStatus;

        ViewHolder(View itemView) {
            super(itemView);
            mAlarmTitle = (TextView) itemView.findViewById(R.id.tv_alarm_title);
            mAlarmContent = (TextView) itemView.findViewById(R.id.tv_alarm_content);
            mAlarmStatus = (TextView) itemView.findViewById(R.id.tv_alarm_status);
        }
    }
}

package com.chen.xinyueweather.module.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chen.xinyueweather.R;
import com.chen.xinyueweather.adapter.AlarmInfoAdapter;
import com.chen.xinyueweather.dao.bean.Alarm;
import com.chen.xinyueweather.injector.components.DaggerAlarmInfoComponent;
import com.chen.xinyueweather.injector.modules.AlarmInfoModule;
import com.chen.xinyueweather.module.base.BaseActivity;
import com.chen.xinyueweather.module.base.IBasePresenter;
import com.chen.xinyueweather.module.city.CityManageActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AlarmInfoActivity extends BaseActivity<IBasePresenter> implements IAlarmInfoView {

    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_alarm_info)
    RecyclerView mAlarmView;

    List<Alarm> mList = new ArrayList<>();

    @Inject
    AlarmInfoAdapter mAdapter;

    public static void launch(Context context, List<Alarm> list) {
        Intent intent = new Intent(context, AlarmInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ALARM_INFO", new ArrayList<>(list));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_alarm_info;
    }

    @Override
    protected void initInjector() {
        DaggerAlarmInfoComponent.builder()
                .applicationComponent(getAppComponent())
                .alarmInfoModule(new AlarmInfoModule(this, mList))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        mToolbar.setTitle("");
        mTvToolbarTitle.setText("预警信息");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mAlarmView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAlarmView.setLayoutManager(layoutManager);

        ArrayList<Alarm> alarm = getIntent().getExtras().getParcelableArrayList("ALARM_INFO");
        Logger.e("size = " + alarm.size());
        mList.clear();
        mList.addAll(alarm);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(true);
    }
}

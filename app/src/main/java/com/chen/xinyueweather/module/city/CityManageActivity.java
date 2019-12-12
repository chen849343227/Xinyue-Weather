package com.chen.xinyueweather.module.city;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.R;
import com.chen.xinyueweather.adapter.CityManageAdapter;
import com.chen.xinyueweather.adapter.TouchCallback;
import com.chen.xinyueweather.adapter.TouchCallbackListener;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.injector.components.DaggerCityManageComponent;
import com.chen.xinyueweather.injector.modules.CityManageModule;
import com.chen.xinyueweather.module.base.BaseActivity;
import com.chen.xinyueweather.module.base.ILocalRxBusPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author along
 * @date Created:17-10-13
 * @Description 当前选择的城市的列表
 */
public class CityManageActivity extends BaseActivity<ILocalRxBusPresenter> implements ICityManageView, TouchCallbackListener {

    private static final String TAG = "CityManageActivity";

    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_noLocationData)
    TextView mTvNoLocationData;
    @BindView(R.id.recycler_view_location)
    RecyclerView mRecyclerViewLocation;
    @BindView(R.id.add_city_fab)
    FloatingActionButton mAddCityFab;
    @BindView(R.id.coordinator_Layout)
    CoordinatorLayout mCoordinatorLayout;

    @Inject
    CityManageAdapter mAdapter;

    private Intent mIntent;

    private List<String> delAreaIds = new ArrayList<>();

    private MyLinearLayoutManager layoutManager;

    /**
     * 启动CityManageActivity
     *
     * @param context
     */
    public static void launch(Context context) {
        Intent intent = new Intent(context, CityManageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 根据省市选择后启动当前Activity
     *
     * @param context
     * @param cityManage
     */
    public static void launch(Context context, CityManage cityManage) {
        Intent intent = new Intent(context, CityManageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("CITY", cityManage);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_city_manage;
    }

    @Override
    protected void initInjector() {
        DaggerCityManageComponent.builder()
                .applicationComponent(getAppComponent())
                .cityManageModule(new CityManageModule(CityManageActivity.this, AndroidApplication.mCityManages))
                .build()
                .inject(CityManageActivity.this);
    }

    @Override
    protected void initViews() {
        Log.e(TAG, "initViews");
        mToolbar.setTitle("");
        mTvToolbarTitle.setText("城市管理");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mRecyclerViewLocation.setAdapter(mAdapter);
        layoutManager = new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewLocation.setLayoutManager(layoutManager);
        ItemTouchHelper helper = new ItemTouchHelper(new TouchCallback(this));
        helper.attachToRecyclerView(mRecyclerViewLocation);
        mPresenter.registerRxBus(Integer.class, integer -> {
            if (integer == 0) {
                mTvNoLocationData.setVisibility(View.VISIBLE);
            } else {
                mTvNoLocationData.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        Log.e(TAG, "updateViews");
        mPresenter.getData(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            try {
                addCity(Objects.requireNonNull(intent.getExtras()).getParcelable("CITY"));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (layoutManager != null) {
            layoutManager = null;
        }
        mPresenter.unregisterRxBus();
    }

    @OnClick({R.id.add_city_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_city_fab:
                SearchCityActivity.launch(CityManageActivity.this, SearchCityActivity.REQUEST_CODE);
                break;
            default:
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SearchCityActivity.REQUEST_CODE:
                switch (resultCode) {
                    case 1:
                        addCity(Objects.requireNonNull(data.getExtras()).getParcelable("CITY_DATA"));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showData(List<CityManage> data) {
        Log.e(TAG, "showData");
        AndroidApplication.mCityManages.clear();
        AndroidApplication.mCityManages.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemSwipe(int position) {
        CityManage cityManage = AndroidApplication.mCityManages.get(position);
        mPresenter.delete(cityManage);
        if (AndroidApplication.sCurrentCity > AndroidApplication.mCityManages.size()) {
            AndroidApplication.sCurrentCity = AndroidApplication.mCityManages.size() - 1;
        }
        String temp = getResources().getString(R.string.activity_city_manage_tip_city_delete_success);
        delAreaIds.add(cityManage.getWeatherId());
        String hint = String.format(temp, cityManage.getAreaName());
        Snackbar.make(mCoordinatorLayout, hint, Snackbar.LENGTH_LONG)
                .setAction("撤销", v -> addCity(cityManage))
                .show();
        //通知Adapter更新
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 添加城市
     *
     * @param cityManage
     */
    private void addCity(CityManage cityManage) {
        for (int i = 0; i < AndroidApplication.mCityManages.size(); i++) {
            if (AndroidApplication.mCityManages.get(i).getAreaName().equals(cityManage.getAreaName())) {
                return;
            }
        }
        AndroidApplication.mCityManages.add(cityManage);
        AndroidApplication.sCurrentCity = AndroidApplication.mCityManages.size() - 1;
        mPresenter.insert(cityManage);
        delAreaIds.remove(cityManage.getWeatherId());
        mAdapter.notifyDataSetChanged();
    }

    private class MyLinearLayoutManager extends LinearLayoutManager {

        MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.chen.xinyueweather.module.city;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.R;
import com.chen.xinyueweather.adapter.ChooseLocationCityAdapter;
import com.chen.xinyueweather.adapter.SearchCityAdapter;
import com.chen.xinyueweather.dao.bean.City;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.injector.components.DaggerSearchCityComponent;
import com.chen.xinyueweather.injector.modules.SearchCityModule;
import com.chen.xinyueweather.module.base.BaseActivity;
import com.chen.xinyueweather.utils.LocationUtils;
import com.chen.xinyueweather.utils.PermissionUtils;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author along
 * @date Created:17-10-18
 * @Description 搜索城市界面
 */
public class SearchCityActivity extends BaseActivity<ISearchCityPresenter> implements ISearchCityView {

    public static final int REQUEST_CODE = 1;

    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.iv_locate)
    ImageView mIvLocate;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.lv_location)
    ListView mLvLocation;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_listState)
    TextView mTvListState;
    @BindView(R.id.tv_allCity)
    TextView mTvAllCity;
    @BindView(R.id.lv_location_search)
    ListView mLvLocationSearch;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;

    /**
     * 正在选择省
     */
    private static final int LEVEL_PROVINCE = 0;
    /**
     * 正在选择城市
     */
    private static final int LEVEL_CITY = 1;
    /**
     * 正在选择地区
     */
    private static final int LEVEL_AREA = 2;
    /**
     * 选中的省
     */
    private String selectProvince;

    /**
     * 选中的市
     */
    private String selectCity;
    /**
     * 当前选中的级别
     */
    private int currentLevel;
    /**
     * 用来存储当前选择的城市信息
     */
    private List<CityManage> mManageList = new ArrayList<>();

    /**
     * 用来储存当前本地地区的数据
     */
    private List<String> mLocationList = new ArrayList<>();

    Subscription subscription;

    @Inject
    SearchCityAdapter mSearchCityAdapter;
    @Inject
    ChooseLocationCityAdapter mChooseLocationCityAdapter;

    public static void launch(Context context, int requestCode) {
        Intent intent = new Intent(context, SearchCityActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_search_city;
    }

    @Override
    protected void initInjector() {
        DaggerSearchCityComponent.builder()
                .applicationComponent(getAppComponent())
                .searchCityModule(new SearchCityModule(this, mLocationList))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mLvLocationSearch.setAdapter(mSearchCityAdapter);
        mLvLocation.setAdapter(mChooseLocationCityAdapter);
        // 热门城市列表的点击事件
        mLvLocationSearch.setOnItemClickListener((parent, view, position, id) -> {
            CityManage cityManage = new CityManage();
            cityManage.setAreaName(AndroidApplication.sCityList.get(position).getAreaName());
            cityManage.setWeatherId(AndroidApplication.sCityList.get(position).getWeatherId());
            cityManage.setTemperature("");
            cityManage.setWeather("");
            setResultData(cityManage);
        });
        // 通过省市选择列表的点击事件
        mLvLocation.setOnItemClickListener((parent, view, position, id) -> {
            if (currentLevel == LEVEL_PROVINCE) {
                selectProvince = mLocationList.get(position);
                mPresenter.queryCitiesByProvinceName(selectProvince);
                currentLevel = LEVEL_CITY;
            } else if (currentLevel == LEVEL_CITY) {
                selectCity = mLocationList.get(position);
                mPresenter.queryAreasByCityName(selectCity);
                currentLevel = LEVEL_AREA;
            } else if (currentLevel == LEVEL_AREA) {
                City city = mPresenter.queryCityByArea(mLocationList.get(position));
                CityManage cityManage = new CityManage();
                cityManage.setAreaName(city.getAreaName());
                cityManage.setWeatherId(city.getWeatherId());
                cityManage.setTemperature("");
                cityManage.setWeather("");
                com.orhanobut.logger.Logger.e(city.getWeatherId());
                CityManageActivity.launch(SearchCityActivity.this, cityManage);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            //这里是对返回按钮进行处理
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
        subscription = RxTextView.textChanges(mEtSearch)
                .debounce(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())// 对[EditText]的监听操作 需要在主线程操作
                //对用户输入的关键字进行过滤
                .filter(charSequence -> {
                    if (charSequence.toString().trim().length() == 0) {
                        mTvListState.setText("热门城市");
                        AndroidApplication.sCityList.clear();
                        mPresenter.initHotCities();
                        mSearchCityAdapter.notifyDataSetChanged();
                    }
                    Log.d("RxJava", "filter is main thread : " + (Looper.getMainLooper() == Looper.myLooper()));
                    return charSequence.toString().trim().length() > 0;
                })
                .flatMap((Func1<CharSequence, Observable<List<City>>>) charSequence -> mPresenter.search(charSequence.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    mTvListState.setText("搜索结果");
                    AndroidApplication.sCityList.clear();
                    AndroidApplication.sCityList.addAll(cities);
                    mSearchCityAdapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);
    }


    /**
     * @param view
     */
    @OnClick({R.id.iv_locate, R.id.tv_allCity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_locate:
                //定位按钮
                getBaseLocation();
                break;
            case R.id.tv_allCity:
                //点击之后默认
                currentLevel = LEVEL_PROVINCE;
                //试图的切换
                mLlSearch.setVisibility(View.INVISIBLE);
                mLvLocation.setVisibility(View.VISIBLE);
                //隐藏输入法
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(SearchCityActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            default:
                break;
        }
    }

    @Override
    public void loadData(List<String> locations) {
        //更换数据
        mLocationList.clear();
        mLocationList.addAll(locations);
        mChooseLocationCityAdapter.notifyDataSetChanged();
        mLvLocation.setSelection(0);
    }

    @Override
    public void showToast(String str) {

    }

    @Override
    public void onLocationSuccess(CityManage cityManage) {
        CityManageActivity.launch(SearchCityActivity.this, cityManage);
    }

    /**
     * 设置activity回调的数据
     *
     * @param cityManage 选择的城市的数据
     */
    public void setResultData(CityManage cityManage) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("CITY_DATA", cityManage);
        intent.putExtras(bundle);
        setResult(1, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mLvLocation.getVisibility() == View.VISIBLE) {
            if (currentLevel == LEVEL_AREA) {
                mPresenter.queryCitiesByProvinceName(selectProvince);
                currentLevel = LEVEL_CITY;
            } else if (currentLevel == LEVEL_CITY) {
                mPresenter.queryProvinces();
                currentLevel = LEVEL_PROVINCE;
            } else if (currentLevel == LEVEL_PROVINCE) {
                mLlSearch.setVisibility(View.VISIBLE);
                mLvLocation.setVisibility(View.INVISIBLE);
            }
        } else {
            super.onBackPressed();
        }
    }

    private void getBaseLocation() {
        //request Permission
        PermissionUtils.requestPermission(SearchCityActivity.this);
        //Criteria类是设置定位的标准信息（系统会根据你的要求，匹配最适合你的定位供应商），一个定位的辅助信息的类
        Criteria c = new Criteria();
        //设置低耗电
        c.setPowerRequirement(Criteria.POWER_LOW);
        //设置需要海拔
        c.setAltitudeRequired(true);
        //设置COARSE精度标准
        c.setBearingAccuracy(Criteria.ACCURACY_COARSE);
        //设置低精度
        c.setAccuracy(Criteria.ACCURACY_LOW);
        Location best = LocationUtils.getBestLocation(SearchCityActivity.this, c);
        if (best == null) {
            Toast.makeText(this, " best location is null", Toast.LENGTH_SHORT).show();
        } else {
            String str = "http://maps.google.cn/maps/api/geocode/json?latlng=32.7763644055,100.4338731743&language=ZH";
            Logger.e("http://maps.google.cn/maps/api/geocode/json?latlng="+best.getLatitude()+"."+ best.getLongitude()+"&language=ZH");
            mPresenter.location(best.getLatitude() + "," + best.getLongitude());
        }
    }

    private void getNetworkLocation() {
        Location net = LocationUtils.getNetWorkLocation(this);
        if (net == null) {
            Toast.makeText(this, "net location is null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "network location: lat==" + net.getLatitude() + "  lng==" + net.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 通过GPS获取定位信息
     */
    public void getGPSLocation() {
        Location gps = LocationUtils.getGPSLocation(this);
        if (gps == null) {
            //设置定位监听，因为GPS定位，第一次进来可能获取不到，通过设置监听，可以在有效的时间范围内获取定位信息
            LocationUtils.addLocationListener(this, LocationManager.GPS_PROVIDER, new LocationUtils.ILocationListener() {
                @Override
                public void onSuccessLocation(Location location) {
                    if (location != null) {
                        Toast.makeText(SearchCityActivity.this, "gps onSuccessLocation location:  lat==" + location.getLatitude() + "     lng==" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SearchCityActivity.this, "gps location is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "gps location: lat==" + gps.getLatitude() + "  lng==" + gps.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.unRegisterListener(this);
    }
}

package com.chen.xinyueweather.module.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.xinyueweather.AndroidApplication;
import com.chen.xinyueweather.R;
import com.chen.xinyueweather.adapter.MyFragmentAdapter;
import com.chen.xinyueweather.module.base.BaseActivity;
import com.chen.xinyueweather.module.city.CityManageActivity;
import com.chen.xinyueweather.utils.AndroidShare;
import com.chen.xinyueweather.utils.PermissionUtils;
import com.chen.xinyueweather.widget.IndicatorView;
import com.chen.xinyueweather.widget.weather.SkyView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author along
 * @date create:17-10-10
 * @Description
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";

    @BindView(R.id.myWeatherView)
    SkyView mMyWeatherView;
    @BindView(R.id.tv_topTemp)
    TextView mTvTopTemp;
    @BindView(R.id.tv_topType)
    TextView mTvTopType;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigationView)
    NavigationView mNavigationView;
    @BindView(R.id.tv_topCity)
    TextView mTvTopCity;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.indicator_view)
    IndicatorView mIndicatorView;


    /*@BindView(R.id.text_view)
    TextView mTvCityCount;*/

    private List<ContentFragment> mFragments = new ArrayList<>();
    private FragmentPagerAdapter mPagerAdapter;


    public static void launch(Context context, int position) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("POSITION", position);
        context.startActivity(intent);
    }

    /**
     * 表示当前选中的item
     */
    private int mItemId = -1;
    private Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case R.id.nav_city_manage:
                CityManageActivity.launch(HomeActivity.this);
                break;
            case R.id.nav_skin:

                break;
            case R.id.nav_check_update:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_about:
                break;
            default:
                break;
        }
        mItemId = -1;
        return true;
    });

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initDrawerLayout(mDrawerLayout, mNavigationView);
        mNavigationView.setItemIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)));
        PermissionUtils.requestPermission(this);
        //  mMyWeatherView.
    }


    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AndroidApplication.sCurrentCity < 0) {
            getCityIndex();
        }
        if (AndroidApplication.mCityManages.size() != 0) {
            try {
                if (AndroidApplication.sCurrentCity >= AndroidApplication.mCityManages.size()) {
                    AndroidApplication.sCurrentCity = AndroidApplication.mCityManages.size() - 1;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                mTvTopCity.setText(AndroidApplication.mCityManages.get(AndroidApplication.sCurrentCity).getAreaName());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            mTvTopCity.setText("");
        }
        mFragments.clear();
        for (int i = 0; i < AndroidApplication.mCityManages.size(); i++) {
            mFragments.add(ContentFragment.newInstance(AndroidApplication.mCityManages.get(i).getWeatherId()));
        }
        mPagerAdapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(AndroidApplication.sCurrentCity);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                AndroidApplication.sCurrentCity = position;
                mTvTopCity.setText(AndroidApplication.mCityManages.get(position).getAreaName());
            }
        });
        mIndicatorView.setCount(AndroidApplication.mCityManages.size());
        mIndicatorView.addOnPageChangeListener(mViewPager, true);

        //mTvCityCount.setText(AndroidApplication.mCityManages.size()+"");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            AndroidApplication.sCurrentCity = intent.getIntExtra("POSITION", -1);
        }
        Log.e(TAG, "onNewIntent");
    }

    /**
     * 菜单按钮的操作
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:

                break;
            case R.id.action_share:
                AndroidShare share = new AndroidShare(this);
                share.share();
                break;
            case R.id.action_preview:
                showPreview();
                break;
            case R.id.action_exit:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 点击选项
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mItemId = item.getItemId();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mToolbar.inflateMenu(R.menu.menu_main);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 初始化DrawerLayout
     *
     * @param mDrawerLayout
     * @param navView
     */
    private void initDrawerLayout(DrawerLayout mDrawerLayout, NavigationView navView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //设置状态栏为透明的
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                mHandler.sendEmptyMessage(mItemId);
            }
        });
        navView.setNavigationItemSelectedListener(this);
    }


    private void showPreview() {
        final ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<>(this, R.layout.item_preview
        );
        arrayAdapter.add("晴");
        arrayAdapter.add("多云");
        arrayAdapter.add("阴");
        arrayAdapter.add("雾");
        arrayAdapter.add("雨");
        arrayAdapter.add("雨夹雪");
        arrayAdapter.add("雪");
        arrayAdapter.add("霾");

        ListView listView = new ListView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(layoutParams);
        listView.setDividerHeight(1);
        listView.setAdapter(arrayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(listView)
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            mMyWeatherView.setWeather(parent.getAdapter().getItem(position).toString());
            dialog.dismiss();
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = this.getSharedPreferences("cityIndex", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("index", AndroidApplication.sCurrentCity);
        editor.commit();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void getCityIndex() {
        //获取退出时app选中的城市的position
        SharedPreferences sharedPreferences = this.getSharedPreferences("cityIndex", MODE_PRIVATE);
        AndroidApplication.sCurrentCity = sharedPreferences.getInt("index", 0);
    }
}

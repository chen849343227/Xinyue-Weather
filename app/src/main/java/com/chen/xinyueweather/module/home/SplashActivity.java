package com.chen.xinyueweather.module.home;

import android.os.Bundle;

import com.chen.xinyueweather.R;
import com.chen.xinyueweather.module.base.BaseActivity;
import com.chen.xinyueweather.utils.RxHelper;

/**
 * author long
 * date 17-10-9
 * desc
 */

public class SplashActivity extends BaseActivity {


    @Override
    protected int attachLayoutRes() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        //RxHelper.countdown(5).doOnNext()

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

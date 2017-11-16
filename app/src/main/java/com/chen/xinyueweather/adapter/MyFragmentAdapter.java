package com.chen.xinyueweather.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.chen.xinyueweather.module.home.ContentFragment;

import java.util.List;

/**
 * @date Created:17-10-18
 * @author along
 * @Description
 */
public class MyFragmentAdapter extends FragmentPagerAdapter  {

    private Context mContext;

    private List<ContentFragment> mFragments;

    public MyFragmentAdapter(FragmentManager fm, List<ContentFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

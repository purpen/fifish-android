package com.qiyuan.fifish.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author lilin
 *         created at 2016/8/8 13:03
 */
public class TabLayoutViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private String[] titles;

    public TabLayoutViewPagerAdapter(FragmentManager manager, Fragment[] fragments, String[] titles) {
        super(manager);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

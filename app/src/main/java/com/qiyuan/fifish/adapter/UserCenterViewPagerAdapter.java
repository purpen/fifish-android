package com.qiyuan.fifish.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import com.qiyuan.fifish.interfaces.ScrollTabHolder;

/**
 * @author lilin
 * created at 2016/8/8 13:03
 */
public class UserCenterViewPagerAdapter extends FragmentPagerAdapter{
    private Fragment[] fragments;
    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    public UserCenterViewPagerAdapter(FragmentManager manager, Fragment[] fragments){
        super(manager);
        this.fragments=fragments;
    }
    @Override
    public int getCount() {

        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }
}

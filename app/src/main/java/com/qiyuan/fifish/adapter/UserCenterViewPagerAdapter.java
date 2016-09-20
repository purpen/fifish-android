package com.qiyuan.fifish.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import com.qiyuan.fifish.interfaces.ScrollTabHolder;
import com.qiyuan.fifish.ui.fragment.ScrollTabHolderFragment;

/**
 * @author lilin
 * created at 2016/8/8 13:03
 */
public class UserCenterViewPagerAdapter extends FragmentPagerAdapter{
    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    private ScrollTabHolder mListener;
    private Fragment[] fragments;
    public UserCenterViewPagerAdapter(FragmentManager manager, Fragment[] fragments){
        super(manager);
        this.fragments=fragments;
        mScrollTabHolders = new SparseArrayCompat<>();
    }
    @Override
    public int getCount() {
        return fragments.length;
    }
    public void setTabHolderScrollingContent(ScrollTabHolder listener) {
        mListener = listener;
    }
    @Override
    public Fragment getItem(int position) {
        ScrollTabHolderFragment fragment = (ScrollTabHolderFragment)fragments[position];
        mScrollTabHolders.put(position, fragment);
        if (mListener != null) {
            fragment.setScrollTabHolder(mListener);
        }
        return fragment;
    }
    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }
}

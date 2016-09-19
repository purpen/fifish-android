package com.qiyuan.fifish.ui.fragment;

import com.qiyuan.fifish.interfaces.ScrollTabHolder;

/**
 * @author lilin
 * created at 2016/9/9 11:40
 */
public abstract class ScrollTabHolderFragment extends BaseFragment implements ScrollTabHolder{
    protected ScrollTabHolder mScrollTabHolder;
    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }
}

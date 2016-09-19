package com.qiyuan.fifish.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by Amy on 2016/8/8.
 */
public class UserCenterViewPager extends ViewPager{
    private boolean isPagingEnabled = true;
    public UserCenterViewPager(Context context) {
        super(context);
    }

    public UserCenterViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }

    public void setViewPagerHeight(int height){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height=height;
        requestLayout();
    }
}

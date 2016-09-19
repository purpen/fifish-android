package com.qiyuan.fifish.interfaces;

import android.widget.AbsListView;

/**
 * Created by Amy on 2016/9/9.
 */
public interface ScrollTabHolder {
    void adjustScroll(int scrollHeight);

    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}

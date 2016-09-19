package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomViewPager;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * @author lilin
 *         created at 2016/8/8 11:22
 */
public class ProductsFragment extends ScrollTabHolderFragment {
    @Bind(R.id.listView)
    ListView listView;
    private CustomViewPager viewPager;
    private ArrayList<String> mList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_list);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            mList.add(i + ". item - currnet page: " + (0 + 1));
        }
        View placeHolderView = Util.inflateView(R.layout.view_header_placeholder, null);
        listView.addHeaderView(placeHolderView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnScrollListener(new OnScroll());
        adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, android.R.id.text1, mList);
        listView.setAdapter(adapter);
    }

    private int lastVisibleItem = 0;

    public class OnScroll implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            int position = listView.getLastVisiblePosition();
            if (lastVisibleItem != position && position == (listView.getCount() - 1)) {
                lastVisibleItem = position;
                Log.e("mListView.getCount()-1", "底部");
                mList.addAll(mList);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, 0);
        }

    }

    @Override
    protected void installListener() {

    }

    @Override
    protected void requestNet() {

    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && listView.getFirstVisiblePosition() >= 1) {
            return;
        }

        listView.setSelectionFromTop(1, scrollHeight);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {

    }

    @Override
    protected void refreshUI(ArrayList list) {
        if (list == null || list.size() == 0) return;

    }

}

package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.activity.UserCenterActivity;
import com.qiyuan.fifish.ui.view.UserCenterViewPager;
import com.qiyuan.fifish.util.Util;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/8/8 11:22
 */
public class ProductsFragment extends BaseFragment {
    @Bind(R.id.pull_refresh_view)
    PullToRefreshListView pullRefreshView;
    private UserCenterViewPager viewPager;
    private ArrayList<String> mlist=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_products);
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    protected void initViews() {
        pullRefreshView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    protected void installListener() {
        pullRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        pullRefreshView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                loadData();
            }
        });

        pullRefreshView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewPager.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                int action = motionEvent.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        viewPager.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void requestNet() {
        new MyHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshUI(loadData());
            }
        }, 2000);
    }

    private ArrayList<String> loadData() {
        LogUtil.e("加载了数据");
        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i <10; i++) {
            data.add("ListView数据的分批加载" + i);
        }
        return data;

    }
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void refreshUI(ArrayList list) {
        if (list==null||list.size()==0) return;
        mlist.addAll(list);
        if (arrayAdapter==null){
            arrayAdapter = new ArrayAdapter<>(
                    activity, android.R.layout.simple_list_item_1,
                    mlist);
            pullRefreshView.setAdapter(arrayAdapter);
        }else {
            arrayAdapter.notifyDataSetChanged();
        }
        int height = Util.setListViewHeightBasedOnChildren1(pullRefreshView.getRefreshableView());
        if (activity instanceof UserCenterActivity) {
            viewPager = ((UserCenterActivity) activity).getViewPager();
            if (viewPager!=null){
                viewPager.setViewPagerHeight(height);
            }
        }
    }

    private static class MyHandler extends Handler{}
}

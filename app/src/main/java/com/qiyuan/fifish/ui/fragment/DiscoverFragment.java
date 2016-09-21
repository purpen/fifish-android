package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.HomeAdapter;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.ScrollableView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DiscoverFragment extends BaseFragment {
    @Bind(R.id.custom_head)
    CustomHeadView customHead;
    @Bind(R.id.pull_lv)
    PullToRefreshListView pullLv;
    private RecyclerView recyclerViewTag;
    private RecyclerView recyclerViewUser;
    private ScrollableView scrollableView;
    private int curPage = 1;
    private ArrayList<ProductsBean.DataBean> mList;
    private HomeAdapter adapter;
    private View headView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_discover);
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true, R.string.discover);
        customHead.setHeadGoBackShow(false);
        customHead.setIvLeft(R.mipmap.search_head);
        headView = Util.inflateView(R.layout.view_discover_headview, null);
        pullLv.getRefreshableView().addHeaderView(headView);
        scrollableView = ButterKnife.findById(headView, R.id.scrollableView);
        recyclerViewTag = ButterKnife.findById(headView, R.id.recycler_view_tag);
        recyclerViewUser = ButterKnife.findById(headView, R.id.recycler_view_user);
        mList = new ArrayList<>();
    }

    @Override
    protected void requestNet() {
        RequestService.getProducts(String.valueOf(curPage), Constants.PAGE_SIZE, null, null, "0", new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                ProductsBean productsBean = JsonUtil.fromJson(result, ProductsBean.class);
                if (productsBean.meta.status_code == Constants.HTTP_OK) {
                    ArrayList<ProductsBean.DataBean> list = productsBean.data;
                    refreshUI(list);
                    return;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });

        RequestService.getProducts(String.valueOf(curPage), Constants.PAGE_SIZE, null, null, "0", new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                ProductsBean productsBean = JsonUtil.fromJson(result, ProductsBean.class);
                if (productsBean.meta.status_code == Constants.HTTP_OK) {
                    ArrayList<ProductsBean.DataBean> list = productsBean.data;
                    refreshUI(list);
                    return;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    @Override
    protected void refreshUI(ArrayList list) {
        if (list == null || list.size() == 0) return;
        curPage++;
        mList.addAll(list);
        if (adapter == null) {
            adapter = new HomeAdapter(mList, activity);
            pullLv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

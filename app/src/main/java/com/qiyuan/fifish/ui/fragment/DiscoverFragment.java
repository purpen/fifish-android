package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.HotTagRecycleViewAdapter;
import com.qiyuan.fifish.adapter.HotUserRecycleViewAdapter;
import com.qiyuan.fifish.adapter.RecommendProductsAdapter;
import com.qiyuan.fifish.adapter.ViewPagerAdapter;
import com.qiyuan.fifish.bean.BannersBean;
import com.qiyuan.fifish.bean.HotUserBean;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.TagsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.RecycleViewDivider;
import com.qiyuan.fifish.ui.view.ScrollableView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.custom_head)
    CustomHeadView customHead;
    @BindView(R.id.pull_lv)
    PullToRefreshListView pullLv;
    private RecyclerView recyclerViewTag;
    private RecyclerView recyclerViewUser;
    private ScrollableView scrollableView;
    private int curPage = 1;
    private ArrayList<ProductsBean.DataEntity> mList;
    private RecommendProductsAdapter adapter;
    private View headView;
    private ArrayList<String> bannerList;
    private ArrayList<TagsBean.DataBean> tagList;
    private ArrayList<HotUserBean.DataBean> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_discover);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true, R.string.discover);
        customHead.setHeadGoBackShow(false);
        customHead.setIvLeft(R.mipmap.search_head);
        headView = Util.inflateView(R.layout.view_discover_headview, null);
        scrollableView = ButterKnife.findById(headView, R.id.scrollableView);
        recyclerViewTag = ButterKnife.findById(headView, R.id.recycler_view_tag);
        recyclerViewUser = ButterKnife.findById(headView, R.id.recycler_view_user);
        mList = new ArrayList<>();
        bannerList = new ArrayList<>();
        tagList = new ArrayList<>();
        userList = new ArrayList<>();
        pullLv.getRefreshableView().addHeaderView(headView);
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(R.mipmap.guide0);
//        list.add(R.mipmap.guide1);
//        list.add(R.mipmap.guide2);
//        list.add(R.mipmap.guide3);
//        scrollableView.setAdapter(new ViewPagerAdapter<>(activity, list).setInfiniteLoop(true));
//        scrollableView.setAutoScrollDurationFactor(8);
//        scrollableView.showIndicators();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (scrollableView != null) scrollableView.stop();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (scrollableView != null) scrollableView.start();
    }

    @Override
    protected void installListener() {
        pullLv.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
    }

    @Override
    protected void requestNet() {
        RequestService.getBanners("1", "6", "app_discover_slide", new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                bannerList.clear();
                BannersBean bannersBean = JsonUtil.fromJson(result, BannersBean.class);
                if (bannersBean.meta.status_code == Constants.HTTP_OK) {
                    for (BannersBean.DataEntity dataEntity : bannersBean.data) {
                        bannerList.add(dataEntity.cover.file.large);
                    }
                    scrollableView.setAdapter(new ViewPagerAdapter<>(activity, bannerList).setInfiniteLoop(true));
                    scrollableView.setAutoScrollDurationFactor(8);
                    scrollableView.showIndicators();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
        RequestService.getHotTags(new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                TagsBean tagsBean = JsonUtil.fromJson(result, TagsBean.class);
                if (tagsBean.meta.status_code == Constants.HTTP_OK) {
                    tagList.clear();
                    tagList = tagsBean.data;
                    LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewTag.setHasFixedSize(true);
                    recyclerViewTag.setLayoutManager(manager);
                    recyclerViewTag.addItemDecoration(new RecycleViewDivider(activity, RecycleViewDivider.HORIZONTAL_LIST, R.drawable.divider_10dp));
                    recyclerViewTag.setAdapter(new HotTagRecycleViewAdapter(activity, tagList));
                    return;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });


        RequestService.getHotUsers(new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                HotUserBean hotUserBean = JsonUtil.fromJson(result, HotUserBean.class);
                if (hotUserBean.meta.status_code == Constants.HTTP_OK) {
                    userList.clear();
                    userList = hotUserBean.data;
                    LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewUser.setHasFixedSize(true);
                    recyclerViewUser.setLayoutManager(manager);
                    recyclerViewUser.addItemDecoration(new RecycleViewDivider(activity, RecycleViewDivider.HORIZONTAL_LIST, R.drawable.divider_10dp));
                    recyclerViewUser.setAdapter(new HotUserRecycleViewAdapter(activity, userList));
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
                    ArrayList<ProductsBean.DataEntity> list = productsBean.data;
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
            adapter = new RecommendProductsAdapter(mList, activity);
            pullLv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

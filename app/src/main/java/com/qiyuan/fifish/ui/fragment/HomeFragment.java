package com.qiyuan.fifish.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.HomeAdapter;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.CommentsDetailActivity;
import com.qiyuan.fifish.ui.activity.SearchActivity;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
public class HomeFragment extends BaseFragment {
    @BindView(R.id.custom_head)
    CustomHeadView customHead;
    @BindView(R.id.pull_lv)
    PullToRefreshListView pullLv;
    private int curPage = 1;
    private ArrayList<ProductsBean.DataEntity> mList;
    private HomeAdapter adapter;
    private WaitingDialog dialog;
    private boolean isLoadMore;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_home);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true, R.string.home);
        customHead.setHeadGoBackShow(false);
        customHead.setIvLeft(R.mipmap.search_head);
        dialog=new WaitingDialog(activity);
        mList=new ArrayList<>();
    }

    @Override
    protected void installListener() {
        customHead.getIvLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });
        pullLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductsBean.DataEntity item = adapter.getItem(i-1);
                Intent intent=new Intent(activity, CommentsDetailActivity.class);
                intent.putExtra(CommentsDetailActivity.class.getSimpleName(),item);
                startActivity(intent);
            }
        });
        pullLv.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        pullLv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                isLoadMore = true;
                requestNet();
            }
        });
        pullLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                isLoadMore = true;
                mList.clear();
                curPage=1;
                requestNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    @Override
    protected void requestNet() {
        RequestService.getProducts(String.valueOf(curPage), Constants.PAGE_SIZE, null, null,"0",new CustomCallBack() {
            @Override
            public void onStarted() {
                if (!isLoadMore&&!activity.isFinishing()&& dialog != null) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                if (!activity.isFinishing()&& dialog != null) dialog.dismiss();
                if (pullLv!=null) pullLv.onRefreshComplete();
                ProductsBean productsBean = JsonUtil.fromJson(result, ProductsBean.class);
                if (productsBean.meta.status_code == Constants.HTTP_OK) {
                    ArrayList<ProductsBean.DataEntity> list = productsBean.data;
                    refreshUI(list);
                    return;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!activity.isFinishing()&& dialog != null) dialog.dismiss();
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }
    @Override
    protected void refreshUI(ArrayList list) {
        if (list == null || list.size() == 0) return;
        LogUtil.e("refreshUI==="+list.size());
        curPage++;
        mList.addAll(list);
        LogUtil.e("mList==="+mList.size());
        if (adapter == null) {
            adapter = new HomeAdapter(mList,activity);
            pullLv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

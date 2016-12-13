package com.qiyuan.fifish.ui.fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ProductsAdapter;
import com.qiyuan.fifish.adapter.ProductsGridAdapter;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/8/8 11:22
 */
public class ProductsFragment extends ScrollTabHolderFragment implements View.OnClickListener{
    TextView tvCount;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.gridListView)
    ListView gridListView;
    private ArrayList<ProductsBean.DataEntity> mList;
    private ProductsAdapter adapter;
    private ProductsGridAdapter gridAdapter;
    public static final String POSITION = "position";
    public static final String ID = "id";
    private int mPosition;
    private String id;
    private int curPage = 1;
    private boolean showList=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mPosition = bundle.getInt(POSITION);
        id = bundle.getString(ID);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_my_products);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static ProductsFragment newInstance(int position, String id) {
        ProductsFragment f = new ProductsFragment();
        Bundle b = new Bundle();
        b.putInt(POSITION, position);
        b.putString(ID, id);
        f.setArguments(b);
        return f;
    }

    @Override
    protected void initViews() {
        View placeHolderView = Util.inflateView(activity,R.layout.view_header_placeholder, null);
        ButterKnife.findById(placeHolderView,R.id.rl).setVisibility(View.VISIBLE);
        tvCount = ButterKnife.findById(placeHolderView, R.id.tv_count);
        ButterKnife.findById(placeHolderView, R.id.ibtn).setOnClickListener(this);
        listView.addHeaderView(placeHolderView);
        gridListView.addHeaderView(placeHolderView);
        mList = new ArrayList<>();
        adapter=new ProductsAdapter(mList,activity,UserProfile.getUserId());
        gridAdapter=new ProductsGridAdapter(mList,activity,UserProfile.getUserId());
        listView.setAdapter(adapter);
        gridListView.setAdapter(gridAdapter);
    }
    private int lastVisibleItem = 0;

    private class OnScroll implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            int position = listView.getLastVisiblePosition();
            if (lastVisibleItem != position && position == (listView.getCount() - 1)) {
                lastVisibleItem = position;
               requestNet();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
        }

    }

    @Override
    protected void installListener() {
        listView.setOnScrollListener(new OnScroll());
        gridListView.setOnScrollListener(new OnScroll());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_count:
                if (showList){
                    showList=false;
                    tvCount.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.show_grid,0);
                    listView.setVisibility(View.GONE);
                    gridListView.setVisibility(View.VISIBLE);
                }else {
                    showList=true;
                    tvCount.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.show_list,0);
                    gridListView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void requestNet() {
        if (TextUtils.isEmpty(id)) return;
        RequestService.getProducts(String.valueOf(curPage), Constants.PAGE_SIZE, null, id,null, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                ProductsBean productsBean = JsonUtil.fromJson(result, ProductsBean.class);
                if (productsBean.meta.status_code == Constants.HTTP_OK) {
                    List<ProductsBean.DataEntity> list = productsBean.data;
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
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && listView.getFirstVisiblePosition() >= 1) {
            return;
        }
        listView.setSelectionFromTop(1, scrollHeight);
        listView.setSelectionFromTop(1,scrollHeight);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {

    }

    @Override
    protected void refreshUI(List list) {
        if (list == null || list.size() == 0) return;
        curPage++;
        mList.addAll(list);
        //列表
        if (adapter == null) {
            adapter = new ProductsAdapter(mList,activity, UserProfile.getUserId());
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        //网格
        if (gridAdapter == null) {
            gridAdapter = new ProductsGridAdapter(mList,activity, UserProfile.getUserId());
            listView.setAdapter(gridAdapter);
        } else {
            gridAdapter.notifyDataSetChanged();
        }

    }

}

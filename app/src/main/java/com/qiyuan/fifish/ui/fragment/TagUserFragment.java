package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.HomeAdapter;
import com.qiyuan.fifish.adapter.SearchProductsAdapter;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.SearchProductsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.SearchActivity;
import com.qiyuan.fifish.ui.activity.TagActivity;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author lilin
 *         created at 2016/10/11 14:52
 */
public class TagUserFragment extends BaseFragment {
    @BindView(R.id.pull_lv)
    PullToRefreshListView pullLv;
    private int curPage = 1;
    private ArrayList<ProductsBean.DataEntity> mList;
    private HomeAdapter adapter;
    private String tag;
    private String evt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) tag = bundle.getString(TagActivity.class.getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.pull_refresh_list);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static TagUserFragment newInstance(Bundle bundle) {
        TagUserFragment fragment = new TagUserFragment();
        if (bundle != null) fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
//        adapter = new SearchProductsAdapter(mList, activity);
//        pullLv.setAdapter(adapter);
    }

    @Override
    protected void installListener() {
        pullLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ProductsBean.DataBean item = adapter.getItem(i-1);
//                Intent intent=new Intent(activity, CommentsDetailActivity.class);
//                intent.putExtra(CommentsDetailActivity.class.getSimpleName(),item);
//                startActivity(intent);
            }
        });

        pullLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        pullLv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                //加载更多
            }
        });
    }

    @Override
    protected void requestNet() {
        if (TextUtils.isEmpty(tag)) return;
//        RequestService.searchInSite(String.valueOf(curPage), tag, new CustomCallBack() {
//            @Override
//            public void onSuccess(String result) {
//                if (TextUtils.isEmpty(result)) return;
////                SearchProductsBean searchProductsBean = JsonUtil.fromJson(result, SearchProductsBean.class);
////                if (searchProductsBean.meta.status_code == Constants.HTTP_OK) {
////                    List<SearchProductsBean.DataBean> list = searchProductsBean.data;
////                    refreshUI(list);
////                    return;
////                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ex.printStackTrace();
//                ToastUtils.showError(R.string.request_error);
//            }
//        });
    }

    @Override
    protected void refreshUI(List list) {
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
}

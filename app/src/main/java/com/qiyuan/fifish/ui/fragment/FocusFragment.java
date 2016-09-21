package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.FocusAdapter;
import com.qiyuan.fifish.bean.FocusBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * @author lilin
 *         created at 2016/8/8 11:22
 */
public class FocusFragment extends ScrollTabHolderFragment {
    @Bind(R.id.listView)
    ListView listView;
    private ArrayList<FocusBean.DataBean> mList;
    private FocusAdapter adapter;
    private int mPosition;
    private String id;
    private int curPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mPosition = bundle.getInt(ProductsFragment.POSITION);
        id = bundle.getString(ProductsFragment.ID);
        LogUtil.e("id==" + id);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_list);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static FocusFragment newInstance(int position, String id) {
        FocusFragment f = new FocusFragment();
        Bundle b = new Bundle();
        b.putInt(ProductsFragment.POSITION, position);
        b.putString(ProductsFragment.ID, id);
        f.setArguments(b);
        return f;
    }

    @Override
    protected void initViews() {
        View placeHolderView = Util.inflateView(R.layout.view_header_placeholder, null);
        listView.addHeaderView(placeHolderView);
        mList = new ArrayList<>();
//        for (int i = 1; i <= 50; i++) {
//            mList.add(i + ". item - currnet page: " + (0 + 1));
//        }
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        listView.setOnScrollListener(new OnScroll());
//    }

    @Override
    protected void requestNet() {
        if (TextUtils.isEmpty(id)) return;
        RequestService.getFocus(id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                FocusBean focusBean = JsonUtil.fromJson(result, FocusBean.class);
                if (focusBean.meta.status_code == Constants.HTTP_OK) {
                    ArrayList<FocusBean.DataBean> list = focusBean.data;
                    refreshUI(list);
                    return;
                }
                ToastUtils.showError(focusBean.meta.message);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    private int lastVisibleItem = 0;

    private class OnScroll implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            int position = listView.getLastVisiblePosition();
            if (lastVisibleItem != position && position == (listView.getCount() - 1)) {
                lastVisibleItem = position;
                Log.e("mListView.getCount()-1", "底部");
//                mList.addAll(mList);
//                adapter.notifyDataSetChanged();
                refreshUI(mList);
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
        curPage++;
        mList.addAll(list);
        if (adapter == null) {
            adapter = new FocusAdapter(mList, activity, id);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

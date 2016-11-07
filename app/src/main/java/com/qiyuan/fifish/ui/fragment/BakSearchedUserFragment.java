package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.FocusAdapter;
import com.qiyuan.fifish.bean.FocusBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author lilin
 * created at 2016/8/8 11:22
 */
public class BakSearchedUserFragment extends BaseFragment {
    @BindView(R.id.pull_lv)
    PullToRefreshListView pull_lv;
    private ArrayList<FocusBean.DataBean> mList;
    private FocusAdapter adapter;
    private String id;
    private int curPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        id = bundle.getString(ProductsFragment.ID);
        LogUtil.e("id==" + id);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.pull_refresh_list);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static BakSearchedUserFragment newInstance(int position, String id) {
        BakSearchedUserFragment f = new BakSearchedUserFragment();
        Bundle b = new Bundle();
        b.putInt(ProductsFragment.POSITION, position);
        b.putString(ProductsFragment.ID, id);
        f.setArguments(b);
        return f;
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
    }

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

    @Override
    protected void installListener() {

    }
    @Override
    protected void refreshUI(List list) {
        if (list == null || list.size() == 0) return;
        curPage++;
        mList.addAll(list);
        if (adapter == null) {
            adapter = new FocusAdapter(mList, activity, id);
            pull_lv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

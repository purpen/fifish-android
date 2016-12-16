package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.SupportVideoAdapter;
import com.qiyuan.fifish.bean.SupportPhotoBean;
import com.qiyuan.fifish.bean.SupportVideoBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/8/10 17:24
 */
public class SupportVideoFragment extends BaseFragment {
    public int curPage = 1;
    @BindView(R.id.pull_lv)
    PullToRefreshListView pullLv;
    private ArrayList<SupportVideoBean> mList;
    private SupportVideoAdapter adapter;
    private WaitingDialog dialog;
    private boolean isLoadMore = false;

    public static SupportVideoFragment newInstance() {
        return new SupportVideoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_support_video);
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initViews() {
        pullLv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = new ArrayList<>();
        dialog = new WaitingDialog(activity);
    }

    @Override
    protected void installListener() {
        pullLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isLoadMore = true;
                resetData();
                requestNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        pullLv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                isLoadMore = true;
                requestNet();
            }
        });

        pullLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0) {
//                    DataChooseSubject.ItemChoosenSubject item = mList.get(position - 1);
//                    Intent intent = new Intent(activity, ArticalDetailActivity.class);
//                    intent.putExtra(ArticalDetailActivity.class.getSimpleName(), item._id);
//                    startActivity(intent);
                }
            }
        });
    }

    private void resetData() {
        curPage = 1;
        mList.clear();
    }

    @Override
    protected void requestNet() {
        RequestService.getSupportProducts("2", new CustomCallBack() {
            @Override
            public void onStarted() {
                if (!isLoadMore && dialog != null && !activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                if (pullLv != null) pullLv.onRefreshComplete();
                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
                SupportPhotoBean response = JsonUtil.fromJson(result, SupportPhotoBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    List<SupportPhotoBean.DataEntity> data = response.data;
                    refreshUI(data);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
                ToastUtils.showError(R.string.request_error);
                ex.printStackTrace();
            }
        });
    }

    @Override
    protected void refreshUI(List list) {
        if (list == null || list.size() == 0) return;
        curPage++;
        mList.addAll(list);
        if (adapter == null) {
            adapter = new SupportVideoAdapter(mList, activity);
            pullLv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.SupportPhotoAdapter;
import com.qiyuan.fifish.bean.SupportPhotoBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author lilin
 *         created at 2016/8/10 17:24
 */
public class SupportPhotoFragment extends BaseFragment {
    public int curPage = 1;
    @BindView(R.id.pull_gv)
    PullToRefreshGridView pullGv;
    private ArrayList<SupportPhotoBean.DataEntity> mList;
    private SupportPhotoAdapter adapter;
    private WaitingDialog dialog;
    private boolean isLoadMore = false;

    public static SupportPhotoFragment newInstance() {
        return new SupportPhotoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_support_photo);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initViews() {
        dialog = new WaitingDialog(activity);
        pullGv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = new ArrayList<>();
        adapter=new SupportPhotoAdapter(mList,activity);
        pullGv.setAdapter(adapter);
    }

    @Override
    protected void installListener() {
        pullGv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                isLoadMore = true;
                resetData();
                requestNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

            }
        });
        pullGv.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        pullGv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                isLoadMore = true;
                requestNet();
            }
        });

        pullGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        RequestService.getSupportProducts("1", new CustomCallBack() {
            @Override
            public void onStarted() {
                if (!isLoadMore&&dialog != null && !activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                if (pullGv!=null) pullGv.onRefreshComplete();
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
        if (list.size() == 0) return;
        curPage++;
        mList.addAll(list);
        if (adapter == null) {
            adapter = new SupportPhotoAdapter(mList, activity);
            pullGv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

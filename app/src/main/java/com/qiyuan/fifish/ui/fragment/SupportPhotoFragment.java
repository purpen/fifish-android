package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.SupportPhotoAdapter;
import com.qiyuan.fifish.bean.SupportPhotoBean;
import com.qiyuan.fifish.ui.view.WaitingDialog;

import java.util.ArrayList;

/**
 * @author lilin
 *         created at 2016/8/10 17:24
 */
public class SupportPhotoFragment extends BaseFragment {
    public int curPage = 1;
    @BindView(R.id.pull_gv)
    PullToRefreshGridView pullGv;
    private ArrayList<SupportPhotoBean> mList;
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
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initViews() {
        pullGv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = new ArrayList<>();
        dialog = new WaitingDialog(activity);
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

    }

    @Override
    protected void refreshUI(ArrayList list) {
        if (list == null) return;
        if (list.size() == 0) {
            if (mList.size() > 0) {
//                ToastUtils.showInfo("没有更多数据哦");
            } else {
//                ToastUtils.showInfo("您还没有订阅的情境");
            }
            return;
        }
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

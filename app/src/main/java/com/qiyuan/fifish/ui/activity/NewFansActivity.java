package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.BindView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.FansAdapter;
import com.qiyuan.fifish.bean.CommentsBean;
import com.qiyuan.fifish.bean.FocusBean;
import com.qiyuan.fifish.bean.SuccessBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import static com.qiyuan.fifish.util.Constants.USER_ID;

/**
 * @author lilin
 * created at 2016/9/20 16:36
 */
public class NewFansActivity extends BaseActivity {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.pull_lv)
    PullToRefreshListView pullLv;
    @BindView(R.id.ll_empty_view)
    LinearLayout llEmptyView;
    private int curPage = 1;
    private int unread_count;
    private List<FocusBean.DataBean> mList;
    private WaitingDialog dialog;
    private FansAdapter adapter;

    public NewFansActivity() {
        super(R.layout.activity_list);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(getClass().getSimpleName())) {
            unread_count = intent.getIntExtra(getClass().getSimpleName(), 0);
        }
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true,R.string.new_fans);
        dialog = new WaitingDialog(this);
        mList=new ArrayList<>();
        adapter=new FansAdapter(mList,activity,UserProfile.getUserId());
        pullLv.setAdapter(adapter);
        pullLv.setEmptyView(llEmptyView);
    }

    @Override
    protected void installListener() {
        pullLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i<1) return;
                FocusBean.DataBean item = adapter.getItem(i - 1);
                Intent intent = new Intent(activity, UserCenterActivity.class);
                intent.putExtra(USER_ID,item.user.id);
//              、  intent.putExtra("target_user_id", list.get(i).getUser().get_id());
//                intent.putExtra("type", 12 + "");
//                intent.putExtra(UserCommentsActivity.class.getSimpleName(), list.get(i).getUser().getNickname());
//                intent.putExtra("reply_id", list.get(i).get_id());
//                intent.putExtra("reply_user_id", list.get(i).getUser().get_id());
                startActivity(intent);
            }
        });

        pullLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    @Override
    protected void requestNet() {
        RequestService.getFans(UserProfile.getUserId(), new CustomCallBack() {
            @Override
            public void onStarted() {
                if(dialog!=null&&!activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                if (pullLv!=null) pullLv.onRefreshComplete();
                FocusBean focusBean = JsonUtil.fromJson(result, FocusBean.class);
                if (focusBean.meta.status_code == Constants.HTTP_OK) {
                    List<FocusBean.DataBean> list = focusBean.data;
                    refreshUI(list);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onFinished() {
                if(dialog!=null&&!activity.isFinishing()) dialog.dismiss();
            }
        });

        RequestService.resetAlertCount("fans",new CustomCallBack(){
            @Override
            public void onSuccess(String result) {
                SuccessBean successBean = JsonUtil.fromJson(result, SuccessBean.class);
                if (successBean.meta.status_code==Constants.HTTP_OK){
                    LogUtil.e("message reset success");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

        });
    }

    @Override
    protected void refreshUI(List list) {
        if (list == null || list.size() == 0) return;
        curPage++;
        if(mList.size()>0) mList.clear();
        mList.addAll(list);
        if (adapter == null) {
            adapter = new FansAdapter(mList, activity, UserProfile.getUserId());
            pullLv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

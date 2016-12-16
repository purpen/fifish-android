package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import butterknife.BindView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.UserCommentsAdapter;
import com.qiyuan.fifish.bean.CommentsBean;
import com.qiyuan.fifish.bean.SuccessBean;
import com.qiyuan.fifish.bean.UserCommentsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * @author lilin
 *         created at 2016/5/4 19:17
 */
public class UserCommentsActivity extends BaseActivity {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.pull_lv)
    PullToRefreshListView pullLv;
    @BindView(R.id.ll_empty_view)
    LinearLayout llEmptyView;
    private int curPage = 1;
    private int unread_count;
    private List<UserCommentsBean.DataBean> mList;
    private static final String COMMENT_TYPE = "12";
    private WaitingDialog dialog;
    private UserCommentsAdapter adapter;

    public UserCommentsActivity() {
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
        custom_head.setHeadCenterTxtShow(true, R.string.title_comment);
        dialog = new WaitingDialog(this);
        mList = new ArrayList<>();
        adapter = new UserCommentsAdapter(mList, activity);
        pullLv.setAdapter(adapter);
        pullLv.setEmptyView(llEmptyView);
    }

    @Override
    protected void installListener() {
        pullLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i<1) return;
                UserCommentsBean.DataBean item = adapter.getItem(i-1);
                Intent intent = new Intent(activity, CommentsDetailActivity.class);
                intent.putExtra("id",item.stuff.id);
//                intent.putExtra("target_user_id", list.get(i).getUser().get_id());
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
        RequestService.getMyComments(new CustomCallBack() {
            @Override
            public void onStarted() {
                if (dialog != null && !activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                if(pullLv!=null) pullLv.onRefreshComplete();
                UserCommentsBean userCommentsBean = JsonUtil.fromJson(result, UserCommentsBean.class);
                if (userCommentsBean.meta.status_code == Constants.HTTP_OK) {
                    refreshUI(userCommentsBean.data);
                    return;
                }
                ToastUtils.showError(R.string.request_error);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }

            @Override
            public void onFinished() {
                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
            }
        });

        RequestService.resetAlertCount("comment",new CustomCallBack(){
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
        if (mList.size()>0) mList.clear();
        mList.addAll(list);
//        for (int i = 0; i < unread_count; i++) {
//            list.get(i).is_unread = true;
//        }
        if (adapter == null) {
            adapter = new UserCommentsAdapter(list, activity);
            pullLv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

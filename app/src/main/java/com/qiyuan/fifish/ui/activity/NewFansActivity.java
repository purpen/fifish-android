package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.FansAdapter;
import com.qiyuan.fifish.bean.FocusBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;

import java.util.List;

import butterknife.Bind;

/**
 * @author lilin
 * created at 2016/9/20 16:36
 */
public class NewFansActivity extends BaseActivity {
    @Bind(R.id.custom_head)
    CustomHeadView custom_head;
    @Bind(R.id.lv)
    ListView lv;
    private int curPage = 1;
    private int unread_count;
    private List<FocusBean.DataBean> list;
    private static final String pageSize = "9999";
    private WaitingDialog dialog;
    private FansAdapter adapter;

    public NewFansActivity() {
        super(R.layout.activity_new_fans);
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
        custom_head.setHeadCenterTxtShow(true, "新的粉丝");
        dialog = new WaitingDialog(this);
//        WindowUtils.chenjin(this);
    }

    @Override
    protected void installListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(activity, CommentListActivity.class);
//                intent.putExtra("target_id", list.get(i).target_id);
//                intent.putExtra("target_user_id", list.get(i).getUser().get_id());
//                intent.putExtra("type", 12 + "");
//                intent.putExtra(UserCommentsActivity.class.getSimpleName(), list.get(i).getUser().getNickname());
//                intent.putExtra("reply_id", list.get(i).get_id());
//                intent.putExtra("reply_user_id", list.get(i).getUser().get_id());
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void requestNet() {
//        ClientDiscoverAPI.mycommentsList(String.valueOf(curPage), pageSize, null, LoginInfo.getUserId() + "", COMMENT_TYPE, new RequestCallBack<String>() {
//            @Override
//            public void onStart() {
//                if (!activity.isFinishing() && dialog != null) dialog.show();
//            }
//
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (!activity.isFinishing() && dialog != null) dialog.dismiss();
//                    }
//                }, DataConstants.DIALOG_DELAY);
//                if (responseInfo == null) return;
//                if (TextUtils.isEmpty(responseInfo.result)) return;
//                LogUtil.e(TAG, responseInfo.result);
//                CommentsBean commentsBean = JsonUtil.fromJson(responseInfo.result, CommentsBean.class);
//                if (commentsBean.isSuccess()) {
//                    if (commentsBean.getData() == null) return;
//                    list = commentsBean.getData().getRows();
//                    refreshUI();
//                } else {
//                    ToastUtils.showError(commentsBean.getMessage());
////                    dialog.showErrorWithStatus(commentsBean.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                dialog.dismiss();
//                ToastUtils.showError("网络异常，请确认网络畅通");
////                dialog.showErrorWithStatus("网络异常，请确认网络畅通");
//            }
//        });
    }

    @Override
    protected void refreshUI() {
        if (list == null || list.size() == 0) return;
        curPage++;
        if (adapter == null) {
            adapter = new FansAdapter(list, activity, UserProfile.getUserId());
            lv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

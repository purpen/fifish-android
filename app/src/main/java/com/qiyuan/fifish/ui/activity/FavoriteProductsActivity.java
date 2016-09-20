package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.FavoriteProductsAdapter;
import com.qiyuan.fifish.bean.CommentsBean;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;

import java.util.List;

import butterknife.Bind;

/**
 * @author lilin
 *         created at 2016/5/4 19:17
 */
public class FavoriteProductsActivity extends BaseActivity {
    @Bind(R.id.custom_head)
    CustomHeadView custom_head;
    @Bind(R.id.lv)
    ListView lv;
    private int curPage = 1;
    private int unread_count;
    private List<CommentsBean.CommentItem> list;
    private static final String pageSize = "9999";
    private static final String COMMENT_TYPE = "12";
    private WaitingDialog dialog;
    private FavoriteProductsAdapter adapter;

    public FavoriteProductsActivity() {
        super(R.layout.activity_user_comments);
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
        custom_head.setHeadCenterTxtShow(true, "喜欢");
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
        if (list == null) return;
        if (list.size() == 0) {
//            Util.makeToast("暂无评论");
            return;
        }

        for (int i = 0; i < unread_count; i++) {
            list.get(i).is_unread = true;
        }

        if (adapter == null) {
            adapter = new FavoriteProductsAdapter(list, activity);
            lv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

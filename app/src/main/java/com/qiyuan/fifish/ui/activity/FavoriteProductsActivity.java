package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.FavoriteProductsAdapter;
import com.qiyuan.fifish.bean.CommentsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.ToastUtils;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author lilin
 *         created at 2016/5/4 19:17
 */
public class FavoriteProductsActivity extends BaseActivity {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.pull_lv)
    PullToRefreshListView pullLv;
    @BindView(R.id.ll_empty_view)
    LinearLayout llEmptyView;
    private int curPage = 1;
    private int unread_count;
    private List<CommentsBean.CommentItem> list;
    private static final String pageSize = "9999";
    private static final String COMMENT_TYPE = "12";
    private WaitingDialog dialog;
    private FavoriteProductsAdapter adapter;

    public FavoriteProductsActivity() {
        super(R.layout.activity_list);
    }

    @Override
    protected void initParams() {
        list = new ArrayList<>();
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
        custom_head.setHeadCenterTxtShow(true, R.string.title_favorite);
        dialog = new WaitingDialog(this);
        pullLv.setEmptyView(llEmptyView);
        adapter = new FavoriteProductsAdapter(list, activity);
    }

    @Override
    protected void installListener() {
        pullLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        RequestService.getMyComments(new CustomCallBack() {
            @Override
            public void onStarted() {
                if (dialog != null && !activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
                LogUtil.e(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }


    @Override
    protected void refreshUI() {
        if (list == null) return;
        if (list.size() == 0) {
            return;
        }

        for (int i = 0; i < unread_count; i++) {
            list.get(i).is_unread = true;
        }

        if (adapter == null) {
            adapter = new FavoriteProductsAdapter(list, activity);
            pullLv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

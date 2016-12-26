package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;

import com.qiyuan.fifish.bean.MessageCountBean;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.CustomItemLayout;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

/**
 * @author lilin
 *         created at 2016/4/28 19:15
 */
public class MessageActivity extends BaseActivity {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.item0)
    CustomItemLayout item0;
    @BindView(R.id.item1)
    CustomItemLayout item1;
    @BindView(R.id.item2)
    CustomItemLayout item2;
    @BindView(R.id.item3)
    CustomItemLayout item3;
    private WaitingDialog dialog;
    private boolean isReload;
    private MessageCountBean.DataBean data;
    public MessageActivity() {
        super(R.layout.activity_message);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isReload=true;
        requestNet();
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true, R.string.message);
        dialog = new WaitingDialog(this);
        item0.setTVStyle(R.mipmap.icon_new_fans, R.string.new_fans, R.color.color_333);
        item1.setTVStyle(R.mipmap.icon_unsupport, R.string.receive_support, R.color.color_333);
        item2.setTVStyle(R.mipmap.icon_comment, R.string.title_comment, R.color.color_333);
//        item3.setTVStyle(R.mipmap.sys_msg, "通知", R.color.color_333);
    }

    @OnClick({R.id.item0, R.id.item1, R.id.item2, R.id.item3})
    void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.item0: //新的粉丝
                intent = new Intent(activity, NewFansActivity.class);
                startActivity(intent);
                break;
            case R.id.item1: //收到的赞/喜欢
                intent = new Intent(activity, FavoriteProductsActivity.class);
                startActivity(intent);
                break;
            case R.id.item2: //评论列表
                intent = new Intent(activity, UserCommentsActivity.class);
                startActivity(intent);
                break;
            case R.id.item3: //通知
                startActivity(new Intent(activity, SystemNoticeActivity.class));
                break;
        }
    }

    @Override
    protected void requestNet() {
        RequestService.getAlertCount(new CustomCallBack() {
            @Override
            public void onStarted() {
                if (dialog != null && !activity.isFinishing() &&!isReload) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                MessageCountBean messageCountBean = JsonUtil.fromJson(result, MessageCountBean.class);
                if (messageCountBean.meta.status_code == Constants.HTTP_OK) {
                    data = messageCountBean.data;
                    refreshUI();
                }
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
    }

    @Override
    protected void refreshUI() {
        if (data == null) return;
        item0.setTipsNum(data.alert_fans_count); //系统通知
        item1.setTipsNum(data.alert_like_count); //赞
        item2.setTipsNum(data.alert_comment_count); //评论
    }
}

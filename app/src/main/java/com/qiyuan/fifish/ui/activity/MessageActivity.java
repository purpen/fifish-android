package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.CustomItemLayout;
import com.qiyuan.fifish.ui.view.WaitingDialog;

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
//    private User user;

    public MessageActivity() {
        super(R.layout.activity_message);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestNet();
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true, "消息");
        dialog = new WaitingDialog(this);
        item0.setTVStyle(R.mipmap.icon_new_fans, "新的粉丝", R.color.color_333);
        item1.setTVStyle(R.mipmap.icon_unsupport, "收到的赞", R.color.color_333);
        item2.setTVStyle(R.mipmap.icon_comment, "评论", R.color.color_333);
//        item3.setTVStyle(R.mipmap.sys_msg, "通知", R.color.color_333);
//        WindowUtils.chenjin(this);
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
//                if (user!=null){
//                    intent.putExtra(UserCommentsActivity.class.getSimpleName(),user.counter.fiu_comment_count);
                startActivity(intent);
//                }
                break;
            case R.id.item3: //通知
                startActivity(new Intent(activity, SystemNoticeActivity.class));
                break;
        }
    }

    @Override
    protected void requestNet() {
//        ClientDiscoverAPI.getUserCenterData(new RequestCallBack<String>() {
//            @Override
//            public void onStart() {
//                if (!activity.isFinishing()&& dialog!=null)  dialog.show();
//            }
//
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                LogUtil.e("result", responseInfo.result);
//                if (TextUtils.isEmpty(responseInfo.result)) {
//                    return;
//                }
//
//                try {
//                    HttpResponse<User> response = JsonUtil.json2Bean(responseInfo.result, new TypeToken<HttpResponse<User>>() {});
//                    if (response.isSuccess()){
//                        user=response.getData();
//                        refreshUI();
//                    }
//                } catch (JsonSyntaxException e) {
//                    LogUtil.e(TAG, e.getLocalizedMessage());
//                    ToastUtils.showError("对不起，数据异常");
////                    dialog.showErrorWithStatus("对不起,数据异常");
//                }
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                dialog.dismiss();
//                ToastUtils.showError("网络异常，请确认网络畅通");
////                dialog.showErrorWithStatus("网络异常,请确认网络畅通");
//            }
//        });
    }

    @Override
    protected void refreshUI() {
//        if (user == null) {
//            return;
//        }
//        if (user.counter != null) {
//            item_push_setting.setTipsNum(user.counter.fiu_notice_count); //系统通知
//            item_clear_cache.setTipsNum(user.counter.fiu_comment_count); //评论
//            item_to_comment.setTipsNum(user.counter.message_count);   //私信
//            item_notice.setTipsNum(user.counter.fiu_alert_count);   //提醒数量
//        }
    }
}

package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.FocusBean;
import com.qiyuan.fifish.bean.SuccessBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/4/22 19:00
 */
public class FocusAdapter extends BaseAdapter<FocusBean.DataBean> {
    private ImageLoader imageLoader;
    private static final int TYPE1 = 1; //单向关注
    private static final int TYPE2 = 2; //互向关注
    public static final int NOT_LOVE = 0; //别人的粉丝列表和LoginInfo.getUserId()的关系
    public static final int LOVE = 1;
    private String userId;

    public FocusAdapter(List<FocusBean.DataBean> list, Activity activity, String userId) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
        this.userId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FocusBean.DataBean item = list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = Util.inflateView(R.layout.item_focus_fans, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, activity.getResources().getDimensionPixelSize(R.dimen.dp55)));
        if (item.follower != null) {
            imageLoader.displayImage(item.follower.avatar.small, holder.riv);
            holder.tv_name.setText(item.follower.username);
            if (!TextUtils.isEmpty(item.follower.summary) && !TextUtils.equals(item.follower.summary, "null")) {
                holder.tv_desc.setText(item.follower.summary);
            }
        }
        if (item.is_focus) {
            setFocusBtnStyle(holder.btn, R.dimen.dp8, R.string.focused, R.mipmap.focused, R.color.color_2288ff, R.drawable.shape_focus);
        } else {
            setFocusBtnStyle(holder.btn, R.dimen.dp15, R.string.focus, R.mipmap.unfocus, R.color.color_7f8fa2, R.drawable.shape_unfocus);
        }

        setClickListener(holder.btn, item);
        return convertView;

    }

    private void setFocusBtnStyle(Button bt_focus, int dimensionPixelSize, int focus, int unfocus_pic, int color, int drawable) {
        dimensionPixelSize = activity.getResources().getDimensionPixelSize(dimensionPixelSize);
        bt_focus.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        bt_focus.setText(focus);
        bt_focus.setTextColor(activity.getResources().getColor(color));
        bt_focus.setBackgroundResource(drawable);
        bt_focus.setCompoundDrawablesWithIntrinsicBounds(unfocus_pic, 0, 0, 0);
    }

    private void setClickListener(Button btn, final FocusBean.DataBean item) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.is_focus) { //做取消关注
                    setFocusBtnStyle((Button) view, R.dimen.dp15, R.string.focus, R.mipmap.unfocus, R.color.color_7f8fa2, R.drawable.shape_unfocus);
                    cancelFocus(item, view);
                } else {
                    setFocusBtnStyle((Button) view, R.dimen.dp8, R.string.focused, R.mipmap.focused, R.color.color_2288ff, R.drawable.shape_focus);
                    doFocus(item, view);
                }
            }
        });
    }

    //取消关注
    private void cancelFocus(final FocusBean.DataBean item, final View view) {
        if (TextUtils.equals(UserProfile.getUserId(), userId)) { //在自己的个人中心
            RequestService.cancelFocus(item.follower.id, new CustomCallBack() {
                @Override
                public void onStarted() {
                    view.setEnabled(false);
                }

                @Override
                public void onSuccess(String result) {
                    SuccessBean successBean = JsonUtil.fromJson(result, SuccessBean.class);
                    if (successBean.meta.status_code == Constants.HTTP_OK) {
                        item.is_focus = false;
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    ToastUtils.showError(R.string.request_error);
                }

                @Override
                public void onFinished() {
                    view.setEnabled(true);
                }
            });
        } else {
//            dealOthersCancelFocus(item, view);
        }
    }

    //关注粉丝操作
    private void doFocus(final FocusBean.DataBean item, final View view) {
        if (TextUtils.equals(UserProfile.getUserId(), userId)) { //在自己的个人中心
            RequestService.doFocus(item.follower.id, new CustomCallBack() {
                @Override
                public void onStarted() {
                    view.setEnabled(false);
                }

                @Override
                public void onSuccess(String result) {
                    SuccessBean successBean = JsonUtil.fromJson(result, SuccessBean.class);
                    if (successBean.meta.status_code == Constants.HTTP_OK) {
                        item.is_focus = true;
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    ToastUtils.showError(R.string.request_error);
                }

                @Override
                public void onFinished() {
                    view.setEnabled(true);
                }
            });
        } else {
//            dealOthersFocus(item, view);
        }
    }

    static class ViewHolder {
        @BindView(R.id.riv)
        ImageView riv;
        @BindView(R.id.riv_auth)
        RoundedImageView riv_auth;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_desc)
        TextView tv_desc;
        @BindView(R.id.btn)
        Button btn;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

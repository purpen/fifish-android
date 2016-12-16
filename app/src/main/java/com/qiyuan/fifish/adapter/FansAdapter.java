package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
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

/**
 * @author lilin
 *         created at 2016/4/22 19:00
 */
public class FansAdapter extends BaseAdapter<FocusBean.DataBean> implements View.OnClickListener {
    private ImageLoader imageLoader;
    public static final int NOT_LOVE = 0; //别人的粉丝列表和LoginInfo.getUserId()的关系
    private String userId;
    public FansAdapter(List<FocusBean.DataBean> list, Activity activity, String userId) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
        this.userId = userId;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_focus_head)
                .showImageForEmptyUri(R.mipmap.default_focus_head)
                .showImageOnFail(R.mipmap.default_focus_head)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .delayBeforeLoading(0)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
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
        imageLoader.displayImage(item.user.avatar.small, holder.riv,options);
        holder.tv_name.setText(item.user.username);
        if (!TextUtils.isEmpty(item.follower.summary)&&!TextUtils.equals(item.follower.summary,"null")){
            holder.tv_desc.setText(item.follower.summary);
        }

        if (item.is_follow) {
            setFocusBtnStyle(holder.btn, R.dimen.dp8, R.string.focused, R.mipmap.focused, R.color.color_2288ff, R.drawable.shape_focus);
        } else {
            setFocusBtnStyle(holder.btn, R.dimen.dp15, R.string.focus, R.mipmap.unfocus, R.color.color_7f8fa2, R.drawable.shape_unfocus);
        }
        setClickListener(holder.btn, item);
        return convertView;
    }

    private void setClickListener(Button btn, final FocusBean.DataBean item) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.is_follow) { //做取消关注
                    setFocusBtnStyle((Button) view, R.dimen.dp15, R.string.focus, R.mipmap.unfocus, R.color.color_7f8fa2, R.drawable.shape_unfocus);
                    cancelFocus(item,view);
                } else {
                    setFocusBtnStyle((Button) view, R.dimen.dp8, R.string.focused, R.mipmap.focused, R.color.color_2288ff, R.drawable.shape_focus);
                    doFocus(item,view);
                }
            }
        });
    }

    private void setFocusBtnStyle(Button bt_focus, int dimensionPixelSize, int focus, int unfocus_pic, int color, int drawable) {
        dimensionPixelSize = activity.getResources().getDimensionPixelSize(dimensionPixelSize);
        bt_focus.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        bt_focus.setText(focus);
        bt_focus.setTextColor(activity.getResources().getColor(color));
        bt_focus.setBackgroundResource(drawable);
        bt_focus.setCompoundDrawablesWithIntrinsicBounds(unfocus_pic, 0, 0, 0);
    }

    //取消关注
    private void cancelFocus(final FocusBean.DataBean item, final View view) {
        if (TextUtils.equals(UserProfile.getUserId(),userId)) { //在自己的个人中心
            RequestService.cancelFocus(item.user.id, new CustomCallBack() {
                @Override
                public void onStarted() {
                    view.setEnabled(false);
                }

                @Override
                public void onSuccess(String result) {
                    SuccessBean successBean = JsonUtil.fromJson(result, SuccessBean.class);
                    if (successBean.meta.status_code== Constants.HTTP_OK){
                        item.is_follow=false;
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
        if (TextUtils.equals(UserProfile.getUserId(),userId)) { //在自己的个人中心
            RequestService.doFocus(item.user.id, new CustomCallBack() {
                @Override
                public void onStarted() {
                    view.setEnabled(false);
                }

                @Override
                public void onSuccess(String result) {
                    SuccessBean successBean = JsonUtil.fromJson(result, SuccessBean.class);
                    if (successBean.meta.status_code== Constants.HTTP_OK){
                        item.is_follow=true;
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


//    private void showFocusFansConfirmView(FocusFans item, String tips) {
//        View view = Util.inflateView(activity, R.layout.popup_focus_fans, null);
//        RoundedImageView riv = (RoundedImageView) view.findViewById(R.id.riv);
//        TextView tv_take_photo = (TextView) view.findViewById(R.id.tv_take_photo);
//        TextView tv_album = (TextView) view.findViewById(R.id.tv_album);
//        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
//        ImageLoader.getInstance().displayImage(item.follows.avatar_url, riv, options);
//        tv_take_photo.setText(String.format(tips + " %s ?", item.follows.nickname));
//        tv_album.setText(tips);
//        tv_album.setOnClickListener(this);
//        tv_album.setTag(item);
//        tv_cancel.setOnClickListener(this);
//        PopupWindowUtil.show(activity, view);
//    }

    @Override
    public void onClick(final View view) {
//        switch (view.getId()) {
//            case R.id.tv_cancel:
//                PopupWindowUtil.dismiss();
//                break;
//            case R.id.tv_album:
//                view.setEnabled(false);
//                final FocusFans item = (FocusFans) view.getTag();
//                if (TextUtils.equals(LoginUserInfo.getUserId(),userId)) {
//                    if (item == null) return;
//                    ClientDiscoverAPI.cancelFocusOperate(item.follows.user_id + "", new RequestCallBack<String>() {
//                        @Override
//                        public void onSuccess(ResponseInfo<String> responseInfo) {
//                            view_link_help.setEnabled(true);
//                            PopupWindowUtil.dismiss();
//                            if (responseInfo == null) return;
//                            if (TextUtils.isEmpty(responseInfo.result)) return;
//                            HttpResponse response = JsonUtil.fromJson(responseInfo.result, HttpResponse.class);
//                            if (response.isSuccess()) {
//                                item.type = TYPE1;
//                                notifyDataSetChanged();
////                                Util.makeToast(response.getMessage());
//                                ToastUtils.showSuccess("已取消关注");
////                                svProgressHUD.showSuccessWithStatus("已取消关注");
//                                return;
//                            }
//                            ToastUtils.showError(response.getMessage());
////                            svProgressHUD.showErrorWithStatus(response.getMessage());
//                        }
//
//                        @Override
//                        public void onFailure(HttpException e, String s) {
//                            view_link_help.setEnabled(true);
//                            PopupWindowUtil.dismiss();
//                            ToastUtils.showError("网络异常，请确认网络畅通");
////                            svProgressHUD.showErrorWithStatus("网络异常，请确认网络畅通");
//                        }
//                    });
//                } else { //处理别人粉丝列表的关注和取消关注操作
//                    dealOthersFocus(item, view);
//                }
//
//                break;
//        }
    }

    /**
     * 需求只考虑LoginInfo.getUserId()与别人的关注用户和粉丝的关系
     */
//    private void dealOthersFoucsFansStyle(final FocusFans item, final ViewHolder holder) {
//        LogUtil.e("dealOthersFoucsFansStyle is_love===" + item.follows.is_love);
//        if (item.follows.is_love == NOT_LOVE) {
//            holder.btn.setText("关注");
//            holder.btn.setTextColor(activity.getResources().getColor(R.color.color_333));
//            holder.btn.setBackgroundResource(R.drawable.shape_unfocus);
//            holder.btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showFocusFansConfirmView(item, "开始关注");
//                    doFocus(item, view);
//                }
//            });
//        } else if (item.follows.is_love == LOVE) {
//            holder.btn.setText("已关注");
//            holder.btn.setTextColor(activity.getResources().getColor(android.R.color.white));
//            holder.btn.setBackgroundResource(R.drawable.shape_focus);
//            holder.btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {//取消关注
//                    showFocusFansConfirmView(item, "停止关注");
//                }
//            });
//        }
//    }

//    private void dealOthersFocus(final FocusFans item, final View view) {
//        LogUtil.e("dealOthersFocusFans is_love===" + item.follows.is_love);
//        if (item.follows.is_love == NOT_LOVE) { //别人的关注列表做关注操作
//            ClientDiscoverAPI.focusOperate(item.follows.user_id + "", new RequestCallBack<String>() {
//                @Override
//                public void onSuccess(ResponseInfo<String> responseInfo) {
//                    view_link_help.setEnabled(true);
//                    PopupWindowUtil.dismiss();
//                    if (responseInfo == null) return;
//                    if (TextUtils.isEmpty(responseInfo.result)) return;
//                    HttpResponse response = JsonUtil.fromJson(responseInfo.result, HttpResponse.class);
//                    if (response.isSuccess()) {
//                        item.follows.is_love = LOVE;
//                        notifyDataSetChanged();
////                        Util.makeToast(response.getMessage());
//                        return;
//                    }
//                    ToastUtils.showError(response.getMessage());
////                    svProgressHUD.showErrorWithStatus(response.getMessage());
//
//                }
//
//                @Override
//                public void onFailure(HttpException e, String s) {
//                    view_link_help.setEnabled(true);
//                    PopupWindowUtil.dismiss();
//                    ToastUtils.showError("网络异常，请确认网络畅通");
////                    svProgressHUD.showErrorWithStatus("网络异常，请确认网络畅通");
//                }
//            });
//        } else if (item.follows.is_love == LOVE) {//取消关注
//            ClientDiscoverAPI.cancelFocusOperate(item.follows.user_id + "", new RequestCallBack<String>() {
//                @Override
//                public void onSuccess(ResponseInfo<String> responseInfo) {
//                    view_link_help.setEnabled(true);
//                    PopupWindowUtil.dismiss();
//                    if (responseInfo == null) return;
//                    if (TextUtils.isEmpty(responseInfo.result)) return;
//                    HttpResponse response = JsonUtil.fromJson(responseInfo.result, HttpResponse.class);
//                    if (response.isSuccess()) {
//                        item.follows.is_love = NOT_LOVE;
//                        notifyDataSetChanged();
//                        ToastUtils.showSuccess("已取消关注");
//                        return;
//                    }
//                    ToastUtils.showError(response.getMessage());
//                }
//
//                @Override
//                public void onFailure(HttpException e, String s) {
//                    view_link_help.setEnabled(true);
//                    PopupWindowUtil.dismiss();
//                    ToastUtils.showError("网络异常，请确认网络畅通");
//                }
//            });
//        }
//
//    }

    static class ViewHolder {
        @BindView(R.id.riv)
        RoundedImageView riv;
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

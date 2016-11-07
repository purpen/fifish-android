package com.qiyuan.fifish.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.FeedbackActivity;
import com.qiyuan.fifish.ui.activity.FindFriendsActivity;
import com.qiyuan.fifish.ui.activity.MessageActivity;
import com.qiyuan.fifish.ui.activity.PublishPictureActivity;
import com.qiyuan.fifish.ui.activity.PublishVideoActivity;
import com.qiyuan.fifish.ui.activity.SupportProductsActivity;
import com.qiyuan.fifish.ui.activity.SystemSettingsActivity;
import com.qiyuan.fifish.ui.activity.UserCenterActivity;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.CustomItemLayout;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import org.xutils.common.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.custom_head)
    CustomHeadView customHead;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.riv)
    RoundedImageView riv;
    @BindView(R.id.item_message)
    CustomItemLayout itemMessage;
    @BindView(R.id.item_support)
    CustomItemLayout itemSupport;
    @BindView(R.id.item_feed_back)
    CustomItemLayout itemFeedBack;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_products_num)
    TextView tvProductsNum;
    @BindView(R.id.tv_focus_num)
    TextView tvFocusNum;
    @BindView(R.id.tv_fans_num)
    TextView tvFansNum;
    private UserProfile userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_mine);
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true, R.string.me);
        customHead.setIvLeft(R.mipmap.icon_add_friend);
        customHead.setHeadGoBackShow(false);
        customHead.setRightImgBtnShow(true);
        customHead.getRightImgBtn().setImageResource(R.mipmap.setting);
        itemMessage.setTVStyle(R.mipmap.message, R.string.message, R.color.color_333);
        itemSupport.setTVStyle(R.mipmap.support, R.string.support, R.color.color_333);
        itemFeedBack.setTVStyle(R.mipmap.feedback, R.string.feedback, R.color.color_333);
    }

    @Override
    protected void installListener() {
        customHead.getIvLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, FindFriendsActivity.class));
            }
        });

        customHead.getRightImgBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, SystemSettingsActivity.class));
            }
        });


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            requestNet();
        }
    }


    @Override
    protected void requestNet() {
        RequestService.getUserProfile(new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("个人中心" + result);
                if (TextUtils.isEmpty(result)) return;
                try {
                    userInfo = JsonUtil.fromJson(result, UserProfile.class);
                    if (userInfo.meta.status_code == Constants.HTTP_OK) {
                        refreshUI();
                        return;
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });

    }

    @Override
    protected void refreshUI() {
        if (userInfo == null || userInfo.data == null) return;
        ImageLoader.getInstance().displayImage(userInfo.data.avatar.large, riv, options);
        userName.setText(userInfo.data.username);
        if (TextUtils.isEmpty(userInfo.data.zone)){
            tvLocation.setVisibility(View.GONE);
        }else {
            tvLocation.setVisibility(View.VISIBLE);
            tvLocation.setText(userInfo.data.zone);
        }
        tvFocusNum.setText(userInfo.data.follow_count);
        tvFansNum.setText(userInfo.data.fans_count);
        if (TextUtils.isEmpty(userInfo.data.summary) || TextUtils.equals("null", userInfo.data.summary)) {
            tvSummary.setText("");
        } else {
            tvSummary.setText(userInfo.data.summary);
        }
        tvProductsNum.setText(userInfo.data.stuff_count);
    }

    @OnClick({R.id.btn1, R.id.btn, R.id.rl, R.id.item_message, R.id.item_support, R.id.item_feed_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(activity, PublishPictureActivity.class));
                break;
            case R.id.btn:
                startActivity(new Intent(activity, PublishVideoActivity.class));
                break;
            case R.id.rl:
                startActivity(new Intent(activity, UserCenterActivity.class));
                break;
            case R.id.item_message:
                startActivity(new Intent(activity, MessageActivity.class));
                break;
            case R.id.item_support:
                startActivity(new Intent(activity, SupportProductsActivity.class));
                break;
            case R.id.item_feed_back:
                startActivity(new Intent(activity, FeedbackActivity.class));
                break;
        }
    }
}

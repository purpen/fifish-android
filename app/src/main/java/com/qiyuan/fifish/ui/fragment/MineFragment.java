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
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ErrorBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.FeedbackActivity;
import com.qiyuan.fifish.ui.activity.MainActivity;
import com.qiyuan.fifish.ui.activity.SystemSettingsActivity;
import com.qiyuan.fifish.ui.activity.UserCenterActivity;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.CustomItemLayout;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.SPUtil;
import com.qiyuan.fifish.util.ToastUtils;

import org.xutils.common.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {

    @Bind(R.id.custom_head)
    CustomHeadView customHead;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.riv)
    RoundedImageView riv;
    @Bind(R.id.item_message)
    CustomItemLayout itemMessage;
    @Bind(R.id.item_support)
    CustomItemLayout itemSupport;
    @Bind(R.id.item_feed_back)
    CustomItemLayout itemFeedBack;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_summary)
    TextView tvSummary;
    @Bind(R.id.tv_products_num)
    TextView tvProductsNum;
    @Bind(R.id.tv_focus_num)
    TextView tvFocusNum;
    @Bind(R.id.tv_fans_num)
    TextView tvFansNum;

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
        customHead.setHeadGoBackShow(false);
        customHead.setRightImgBtnShow(true);
        customHead.getRightImgBtn().setImageResource(R.mipmap.setting);
        itemMessage.setTVStyle(R.mipmap.message, R.string.message, R.color.color_333);
        itemSupport.setTVStyle(R.mipmap.support, R.string.support, R.color.color_333);
        itemFeedBack.setTVStyle(R.mipmap.feedback, R.string.feedback, R.color.color_333);
    }

    @Override
    protected void installListener() {
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
        RequestService.getUserProfile(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                try {
                    UserProfile userInfo = JsonUtil.fromJson(result, UserProfile.class);
                    if (userInfo.meta.meta.status_code == Constants.HTTP_OK) {
                        SPUtil.write(Constants.LOGIN_INFO, result);
                        startActivity(new Intent(activity, MainActivity.class));
                        return;
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } finally {
                    ErrorBean errorBean = JsonUtil.fromJson(result, ErrorBean.class);
                    ToastUtils.showError(errorBean.meta.message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @OnClick({R.id.rl, R.id.item_message, R.id.item_support, R.id.item_feed_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl:
                startActivity(new Intent(activity, UserCenterActivity.class));
                break;
            case R.id.item_message:

                break;
            case R.id.item_support:

                break;
            case R.id.item_feed_back:
                startActivity(new Intent(activity, FeedbackActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

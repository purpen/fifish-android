package com.qiyuan.fifish.ui.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.activity.FeedbackActivity;
import com.qiyuan.fifish.ui.activity.SystemSettingsActivity;
import com.qiyuan.fifish.ui.activity.UserCenterActivity;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.CustomItemLayout;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import butterknife.Bind;
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_mine);
        super.onCreateView(inflater, container, savedInstanceState);
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
                startActivity(new Intent(activity,SystemSettingsActivity.class));
            }
        });
    }

    @OnClick({R.id.rl,R.id.item_message, R.id.item_support, R.id.item_feed_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl:
                startActivity(new Intent(activity,UserCenterActivity.class));
                break;
            case R.id.item_message:

                break;
            case R.id.item_support:

                break;
            case R.id.item_feed_back:
                startActivity(new Intent(activity,FeedbackActivity.class));
                break;
        }
    }
}

package com.qiyuan.fifish.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.Util;

/**
 * @author lilin
 * created at 2016/4/11 16:58
 */
public class CustomSubItemLayout extends RelativeLayout {
    private TextView tv_right_txt;
    private ImageView iv_more_arrow;
    private TextView tv_arrow_left;
    private RelativeLayout rl_item_box;
    private ImageView iv;
    private TextView tv_title;
    private TextView tv_subtitle;
    public CustomSubItemLayout(Context context) {
        this(context,null);
    }

    public CustomSubItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomSubItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflatelayout(context);
    }

    private void inflatelayout(Context context){
        View view = Util.inflateView(R.layout.custom_subitem_layout, this);
        rl_item_box = (RelativeLayout) view.findViewById(R.id.rl_item_box);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_subtitle = (TextView) view.findViewById(R.id.tv_subtitle);
        iv = (ImageView) view.findViewById(R.id.iv);
        iv_more_arrow = (ImageView) view.findViewById(R.id.iv_more_arrow);
        tv_right_txt = (TextView) view.findViewById(R.id.tv_right_txt);
        tv_arrow_left = (TextView) view.findViewById(R.id.tv_arrow_left);
    }

    public void setRightMoreImgStyle(boolean isShow){
        if (isShow){
            iv_more_arrow.setVisibility(VISIBLE);
        }else{
            iv_more_arrow.setVisibility(GONE);
        }
    }

    public void setTitle(String s){
        tv_title.setText(s);
    }

    public void setSubTitle(String s){
        tv_subtitle.setText(s);
    }

    public void setImg(int resId){
        iv.setImageResource(resId);
    }
    public void setTvArrowLeftStyle(boolean isShow,int resId){
        if (isShow){
            tv_arrow_left.setVisibility(VISIBLE);
            tv_arrow_left.setText(resId);
        }else{
            tv_arrow_left.setVisibility(GONE);
        }
    }

    public void setTvArrowLeftStyle(boolean isShow, String txt, int color){
        if (isShow){
            tv_arrow_left.setVisibility(VISIBLE);
            tv_arrow_left.setText(txt);
            tv_arrow_left.setTextColor(getResources().getColor(color));
        }else{
            tv_arrow_left.setVisibility(GONE);
        }
    }
    public String getTvarrowLeftTxt(){
        return  tv_arrow_left.getText().toString();
    }


    public void sertTVRightTxt(String txt){
        if (!TextUtils.isEmpty(txt)){
            tv_right_txt.setVisibility(VISIBLE);
            tv_right_txt.setText(txt);
        }
    }
    public void setTVRightTxt(String txt, int txtColor){
        if (!TextUtils.isEmpty(txt)){
            tv_right_txt.setVisibility(VISIBLE);
            tv_right_txt.setText(txt);
            tv_right_txt.setTextColor(getResources().getColor(txtColor));
        }
    }

    public void setHeight(int dimenId){
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(dimenId));
        rl_item_box.setLayoutParams(lp);
    }
}

package com.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiyuan.fifish.R;

/**
 * 可复用标题栏
 * <p/>
 * 添加自定义属性 标题|返回键 slf  ff3366
 */
public class GlobalTitleLayout extends RelativeLayout {

    private ImageView mapImageView;//标题栏地图图标（在商家列表页最右上处）
    private ImageView backButton;//标题栏返回按钮
    private TextView offlineStore;//标题栏线下体验店图标（在好货（后更名为商店）碎片左上角）
    private ImageView qrCodeScan;//标题栏扫描二维码的图标（在首页碎片左上角）
    private TextView titleText;// 标题栏标题文字
    private TextView rightButton;//标题栏右侧文字按钮，默认隐藏
    private ImageView rightImageShareButton;//标题栏右侧分享图标按钮，默认隐藏
    private ImageView rightImageSetButton;//标题栏右侧设置图标按钮，默认隐藏
    private ImageView rightImageSearchButton;//标题栏右侧搜索图标按钮，默认显示
    private ImageView rightImageShopCartButton;//标题栏右侧购物车图标按钮，默认显示
    private TextView rightImageShopCartCounterButton;//标题栏右侧购物车上的黑色计数图标按钮，默认隐藏

    public GlobalTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(
                //multip为多重的、多样的、许多的之意，在这儿是指该自定义titlebar为可复用的，可多次使用的
                R.layout.multip_global_title,
                this,
                true
        );
        String title = null;
        boolean showBackButton = true;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GlobalTitleLayout);
        if (typedArray != null) {
            title = typedArray.getString(R.styleable.GlobalTitleLayout_global_title_layout_title);
            if (!TextUtils.isEmpty(title)) {
                showBackButton = typedArray.getBoolean(R.styleable.GlobalTitleLayout_global_title_layout_show_back, false);
            }
        }


        if (view != null) {
            backButton = (ImageView) view.findViewById(R.id.global_title_back);
            titleText = (TextView) view.findViewById(R.id.global_title_text);
            rightButton = (TextView) view.findViewById(R.id.global_title_button);

            if (title != null) {
                setTitle(title);
            }

            setBackButtonVisibility(showBackButton);

            //为标题栏后退按钮绑定默认监听事件
            //监听事件默认调用当前Activity 的onBackPressed 方法
            backButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        activity.onBackPressed();
                    }
                }
            });

        }
        if (typedArray != null) {
            typedArray.recycle();
        }
    }

    // 设置标题栏标题文字
    public void setTitle(CharSequence title) {
        if (title != null) {
            titleText.setText(title);
        }
    }
    // 是否显示标题栏标题文字
    public void showTitle(boolean bool) {
        if (bool) titleText.setVisibility(VISIBLE);
        else titleText.setVisibility(GONE);

    }
    // 设置标题栏标题文字
    public void setBoldTitle(CharSequence title) {
        if (title != null) {
            titleText.getPaint().setFakeBoldText(true);
            titleText.setText(title);
        }
    }
    /**
     * 为右侧文字按钮设置文字并绑定监听器，同时自动显示右侧按钮
     *
     * @param buttonText                 右侧按钮文字
     * @param onRightButtonClickListener 右侧按钮 OnClickListener 监听器
     */
    public void setRightButton(
            CharSequence buttonText,
            OnClickListener onRightButtonClickListener) {
        if (onRightButtonClickListener != null && buttonText != null) {
            rightButton.setVisibility(VISIBLE);
            rightButton.setText(buttonText);
            rightButton.setOnClickListener(onRightButtonClickListener);
        }
    }

    public void setRightButton(CharSequence title) {
        if (title != null) {
            rightButton.setText(title);
        }
    }

    /*对如下7个图标按钮的监听事件留到后面做到了搜索、购物车等相应页面的时候再考虑
    到那时，给它们分别设置监听，可在本类中写，也可到时看情况在别的类中写，监听被点击之后
    分别可跳转到相应的搜索、购物车等页面*/

    public void setRightButtonOnClickListener(OnClickListener onRightButtonClickListener) {
        if (onRightButtonClickListener != null) {
            rightButton.setOnClickListener(onRightButtonClickListener);
        }
    }

    /**
     * 为标题栏后退按钮设置点击事件，由于已设置默认事件，如无特殊情况，不推荐重新设置监听器
     *
     * @param onBackButtonClickListener 后退按钮 OnClickListener 监听器
     */
    public void setOnBackButtonClickListener(OnClickListener onBackButtonClickListener) {
        if (onBackButtonClickListener != null) {
            backButton.setOnClickListener(onBackButtonClickListener);
        }
    }

    /**
     * 设置是否显示标题栏后退按钮，默认为显示
     *
     * @param visible 是否显示标题栏后退按钮
     */
    public void setBackButtonVisibility(boolean visible) {

        if (visible) {
            backButton.setVisibility(VISIBLE);
        } else {
            backButton.setVisibility(GONE);
        }
    }

    //
    public void setBackButtonListener(OnClickListener listener) {
        if (listener != null) {
            backButton.setVisibility(VISIBLE);
            backButton.setOnClickListener(listener);
        } else {
            backButton.setVisibility(GONE);
        }
    }

}

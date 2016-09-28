package com.qiyuan.fifish.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.qiyuan.fifish.R;

/**
 * Created by taihuoniao on 2016/1/21.
 */
public class WaitingDialog extends Dialog {
    private ImageView ivBigLoading;
    private RotateAnimation mRotateAnimation;
    public WaitingDialog(Context context) {
        this(context, R.style.custom_progress_dialog);
    }

    private WaitingDialog(Context context, int theme) {
        super(context, R.style.custom_progress_dialog);
        this.setContentView(R.layout.view_svprogressdefault);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
//        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        initViews();
        init();
    }
    private void initViews() {
        ivBigLoading = (ImageView) findViewById(R.id.ivBigLoading);
    }

    private void init() {
        mRotateAnimation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(1000L);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setRepeatCount(-1);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
    }

    @Override
    public void show() {
        clearAnimations();
        super.show();
        ivBigLoading.startAnimation(mRotateAnimation);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        clearAnimations();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }

    private void clearAnimations() {
        ivBigLoading.clearAnimation();
    }

}

package com.qiyuan.fifish.util;

import android.app.Activity;
import android.view.*;
import android.widget.PopupWindow;


/**
 * @author lilin
 *         created at 2016/4/26 18:39
 */
public class PopupWindowUtil {
    private static OnDismissListener windowListener;
    private static PopupWindow popupWindow;
    private static Activity activity;
    public interface OnDismissListener {
        void onDismiss();
    }

    public static void setListener(OnDismissListener listener) {
        windowListener = listener;
    }

    public static void show(Activity activity, View view) {
        PopupWindowUtil.activity = activity;
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(android.R.style.Widget_PopupWindow);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(dismissListener);
        setWindowAlpha(0.5f);
    }

    public static void show(Activity activity, View view, int gravity) {
        PopupWindowUtil.activity = activity;
        PopupWindow popupWindow = new PopupWindow(view, Util.getScreenWidth() * 4 / 5, Util.getScreenHeight() / 4);
        popupWindow.setAnimationStyle(android.R.style.Widget_PopupWindow);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(view, gravity, 0, 0);
        popupWindow.setOnDismissListener(dismissListener);
        setWindowAlpha(0.5f);
    }

    private static void setWindowAlpha(float f) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = f;
        window.setAttributes(lp);
    }

    private static PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setWindowAlpha(1f);
            if (windowListener != null) {
                windowListener.onDismiss();
                PopupWindowUtil.windowListener = null;
            }
        }
    };


    public static void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }
}

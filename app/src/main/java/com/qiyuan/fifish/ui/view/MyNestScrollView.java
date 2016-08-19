package com.qiyuan.fifish.ui.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
/**
 * Created by Amy on 2016/8/8.
 */
public class MyNestScrollView extends NestedScrollView{
    public MyNestScrollView(Context context) {
        this(context, null);
    }

    public MyNestScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyNestScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        init();
    }

//    private void init() {
//        requestDisallowInterceptTouchEvent(true);
//    }

//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        LogUtil.e("scrollY==="+t+";;;;oldScrollY==="+oldt+"=="+computeVerticalScrollRange());
//        if (t<=0){
//            requestDisallowInterceptTouchEvent(true);
//        }else {
//            requestDisallowInterceptTouchEvent(false);
//        }
//    }

}

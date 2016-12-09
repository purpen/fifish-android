package com.qiyuan.fifish.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ViewPagerAdapter;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;

/**
 * @author lilin
 *         created at 2016/4/20 11:58
 */
public class ScrollableView extends RelativeLayout {
    private Context context;
    private int interval = 3000;  //默认3s
    private CustomAutoScrollViewPager viewPager;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private ArrayList<ImageView> imageViews;
    private int currentItem;
    private LinearLayout ll;
    private int size;

    public ScrollableView(Context context) {
        this(context, null);
    }

    public ScrollableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public void setAdapter(ViewPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        this.size=adapter.getSize();
    }

    private void initView() {
        View view = Util.inflateView(R.layout.scrollable_view, this);
        viewPager = (CustomAutoScrollViewPager) view.findViewById(R.id.casvp);
        ll = (LinearLayout) view.findViewById(R.id.ll);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener){
        if (onPageChangeListener!=null){
            viewPager.addOnPageChangeListener(onPageChangeListener);
        }
    }

    public void setSwipeScrollDurationFactor(double f) {
        viewPager.setSwipeScrollDurationFactor(f);
    }

    public void setAutoScrollDurationFactor(double f){
        viewPager.setAutoScrollDurationFactor(f);

    }

    public void setInterval(int interval){
        viewPager.setInterval(interval);
    }

    public void start(){
        viewPager.startAutoScroll();
    }

    public void stop(){
        viewPager.stopAutoScroll();
    }

    public void showIndicators() {
        imageViews = new ArrayList<>();
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(context.getResources().getDimensionPixelSize(R.dimen.dp5), 0, 0, context.getResources().getDimensionPixelSize(R.dimen.dp10));
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageView imageView;
        for (int i = 0; i < size; i++) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(vlp);
            if (i==currentItem){
//                imageView.setImageResource(R.drawable.shape_oval_sel);
                imageView.setImageResource(R.drawable.indicator_rect_sel);
            }else {
//                imageView.setImageResource(R.drawable.shape_oval_unsel);
                imageView.setImageResource(R.drawable.indicator_rect_unsel);
            }
            imageViews.add(imageView);
            ll.addView(imageView, llp);
        }
        addOnPageChangeListener(new CustomOnPageChangeListener());
    }

    private class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            currentItem=position = position % size;//mPagerList.size()
            setCurFocus(position);
//            LogUtil.e("onPageSelected", position % size + "");
        }

        private void setCurFocus(int position) {
//            LogUtil.e("setCurFocus", position + "");
            for (int i = 0; i < size; i++) {
                if (i == position) {
                    imageViews.get(i).setImageResource(R.drawable.indicator_rect_sel);
                } else {
                    imageViews.get(i).setImageResource(R.drawable.indicator_rect_unsel);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public void setCurrentItem(int i) {
        viewPager.setCurrentItem(i);
    }

    public void setPagingEnabled(boolean b) {
        viewPager.setPagingEnabled(false);
    }
}

package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.ui.activity.MainActivity;
import com.qiyuan.fifish.ui.activity.UserGuideActivity;
import com.qiyuan.fifish.util.ToastUtils;

import java.util.List;

public class ViewPagerAdapter<T> extends RecyclingPagerAdapter {
    private final String TAG = getClass().getSimpleName();
    protected Activity activity;
    protected List<T> list;
    private int size;
    private boolean isInfiniteLoop;
    private String code;

    public int getSize() {
        return size;
    }

    public ViewPagerAdapter(final Activity activity, List<T> list) {
        this.activity = activity;
        this.list = list;
        this.size = list.size();
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : size;
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(activity);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final T content = list.get(getPosition(position));

//        if (content instanceof Banner) {
//            ImageLoader.getInstance().displayImage(((Banner) content).cover_url, holder.imageView, options);
//        }

        if (content instanceof Integer) {
            holder.imageView.setImageResource((Integer) content);
//            ImageLoader.getInstance().displayImage("drawable://"+(Integer) content,holder.imageView,options);
        }

        if (content instanceof String) {
            if (TextUtils.isEmpty((String) content)) {
                ToastUtils.showError("图片链接为空");
            } else {
                ImageLoader.getInstance().displayImage((String) content, holder.imageView);
            }
        }


        if (activity instanceof UserGuideActivity) {
            if (position == size - 1) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 最后一张被点击
                    }
                });
            }
        }

        if (activity instanceof MainActivity) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 图片被点击
                }
            });
        }

        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ViewPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
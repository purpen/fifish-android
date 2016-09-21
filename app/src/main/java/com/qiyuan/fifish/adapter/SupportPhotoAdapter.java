package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.SupportPhotoBean;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;

/**
 * @author lilin
 *         created at 2016/9/20 18:04
 */
public class SupportPhotoAdapter extends BaseAdapter<SupportPhotoBean> {
    private int width;
    public SupportPhotoAdapter(ArrayList<SupportPhotoBean> list, Activity activity) {
        super(list, activity);
        this.width=Util.getScreenWidth()-4*activity.getResources().getDimensionPixelSize(R.dimen.dp10);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SupportPhotoBean item = list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = holder.iv = new ImageView(activity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height=width/3;
        convertView.setLayoutParams(params);
        ImageLoader.getInstance().displayImage(item.url, holder.iv, options);
        return convertView;
    }

    static class ViewHolder {
        ImageView iv;
    }
}

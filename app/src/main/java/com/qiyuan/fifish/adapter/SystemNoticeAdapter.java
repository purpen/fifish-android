package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.SystemNoticeData;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Util;

import java.util.List;

/**
 * @author lilin
 * created at 2016/5/4 19:24
 */
public class SystemNoticeAdapter extends BaseAdapter<SystemNoticeData.SystemNoticeItem>{
    private ImageLoader imageLoader;
    public SystemNoticeAdapter(List list, Activity activity){
        super(list,activity);
        this.imageLoader= ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SystemNoticeData.SystemNoticeItem item = list.get(position);
        ViewHolder holder;
        if (convertView==null){
            convertView = Util.inflateView(R.layout.item_system_notice, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        holder.title.setText(item.title);
        holder.tv_desc.setText(item.content);
        holder.tv_time.setText(item.created_at);
        if (item.is_unread){//未读
            holder.dot.setVisibility(View.VISIBLE);
        }else {
            holder.dot.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.cover_url)){
            holder.riv.setVisibility(View.VISIBLE);
            imageLoader.displayImage(item.cover_url,holder.riv,options);
        }else {
            holder.riv.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_desc)
        TextView tv_desc;
        @BindView(R.id.dot)
        TextView dot;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.riv)
        RoundedImageView riv;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

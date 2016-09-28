package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.CommentsBean;
import com.qiyuan.fifish.ui.activity.UserCenterActivity;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.Util;

import java.util.List;

/**
 * @author lilin
 * created at 2016/5/4 19:24
 */
public class FavoriteProductsAdapter extends BaseAdapter<CommentsBean.CommentItem>{
    private ImageLoader imageLoader;
    public FavoriteProductsAdapter(List list, Activity activity){
        super(list,activity);
        this.imageLoader= ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CommentsBean.CommentItem item = list.get(position);
        ViewHolder holder;
        if (convertView==null){
            convertView = Util.inflateView(R.layout.item_user_comments, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        imageLoader.displayImage(item.getUser().getSmall_avatar_url(),holder.riv,options);
        holder.tv_name.setText(item.getUser().getNickname());
        holder.tv_desc.setText(item.getContent());
        holder.tv_time.setText(item.getCreated_at());
        holder.iv.setVisibility(View.VISIBLE);
        if (item.is_unread){
            holder.dot.setVisibility(View.VISIBLE);
        }else {
            holder.dot.setVisibility(View.GONE);
        }
        imageLoader.displayImage(item.target_small_cover_url,holder.iv,options);
        holder.riv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, UserCenterActivity.class);
                intent.putExtra(Constants.USER_ID,item.user_id);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.riv)
        ImageView riv;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_desc)
        TextView tv_desc;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.dot)
        TextView dot;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

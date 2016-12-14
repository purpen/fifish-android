package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.UserCommentsBean;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/5/4 19:24
 */
public class UserCommentsAdapter extends BaseAdapter<UserCommentsBean.DataBean> {
    private ImageLoader imageLoader;

    public UserCommentsAdapter(List list, Activity activity) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UserCommentsBean.DataBean item = list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = Util.inflateView(R.layout.item_comments_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, activity.getResources().getDimensionPixelSize(R.dimen.dp64)));

        imageLoader.displayImage(item.sender.avatar.small, holder.riv, options);
        holder.tvTop.setText(item.sender.username);
        holder.tvTopRight.setText(item.created_at);
        holder.tvBottom.setText(item.content);
        holder.tvBottom.setTextColor(activity.getResources().getColor(R.color.color_222));
        holder.imageView.setVisibility(View.VISIBLE);
        imageLoader.displayImage(item.stuff.cover.file.thumb,holder.imageView, options);
//        holder.iv.setVisibility(View.VISIBLE);
//        if (item.is_unread){
//            holder.dot.setVisibility(View.VISIBLE);
//        }else {
//            holder.dot.setVisibility(View.GONE);
//        }
//        imageLoader.displayImage(item.target_small_cover_url,holder.iv,options);
//        holder.riv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(activity, UserCenterActivity.class);
//                intent.putExtra(Constants.USER_ID,item.user_id);
//                activity.startActivity(intent);
//            }
//        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.riv)
        RoundedImageView riv;
        @BindView(R.id.tv_top)
        TextView tvTop;
        @BindView(R.id.tv_top_right)
        TextView tvTopRight;
        @BindView(R.id.tv_bottom)
        TextView tvBottom;
        @BindView(R.id.imageView)
        ImageView imageView;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

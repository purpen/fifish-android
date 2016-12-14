package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.SupportedProductsBean;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/5/4 19:24
 */
public class FavoriteProductsAdapter extends BaseAdapter<SupportedProductsBean.DataBean> {
    private ImageLoader imageLoader;

    public FavoriteProductsAdapter(List list, Activity activity) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SupportedProductsBean.DataBean item = list.get(position);
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
        if (item.stuff.kind==1){
            holder.tvTopRight.setText(activity.getString(R.string.support_your)+activity.getString(R.string.picture_products));
        }else if (item.stuff.kind==2){
            holder.tvTopRight.setText(activity.getString(R.string.support_your)+activity.getString(R.string.video_products));
        }
        holder.tvBottom.setText(item.created_at);
        holder.tvBottom.setTextColor(activity.getResources().getColor(R.color.color_999));
        holder.imageView.setVisibility(View.VISIBLE);
        imageLoader.displayImage(item.stuff.cover.file.thumb,holder.imageView, options);
//        if (item.is_unread) {
//            holder.dot.setVisibility(View.VISIBLE);
//        } else {
//            holder.dot.setVisibility(View.GONE);
//        }
//        imageLoader.displayImage(item.target_small_cover_url, holder.iv, options);
//        holder.riv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity, UserCenterActivity.class);
//                intent.putExtra(Constants.USER_ID, item.user_id);
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

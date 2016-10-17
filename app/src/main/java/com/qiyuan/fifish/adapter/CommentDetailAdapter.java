package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.ProductsCommentBean;
import com.qiyuan.fifish.ui.activity.UserCenterActivity;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/5/4 19:24
 */
public class CommentDetailAdapter extends BaseAdapter<ProductsCommentBean.DataBean> {
    private ImageLoader imageLoader;
    private ProductsBean.DataEntity products;

    public CommentDetailAdapter(List list, Activity activity, ProductsBean.DataEntity products) {
        super(list, activity);
        this.products = products;
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductsCommentBean.DataBean item = list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = Util.inflateView(R.layout.item_products_comments, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(item.user.avatar.large, holder.riv, options);
        holder.tv_name.setText(item.user.username);
        holder.tv_name.setTextColor(activity.getResources().getColor(R.color.color_2187ff));
        if (item.user.summary != null) {
            holder.tv_desc.setText(item.user.summary.toString());
        }
        holder.tv_time.setText(item.created_at);
        holder.riv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UserCenterActivity.class);
                intent.putExtra(Constants.USER_ID, item.user.id);
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

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

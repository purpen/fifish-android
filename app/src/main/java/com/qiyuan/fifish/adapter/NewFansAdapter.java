package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
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
public class NewFansAdapter extends BaseAdapter<SupportedProductsBean.DataBean> {
    private ImageLoader imageLoader;

    public NewFansAdapter(List list, Activity activity) {
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
        if (!TextUtils.isEmpty(item.sender.summary)&&!TextUtils.equals("null",item.sender.summary)){
            holder.tvBottom.setText(item.sender.summary);
            holder.tvBottom.setTextColor(activity.getResources().getColor(R.color.color_999));
        }
        holder.btnFocus.setVisibility(View.VISIBLE);
        setClickListener(item,holder.btnFocus);
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


    //关注操作
    private void setClickListener(SupportedProductsBean.DataBean item, Button btnFocus) {
        // TODO
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
        @BindView(R.id.btn_focus)
        Button btnFocus;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

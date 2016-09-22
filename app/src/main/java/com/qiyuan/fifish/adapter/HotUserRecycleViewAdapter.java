package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.HotUserBean;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/5/8 18:38
 */
public class HotUserRecycleViewAdapter extends RecyclerView.Adapter<HotUserRecycleViewAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickLitener;

    public void setmOnItemClickLitener(OnItemClickListener itemClickLitener) {
        this.mOnItemClickLitener = itemClickLitener;
    }

    private Activity activity;
    private ArrayList<HotUserBean.DataBean> list;
    private DisplayImageOptions options;

    public HotUserRecycleViewAdapter(Activity activity, ArrayList<HotUserBean.DataBean> list) {
        this.activity = activity;
        this.list = list;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_background_750_1334)
                .showImageForEmptyUri(R.mipmap.default_background_750_1334)
                .showImageOnFail(R.mipmap.default_background_750_1334)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_hot_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HotUserBean.DataBean item = list.get(position);
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickLitener.onItemLongClick(holder.itemView, holder.getAdapterPosition());
                    return false;
                }
            });
        }
        ImageLoader.getInstance().displayImage(item.avatar.small, holder.iv, options);
        holder.tvTag.setText(item.username);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv)
        RoundedImageView iv;
        @Bind(R.id.tv_tag)
        TextView tvTag;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


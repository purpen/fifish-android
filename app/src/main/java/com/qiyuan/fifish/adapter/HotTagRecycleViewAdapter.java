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
import com.qiyuan.fifish.bean.TagsBean;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/5/8 18:38
 */
public class HotTagRecycleViewAdapter extends RecyclerView.Adapter<HotTagRecycleViewAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    private Activity activity;
    private ArrayList<TagsBean.DataEntity> list;
    private DisplayImageOptions options;

    public HotTagRecycleViewAdapter(Activity activity, ArrayList<TagsBean.DataEntity> list) {
        this.activity = activity;
        this.list = list;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_cover)
                .showImageForEmptyUri(R.mipmap.default_cover)
                .showImageOnFail(R.mipmap.default_cover)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_hot_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TagsBean.DataEntity item = list.get(position);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, holder.getAdapterPosition());
                    return false;
                }
            });
        }
        ImageLoader.getInstance().displayImage(item.cover.file.thumb, holder.iv, options);
        holder.tvTag.setText(item.display_name);
        holder.tvNum.setText(String.format("%s人参与",position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        RoundedImageView iv;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.tv_num)
        TextView tvNum;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


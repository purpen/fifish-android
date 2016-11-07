package com.qiyuan.fifish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.FilterMirror;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.GPUImageFilterTools;

import java.util.HashMap;
import java.util.List;


/**
 * Created by taihuoniao on 2016/3/21.
 */
public class FilterRecyclerAdapter extends RecyclerView.Adapter<FilterRecyclerAdapter.VH> {
    //    private Uri imageUri;
    private Context context;
    private GPUImageFilterTools.FilterList filterList;
    private GPUImageFilterTools.OnGpuImageFilterChosenListener listener;
    private int lastClick = -1;//上次点击的item
    private ItemClick itemClick;

    public FilterRecyclerAdapter(Context context, GPUImageFilterTools.OnGpuImageFilterChosenListener listener,
                                 ItemClick itemClick) {
//        this.imageUri = imageUri;
        this.context = context;
        this.filterList = GPUImageFilterTools.getList();
        this.listener = listener;
        this.itemClick = itemClick;
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_filter_recycler, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position!= lastClick) {
                    lastClick=position;
                    filterList.selectFilter(holder.getAdapterPosition());
                    listener.onGpuImageFilterChosenListener(GPUImageFilterTools.createFilterForType(context,
                            filterList.filters.get(holder.getAdapterPosition())), holder.getAdapterPosition());
                    notifyDataSetChanged();
                    lastClick = position;
                } else {
                    itemClick.click(holder.getAdapterPosition(),filterList.getName(position));
                    filterList.selectFilter(holder.getAdapterPosition());
                    listener.onGpuImageFilterChosenListener(GPUImageFilterTools.createFilterForType(context,
                            filterList.filters.get(holder.getAdapterPosition())), holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            }
        });
//        holder.imageView.setImage(imageUri);
//        final GPUImageFilter filter = GPUImageFilterTools.createFilterForType(context, filterList.filters.get(position));
//        final GPUImageFilterTools.FilterAdjuster filterAdjuster = new GPUImageFilterTools.FilterAdjuster(filter);
//        holder.imageView.setFilter(filter);
//        if (filterAdjuster.canAdjust()) {
//            filterAdjuster.adjust(50);
//        }
//        holder.imageView.requestRender();

        switch (filterList.getName(position)) {
            case "原图":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "摩卡":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "暮光":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "候鸟":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "戏剧":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "夏日":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "都市":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "佳人":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "摩登":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "流年":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "日光":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case "午茶":
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            default:
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                break;
        }
        holder.textView.setText(filterList.getName(position));
        if (filterList.isSelected(position)) {
            holder.redTv.setVisibility(View.VISIBLE);
            holder.textView.setTextColor(context.getResources().getColor(R.color.green_black_theme));
        } else {
            holder.redTv.setVisibility(View.GONE);
            holder.textView.setTextColor(context.getResources().getColor(R.color.white_theme));
        }
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public ImageView imageView;
        //        public GPUImageView imageView;
        public TextView textView;
        public TextView redTv;

        public VH(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_edit_recycler_filterimg);
            textView = (TextView) itemView.findViewById(R.id.item_edit_recycler_textview);
            redTv = (TextView) itemView.findViewById(R.id.item_edit_recycler_red);

        }
    }

    public interface ItemClick {
        void click(int postion,String typeName);
    }

    public interface FilterClick {
        void filterClick(int position);
    }
}

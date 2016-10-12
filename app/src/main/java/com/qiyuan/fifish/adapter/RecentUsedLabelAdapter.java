package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.TagsBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
public class RecentUsedLabelAdapter extends BaseAdapter<TagsBean.DataBean> {
    public RecentUsedLabelAdapter(ArrayList<TagsBean.DataBean> list,Activity activity) {
        super(list,activity);
    }

    @Override
    public int getCount() {
        return super.getCount()+1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_add_label, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position==0){
            holder.name.setTextColor(parent.getResources().getColor(R.color.color_999));
            holder.name.setText("推荐标签");
        }else {
            TagsBean.DataBean item =list.get(position-1);
            holder.name.setTextColor(parent.getResources().getColor(R.color.color_333));
            holder.name.setText("#" + item.display_name + " ");
        }

//        if (activeTagsBean == null) {
//            if (position == 0) {
//                holder.name.setTextColor(parent.getResources().getColor(R.color.color_999));
//                holder.name.setText("最近使用的标签");
//            } else {
//                holder.name.setTextColor(parent.getResources().getColor(R.color.title_black));
//                holder.name.setText("#" + list.get(position - 1) + " ");
//            }
//        } else {
//            if (position == 0) {
//                holder.name.setTextColor(parent.getResources().getColor(R.color.color_999));
//                holder.name.setText("推荐标签");
//            }
//            else if (position == activeTagsBean.getData().getItems().size() + 1) {
//                holder.name.setTextColor(parent.getResources().getColor(R.color.color_999));
//                holder.name.setText("最近使用的标签");
//            } else if (position <= activeTagsBean.getData().getItems().size()) {
//                holder.name.setTextColor(parent.getResources().getColor(R.color.title_black));
//                holder.name.setText("#" + activeTagsBean.getData().getItems().get(position - 1).get(0) + " ");
//            } else {
//                holder.name.setTextColor(parent.getResources().getColor(R.color.title_black));
//                holder.name.setText("#" + list.get(position - 2 - activeTagsBean.getData().getItems().size()) + " ");
//            }
//        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

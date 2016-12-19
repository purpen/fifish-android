package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/4/12 18:41
 */
public class PoiAddressAdapter extends CommonBaseAdapter<PoiItem> {

    public PoiAddressAdapter(Activity activity, List<PoiItem> list) {
        super(list,activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PoiItem item = list.get(position);
        ViewHolder holder;
        if (convertView==null){
            convertView = Util.inflateView(R.layout.item_poi_layout, null);
            holder=new ViewHolder(convertView);
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, activity.getResources().getDimensionPixelSize(R.dimen.dp60)));
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder)convertView.getTag();
        }
        holder.tv_short_name.setText(item.getTitle());
        holder.tv_poi_detail.setText(item.getSnippet());
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.tv_short_name)
        TextView tv_short_name;
        @BindView(R.id.tv_poi_detail)
        TextView tv_poi_detail;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}

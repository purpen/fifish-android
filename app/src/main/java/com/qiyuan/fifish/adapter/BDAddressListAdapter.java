package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/4/12 18:41
 */
public class BDAddressListAdapter extends BaseAdapter<PoiInfo> {

    public BDAddressListAdapter(Activity activity, ArrayList<PoiInfo> list) {
        super(list,activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PoiInfo info = list.get(position);
        ViewHolder holder;
        if (convertView==null){
            convertView = Util.inflateView(R.layout.item_poi_lv, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder)convertView.getTag();
        }
        holder.tv_short_name.setText(info.name);
        holder.tv_poi_detail.setText(info.address);
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

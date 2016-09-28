package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.SupportVideoBean;
import com.qiyuan.fifish.util.Util;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import java.util.ArrayList;

/**
 * @author lilin
 *         created at 2016/9/20 18:04
 */
public class SupportVideoAdapter extends BaseAdapter<SupportVideoBean> {
    public SupportVideoAdapter(ArrayList<SupportVideoBean> list, Activity activity) {
        super(list, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SupportVideoBean item = list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            View view = Util.inflateView(R.layout.item_video_view, null);
            holder = new ViewHolder(view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        convertView.setLayoutParams(params);
        holder.videoView.setUp(item.url, JCVideoPlayer.SCREEN_LAYOUT_LIST);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.videoView)
        JCVideoPlayerStandard videoView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

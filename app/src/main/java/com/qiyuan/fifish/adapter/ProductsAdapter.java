package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lilin
 *         created at 2016/4/22 19:00
 */
public class ProductsAdapter extends BaseAdapter<ProductsBean.DataEntity> implements View.OnClickListener {
    private ImageLoader imageLoader;
    private String userId;

    public ProductsAdapter(List<ProductsBean.DataEntity> list, Activity activity, String userId) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
        this.userId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductsBean.DataEntity item = list.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = Util.inflateView(activity, R.layout.item_image_video_view, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (item.kind){
            case "1":
                viewHolder.videoView.setVisibility(View.GONE);
                viewHolder.iv.setVisibility(View.VISIBLE);
                if (item.cover != null) {
                    imageLoader.displayImage(item.cover.file.large, viewHolder.iv, options);
                }
                break;
            case "2":
                viewHolder.iv.setVisibility(View.GONE);
                viewHolder.videoView.setVisibility(View.VISIBLE);
                viewHolder.videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (item.cover!=null){
                    viewHolder.videoView.setUp(item.cover.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
                    ImageLoader.getInstance().displayImage(item.cover.file.large,viewHolder.videoView.thumbImageView,options);
                }
                break;
            default:
                break;
        }
        return convertView;
    }

    @Override
    public void onClick(final View view) {

    }

    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.videoView)
        JCVideoPlayerStandard videoView;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

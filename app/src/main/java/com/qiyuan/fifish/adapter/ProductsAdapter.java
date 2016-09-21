package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.util.Util;

import java.util.List;

import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lilin
 *         created at 2016/4/22 19:00
 */
public class ProductsAdapter extends BaseAdapter<ProductsBean.DataBean> implements View.OnClickListener {
    private ImageLoader imageLoader;
    private String userId;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

    public ProductsAdapter(List<ProductsBean.DataBean> list, Activity activity, String userId) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
        this.userId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(list.get(position).kind);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        final ProductsBean.DataBean item = list.get(position);
        ImageHolder imageholder;
        VideoHolder videoholder;
        if (convertView == null) {
            switch (type) {
                case TYPE_VIDEO:
                    convertView = Util.inflateView(R.layout.item_video_view, null);
                    videoholder = new VideoHolder(convertView);
                    videoholder.videoView.setUp(item.photo.fileurl, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);
                    convertView.setTag(videoholder);
                    break;
                case TYPE_IMAGE:
                    imageholder = new ImageHolder();
                    AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.height = activity.getResources().getDimensionPixelSize(R.dimen.dp210);
                    convertView = imageholder.iv = new ImageView(activity);
                    convertView.setLayoutParams(params);
                    imageholder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageLoader.displayImage(item.photo.fileurl, imageholder.iv, options);
                    convertView.setTag(imageholder);
                    break;
                default:
                    break;
            }
        } else {
            switch (type) {
                case TYPE_VIDEO:
                    videoholder = (VideoHolder) convertView.getTag();
                    videoholder.videoView.setUp(item.photo.fileurl, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);
                    break;
                case TYPE_IMAGE:
                    imageholder = (ImageHolder) convertView.getTag();
                    imageLoader.displayImage(item.photo.fileurl, imageholder.iv, options);
                    break;
                default:
                    break;
            }
        }
        return convertView;
    }

    @Override
    public void onClick(final View view) {

    }

    static class ImageHolder {
        ImageView iv;
    }

    static class VideoHolder {
        JCVideoPlayerStandard videoView;

        public VideoHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

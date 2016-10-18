package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_VIDEO = 1;

    public ProductsAdapter(List<ProductsBean.DataEntity> list, Activity activity, String userId) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
        this.userId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals("1",list.get(position).kind)){
            return TYPE_IMAGE;
        }else {
            return TYPE_VIDEO;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductsBean.DataEntity item = list.get(position);
        ImageHolder imageHolder;
        VideoHolder videoHolder;
//        if (convertView == null) {
//            switch (type) {
//                case TYPE_VIDEO:
//                    convertView = Util.inflateView(activity,R.layout.item_video_view, null);
//                    videoholder = new VideoHolder(convertView);
//                    videoholder.videoView.setUp(item.photo.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,"");
//                    convertView.setTag(videoholder);
//                    break;
//                case TYPE_IMAGE:
//                    imageholder = new ImageHolder();
//                    AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    params.height = activity.getResources().getDimensionPixelSize(R.dimen.dp210);
//                    convertView = imageholder.iv = new ImageView(activity);
//                    convertView.setLayoutParams(params);
//                    imageholder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    imageLoader.displayImage(item.photo.file.large, imageholder.iv, options);
//                    convertView.setTag(imageholder);
//                    break;
//                default:
//                    break;
//            }
//        } else {
//            switch (type) {
//                case TYPE_VIDEO:
//                    videoholder = (VideoHolder) convertView.getTag();
//                    videoholder.videoView.setUp(item.photo.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,"");
//                    break;
//                case TYPE_IMAGE:
//                    imageholder = (ImageHolder) convertView.getTag();
//                    imageLoader.displayImage(item.photo.file.large, imageholder.iv, options);
//                    break;
//                default:
//                    break;
//            }
//        }
        switch (getItemViewType(position)) {
            case TYPE_IMAGE:
                if (convertView == null) {
                    imageHolder = new ImageHolder();
                    convertView = imageHolder.iv = new ImageView(activity);
                    convertView.setTag(imageHolder);
                } else {
                    imageHolder = (ImageHolder) convertView.getTag();
                }
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.height = activity.getResources().getDimensionPixelSize(R.dimen.dp210);
                convertView.setLayoutParams(params);
                imageHolder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageLoader.displayImage(item.photo.file.large, imageHolder.iv, options);
                break;
            case TYPE_VIDEO:
                if (convertView == null) {
                    convertView = Util.inflateView(activity, R.layout.item_video_view, null);
                    videoHolder = new VideoHolder(convertView);
                    convertView.setTag(videoHolder);
                }else {
                    videoHolder = (VideoHolder) convertView.getTag();
                }
                videoHolder.videoView.setUp(item.photo.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
                videoHolder.videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.getInstance().displayImage(item.photo.file.large,videoHolder.videoView.thumbImageView,options);
                break;
            default:
                break;
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
        @BindView(R.id.videoView)
        JCVideoPlayerStandard videoView;

        public VideoHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

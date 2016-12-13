package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
public class ProductsGridAdapter extends BaseAdapter<ProductsBean.DataEntity> implements View.OnClickListener {
    private ImageLoader imageLoader;
    private String userId;
    private int imageW;
    private LinearLayout.LayoutParams params;

    public ProductsGridAdapter(List<ProductsBean.DataEntity> list, Activity activity, String userId) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
        this.userId = userId;
        imageW = Util.getScreenWidth() - 2 * activity.getResources().getDimensionPixelSize(R.dimen.dp5);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageW / 3, 1);
    }

    @Override
    public int getCount() {
        int size = super.getCount();
        return (size % 3 == 0) ? size / 3 : (size / 3 + 1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = Util.inflateView(activity, R.layout.item_grid_image_video, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.rlCover0.setLayoutParams(params);
        viewHolder.rlCover1.setLayoutParams(params);
        viewHolder.rlCover2.setLayoutParams(params);

        ProductsBean.DataEntity itemLeft = list.get(3 * position);
        imageLoader.displayImage(itemLeft.cover.file.large, viewHolder.ivCover0, options);

        if ((3 * position + 1) < super.getCount()) {
            ProductsBean.DataEntity itemMid = list.get(3 * position + 1);
            viewHolder.ivCover1.setVisibility(View.VISIBLE);
            imageLoader.displayImage(itemMid.cover.file.large, viewHolder.ivCover1, options);
        } else {
            viewHolder.ivCover1.setVisibility(View.GONE);
        }

        if ((3 * position + 2) < super.getCount()) {
            ProductsBean.DataEntity itemRight = list.get(3 * position + 2);
            viewHolder.ivCover2.setVisibility(View.VISIBLE);
            imageLoader.displayImage(itemRight.cover.file.large, viewHolder.ivCover2, options);
        } else {
            viewHolder.ivCover2.setVisibility(View.GONE);
        }


//        switch (item.kind) {
//            case "1": //图片
//                viewHolder.iv.setVisibility(View.GONE);
//                viewHolder.iv.setVisibility(View.VISIBLE);
//                if (item.cover != null) {
//                    imageLoader.displayImage(item.cover.file.large, viewHolder.iv, options);
//                }
//                break;
//            case "2": //视频
//                viewHolder.iv.setVisibility(View.GONE);
//                viewHolder.videoView.setVisibility(View.VISIBLE);
//                viewHolder.videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                if (item.cover != null) {
//                    viewHolder.videoView.setUp(item.cover.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
//                    ImageLoader.getInstance().displayImage(item.cover.file.large, viewHolder.videoView.thumbImageView, options);
//                }
//                break;
//            default:
//                break;
//        }
        return convertView;
    }

    @Override
    public void onClick(final View view) {

    }

    static class ViewHolder {
        @BindView(R.id.iv_cover0)
        ImageView ivCover0;
        @BindView(R.id.iv_play0)
        ImageView ivPlay0;
        @BindView(R.id.rl_cover0)
        RelativeLayout rlCover0;
        @BindView(R.id.iv_cover1)
        ImageView ivCover1;
        @BindView(R.id.iv_play1)
        ImageView ivPlay1;
        @BindView(R.id.rl_cover1)
        RelativeLayout rlCover1;
        @BindView(R.id.iv_cover2)
        ImageView ivCover2;
        @BindView(R.id.iv_play2)
        ImageView ivPlay2;
        @BindView(R.id.rl_cover2)
        RelativeLayout rlCover2;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

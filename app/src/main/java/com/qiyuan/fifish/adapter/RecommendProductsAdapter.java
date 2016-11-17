package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.SupportProductsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.BottomSheetView;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lilin
 *         created at 2016/9/22 15:58
 */
public class RecommendProductsAdapter extends BaseAdapter<ProductsBean.DataEntity> {
    private ImageLoader imageLoader;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

    public RecommendProductsAdapter(List<ProductsBean.DataEntity> list, Activity activity) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductsBean.DataEntity item = list.get(position);
        Integer type = Integer.valueOf(item.kind);
        VideoHolder videoHolder;
        if (convertView == null) {
            convertView = Util.inflateView(activity,R.layout.item_dicover_products, null);
            videoHolder = new VideoHolder(convertView);
            convertView.setTag(videoHolder);
        } else {
            videoHolder = (VideoHolder) convertView.getTag();
        }
        switch (type) {
            case TYPE_VIDEO:
                videoHolder.videoView.setVisibility(View.VISIBLE);
                videoHolder.ivCover.setVisibility(View.GONE);
                videoHolder.videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (item.cover!=null){
                    videoHolder.videoView.setUp(item.cover.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,"");
                    ImageLoader.getInstance().displayImage(item.cover.file.large,videoHolder.videoView.thumbImageView,options);
                }
                break;
            case TYPE_IMAGE:
                videoHolder.videoView.setVisibility(View.GONE);
                videoHolder.ivCover.setVisibility(View.VISIBLE);
                if (item.cover!=null) imageLoader.displayImage(item.cover.file.large, videoHolder.ivCover, options);
                break;
            default:
                break;
        }
        imageLoader.displayImage(item.user.avatar.large, videoHolder.riv);
        videoHolder.tvName.setText(item.user.username);
        videoHolder.tvZanNum.setText(item.like_count + "次赞");
        if (item.user.summary != null) {
            videoHolder.tvDesc.setVisibility(View.VISIBLE);
            videoHolder.tvDesc.setText(item.user.summary.toString());
        } else {
            videoHolder.tvDesc.setVisibility(View.INVISIBLE);
        }
        videoHolder.tvTxt.setText(item.content);
        if (position == list.size() - 1) {
            videoHolder.viewLine.setVisibility(View.GONE);
        } else {
            videoHolder.viewLine.setVisibility(View.VISIBLE);
        }
        videoHolder.tvCommentNum.setText(String.format("所有%s条评论", item.comment_count));
        videoHolder.tvTime.setText(item.created_at);
        if (item.is_follow){
            setFocusBtnStyle(videoHolder.btnFocus, R.dimen.dp8, R.string.focused, R.mipmap.focused, android.R.color.white, R.drawable.shape_focus);
        }else {
            setFocusBtnStyle(videoHolder.btnFocus, R.dimen.dp15, R.string.focus, R.mipmap.unfocus, R.color.color_2187ff, R.drawable.shape_unfocus);
        }
        setClickListener(videoHolder.ibtnFavorite, item);
        setClickListener(videoHolder.ibtnComment, item);
        setClickListener(videoHolder.ibtnShare, item);
        setClickListener(videoHolder.ibtnMore, item);
        setClickListener(videoHolder.btnFocus, item);
        return convertView;
    }

    private void setClickListener(View v, final ProductsBean.DataEntity item) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ibtn_favorite:
                        if (item.is_love) {
                            cancelSupport(view, item);
                        } else {
                            doSupport(view, item);
                        }
                        break;
                    case R.id.ibtn_comment:
                        break;
                    case R.id.ibtn_share:

                        break;
                    case R.id.ibtn_more:
                        ArrayList<String> strings = new ArrayList<>();
                        strings.add("google");
                        strings.add("google");
                        strings.add("google");
                        strings.add("google");
                        BottomSheetView.show(activity, new SimpleTextAdapter(activity, strings), BottomSheetView.LINEAR_LAYOUT);
                        break;
                    case R.id.btn_focus:
                        if (item.is_follow) { //做取消关注
                            setFocusBtnStyle((Button) view, R.dimen.dp15, R.string.focus, R.mipmap.unfocus, R.color.color_2187ff, R.drawable.shape_unfocus);
                            cancelFocus(view,item);
                        } else {
                            setFocusBtnStyle((Button) view, R.dimen.dp8, R.string.focused, R.mipmap.focused, android.R.color.white, R.drawable.shape_focus);
                            doFocus(view,item);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void doFocus(final View view, final ProductsBean.DataEntity item) {
        view.setEnabled(false);
        RequestService.doFocus(item.user_id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                item.is_follow=true;
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    private void cancelFocus(final View view, final ProductsBean.DataEntity item) {
        view.setEnabled(false);
        RequestService.cancelFocus(item.user_id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
//                if (response.meta.status_code == Constants.HTTP_OK) {
                    item.is_follow = false;
                    notifyDataSetChanged();
//                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    

    private void cancelSupport(final View view, final ProductsBean.DataEntity item) {
        view.setEnabled(false);
        RequestService.cancelSupport(item.id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                SupportProductsBean response = JsonUtil.fromJson(result, SupportProductsBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    item.is_love = false;
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    private void doSupport(final View view, final ProductsBean.DataEntity item) {
        view.setEnabled(false);
        RequestService.doSupport(item.id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                SupportProductsBean response = JsonUtil.fromJson(result, SupportProductsBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    item.is_love = true;
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    private void setFocusBtnStyle(Button bt_focus, int dimensionPixelSize, int focus, int unfocus_pic, int color, int drawable) {
        dimensionPixelSize=activity.getResources().getDimensionPixelSize(dimensionPixelSize);
        bt_focus.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        bt_focus.setText(focus);
        bt_focus.setTextColor(activity.getResources().getColor(color));
        bt_focus.setBackgroundResource(drawable);
        bt_focus.setCompoundDrawablesWithIntrinsicBounds(unfocus_pic, 0, 0, 0);
    }

    static class VideoHolder {
        @BindView(R.id.riv)
        RoundedImageView riv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.btn_focus)
        Button btnFocus;
        @BindView(R.id.videoView)
        JCVideoPlayerStandard videoView;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.ibtn_favorite)
        ImageButton ibtnFavorite;
        @BindView(R.id.ibtn_comment)
        ImageButton ibtnComment;
        @BindView(R.id.ibtn_share)
        ImageButton ibtnShare;
        @BindView(R.id.ibtn_more)
        ImageButton ibtnMore;
        @BindView(R.id.tv_zan_num)
        TextView tvZanNum;
        @BindView(R.id.tv_txt)
        TextView tvTxt;
        @BindView(R.id.btn_more)
        TextView btnMore;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.view_line)
        View viewLine;

        public VideoHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

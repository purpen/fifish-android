package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.SearchProductsBean;
import com.qiyuan.fifish.bean.SupportProductsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.TagActivity;
import com.qiyuan.fifish.ui.view.BottomSheetView;
import com.qiyuan.fifish.ui.view.labelview.AutoLabelUI;
import com.qiyuan.fifish.ui.view.labelview.Label;
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

import static android.R.attr.tag;

/**
 * @author lilin
 *         created at 2016/9/22 15:58
 */
public class SearchProductsAdapter extends BaseAdapter<SearchProductsBean.DataBean> {
    private ImageLoader imageLoader;
    private static final String TYPE_IMAGE = "1";
    private static final String TYPE_VIDEO = "2";

    public SearchProductsAdapter(List<SearchProductsBean.DataBean> list, Activity activity) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SearchProductsBean.DataBean item = list.get(position);
        VideoHolder videoHolder;
        if (convertView == null) {
            convertView = Util.inflateView(activity,R.layout.item_dicover_products, null);
            videoHolder = new VideoHolder(convertView);
            convertView.setTag(videoHolder);
        } else {
            videoHolder = (VideoHolder) convertView.getTag();
        }
        switch (item.stuff.kind) {
            case TYPE_VIDEO:
                videoHolder.videoView.setVisibility(View.VISIBLE);
                videoHolder.ivCover.setVisibility(View.GONE);
                videoHolder.videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (item.stuff.cover!=null){
                    videoHolder.videoView.setUp(item.stuff.cover.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,"");
                    ImageLoader.getInstance().displayImage(item.stuff.cover.file.large,videoHolder.videoView.thumbImageView,options);
                }
                break;
            case TYPE_IMAGE:
                videoHolder.videoView.setVisibility(View.GONE);
                videoHolder.ivCover.setVisibility(View.VISIBLE);
                if (item.stuff.cover!=null) imageLoader.displayImage(item.stuff.cover.file.large, videoHolder.ivCover, options);
                break;
            default:
                break;
        }
        imageLoader.displayImage(item.stuff.user.avatar.large, videoHolder.riv);
        videoHolder.tvName.setText(item.stuff.user.username);
        if (item.stuff.is_love) {
            videoHolder.ibtnFavorite.setImageResource(R.mipmap.icon_support);
        } else {
            videoHolder.ibtnFavorite.setImageResource(R.mipmap.icon_unsupport);
        }
        if (item.stuff.like_count>0){
            videoHolder.tvZanNum.setVisibility(View.VISIBLE);
            videoHolder.tvZanNum.setText(String.valueOf(item.stuff.like_count));
        }else {
            videoHolder.tvZanNum.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.stuff.address)&&!TextUtils.equals("null",item.stuff.address)) {
            videoHolder.tvDesc.setVisibility(View.VISIBLE);
            videoHolder.tvDesc.setText(item.stuff.address);
        } else {
            videoHolder.tvDesc.setVisibility(View.INVISIBLE);
        }
        videoHolder.labelView.clear();
        for (SearchProductsBean.DataBean.StuffBean.TagBean tag:item.stuff.tags) {
            videoHolder.labelView.addLabel("#" + tag.name);
        }
        videoHolder.tvTxt.setText(item.content);
        if (position == list.size() - 1) {
            videoHolder.viewLine.setVisibility(View.GONE);
        } else {
            videoHolder.viewLine.setVisibility(View.VISIBLE);
        }
        videoHolder.tvCommentNum.setText(String.format("所有%s条评论", item.stuff.comment_count));
        videoHolder.tvTime.setText(item.stuff.created_at);
        if (item.stuff.is_follow){
            setFocusBtnStyle(videoHolder.btnFocus, R.dimen.dp8, R.string.focused, R.mipmap.focused, android.R.color.white, R.drawable.shape_focus);
        }else {
            setFocusBtnStyle(videoHolder.btnFocus, R.dimen.dp15, R.string.focus, R.mipmap.unfocus, R.color.color_2187ff, R.drawable.shape_unfocus);
        }
        setClickListener(videoHolder.ibtnFavorite, item);
        setClickListener(videoHolder.ibtnComment, item);
        setClickListener(videoHolder.ibtnShare, item);
        setClickListener(videoHolder.ibtnMore, item);
        setClickListener(videoHolder.btnFocus, item);
        videoHolder.labelView.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(Label labelClicked) {
                Intent intent = new Intent(activity, TagActivity.class);
                String txt = labelClicked.getText().substring(1);
                if (!TextUtils.isEmpty(txt)) {
                    intent.putExtra(TagActivity.class.getSimpleName(), txt);
                    activity.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    private void setClickListener(View v, final SearchProductsBean.DataBean item) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ibtn_favorite:
                        if (item.stuff.is_love) {
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
                        if (item.stuff.is_follow) { //做取消关注
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

    private void doFocus(final View view, final SearchProductsBean.DataBean item) {
        view.setEnabled(false);
        RequestService.doFocus(item.user_id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                item.stuff.is_follow=true;
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

    private void cancelFocus(final View view, final SearchProductsBean.DataBean item) {
        view.setEnabled(false);
        RequestService.cancelFocus(item.user_id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
//                if (response.meta.status_code == Constants.HTTP_OK) {
                    item.stuff.is_follow = false;
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

    

    private void cancelSupport(final View view, final SearchProductsBean.DataBean item) {
        view.setEnabled(false);
        RequestService.cancelSupport(item.stuff.id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                SupportProductsBean response = JsonUtil.fromJson(result, SupportProductsBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    item.stuff.is_love = false;
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

    private void doSupport(final View view, final SearchProductsBean.DataBean item) {
        view.setEnabled(false);
        RequestService.doSupport(item.stuff.id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                SupportProductsBean response = JsonUtil.fromJson(result, SupportProductsBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    item.stuff.is_love = true;
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
        @BindView(R.id.label_view)
        AutoLabelUI labelView;
        public VideoHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

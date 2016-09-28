package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.CommentDetailAdapter;
import com.qiyuan.fifish.adapter.SimpleTextAdapter;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.ProductsCommentBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.BottomSheetView;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * @author lilin
 *         created at 2016/5/4 19:17
 */
public class CommentsDetailActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.pull_lv)
    PullToRefreshListView pullLv;
    private int curPage = 1;
    private ArrayList<ProductsCommentBean.DataBean> mList;
    private WaitingDialog dialog;
    private ProductsBean.DataBean products;
    private CommentDetailAdapter adapter;
    private RoundedImageView riv;
    private TextView tvName;
    private TextView tvDesc;
    private TextView tvTime;
    private JCVideoPlayerStandard videoView;
    private ImageView ivCover;
    private ImageButton ibtnFavorite;
    private ImageButton ibtnComment;
    private ImageButton ibtnShare;
    private ImageButton ibtnMore;
    private TextView tvContent;

    public CommentsDetailActivity() {
        super(R.layout.activity_comment_detail);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(TAG)) {
            products = (ProductsBean.DataBean) intent.getSerializableExtra(TAG);
        }
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true, R.string.title_comment);
        dialog = new WaitingDialog(this);
        mList=new ArrayList<>();
        initHeadView();
        pullLv.setMode(PullToRefreshBase.Mode.DISABLED);
        pullLv.setAdapter(adapter);
    }

    private void initHeadView() {
        View view = Util.inflateView(R.layout.item_home_video, null);
        pullLv.getRefreshableView().addHeaderView(view);
        riv = ButterKnife.findById(view, R.id.riv);
        tvName = ButterKnife.findById(view, R.id.tv_name);
        tvDesc = ButterKnife.findById(view, R.id.tv_desc);
        tvTime = ButterKnife.findById(view, R.id.tv_time);
        videoView = ButterKnife.findById(view, R.id.videoView);
        ivCover = ButterKnife.findById(view, R.id.iv_cover);
        ibtnFavorite = ButterKnife.findById(view, R.id.ibtn_favorite);
        ibtnComment = ButterKnife.findById(view, R.id.ibtn_comment);
        ibtnShare = ButterKnife.findById(view, R.id.ibtn_share);
        ibtnMore = ButterKnife.findById(view, R.id.ibtn_more);
        tvContent = ButterKnife.findById(view, R.id.tv_content);
        ButterKnife.findById(view, R.id.view_line).setVisibility(View.GONE);
        ImageLoader.getInstance().displayImage(products.user.avatar.large,riv,options);
        tvName.setText(products.user.username);
        if (products.user.summary!=null){
            tvDesc.setText(products.user.summary.toString());
        }
        tvTime.setText(products.created_at);
        if (TextUtils.equals(Constants.TYPE_IMAGE,products.kind)){
            videoView.setVisibility(View.GONE);
            ivCover.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(products.photo.file.large,ivCover, options);
        }else if(TextUtils.equals(Constants.TYPE_VIDEO,products.kind)){
            videoView.setVisibility(View.VISIBLE);
            ivCover.setVisibility(View.GONE);
            videoView.setUp(products.photo.file.large, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);
        }
        tvContent.setText(products.content);
    }

    @Override
    protected void installListener() {
        riv.setOnClickListener(this);
        ibtnFavorite.setOnClickListener(this);
        ibtnComment.setOnClickListener(this);
        ibtnShare.setOnClickListener(this);
        ibtnMore.setOnClickListener(this);
        tvContent.setOnClickListener(this);
        pullLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mList.size()==0) return;
                ProductsCommentBean.DataBean item = adapter.getItem(i-1);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibtn_favorite:

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
                BottomSheetView.show(activity,new SimpleTextAdapter(activity,strings),BottomSheetView.LINEAR_LAYOUT);
                break;
            case R.id.tv_content:

                break;
            default:
                break;
        }
    }

    @Override
    protected void requestNet() {
        if (products == null) return;
        RequestService.getProductsComments(products.id, new CustomCallBack() {
            @Override
            public void onStarted() {
                if (!activity.isFinishing() && dialog != null) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                if (!activity.isFinishing() && dialog != null) dialog.dismiss();
                ProductsCommentBean response = JsonUtil.fromJson(result, ProductsCommentBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    ArrayList<ProductsCommentBean.DataBean> list = response.data;
                    refreshUI(list);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!activity.isFinishing() && dialog != null) dialog.dismiss();
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    @Override
    protected void refreshUI(ArrayList list) {
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        if (adapter == null) {
            adapter = new CommentDetailAdapter(mList, activity,products);
            pullLv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

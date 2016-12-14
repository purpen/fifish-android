package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.CommentDetailAdapter;
import com.qiyuan.fifish.adapter.SimpleTextAdapter;
import com.qiyuan.fifish.bean.LoginUserInfo;
import com.qiyuan.fifish.bean.PostCommentBean;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.ProductsCommentBean;
import com.qiyuan.fifish.bean.ProductsDetailBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * @author lilin
 *         created at 2016/5/4 19:17
 */
public class CommentsDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.pull_lv)
    PullToRefreshListView pullLv;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.btn_send)
    Button btnSend;

    private int curPage = 1;
    private ArrayList<ProductsCommentBean.DataBean> mList;
    private WaitingDialog dialog;
    private ProductsBean.DataEntity products;
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
    private TextView tvVideoTime;
    private View videoContainer;
    private String reply_user_id;
    private String parent_id;
    private String id;
    public CommentsDetailActivity() {
        super(R.layout.activity_comment_detail);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(TAG)) {
            products = (ProductsBean.DataEntity) intent.getSerializableExtra(TAG);
            this.id=products.id;
        }

        if (intent.hasExtra("id")){
            id=intent.getStringExtra("id");
        }
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true, R.string.title_comment);
        dialog = new WaitingDialog(this);
        mList = new ArrayList<>();
        View view = Util.inflateView(R.layout.item_home_video, null);
        pullLv.getRefreshableView().addHeaderView(view);
        riv = ButterKnife.findById(view, R.id.riv);
        videoContainer = ButterKnife.findById(view, R.id.video_container);
        tvName = ButterKnife.findById(view, R.id.tv_name);
        tvDesc = ButterKnife.findById(view, R.id.tv_desc);
        videoView = ButterKnife.findById(view, R.id.videoView);
        tvTime = ButterKnife.findById(view, R.id.tv_time);
        tvVideoTime = ButterKnife.findById(view, R.id.tv_video_time);
        ivCover = ButterKnife.findById(view, R.id.iv_cover);
        ibtnFavorite = ButterKnife.findById(view, R.id.ibtn_favorite);
        ibtnComment = ButterKnife.findById(view, R.id.ibtn_comment);
        ibtnShare = ButterKnife.findById(view, R.id.ibtn_share);
        ibtnMore = ButterKnife.findById(view, R.id.ibtn_more);
        tvContent = ButterKnife.findById(view, R.id.tv_content);
        ButterKnife.findById(view, R.id.view_line).setVisibility(View.GONE);
        pullLv.setMode(PullToRefreshBase.Mode.DISABLED);
        pullLv.setAdapter(adapter);
        if(products!=null){
            initHeadView();
        }else {
            if (TextUtils.isEmpty(id)) return;
            RequestService.getProductsDetail(id,new CustomCallBack(){
                @Override
                public void onSuccess(String result) {
                    ProductsDetailBean productsDetailBean = JsonUtil.fromJson(result, ProductsDetailBean.class);
                    if (productsDetailBean.meta.status_code==Constants.HTTP_OK){
                        initHeadView(productsDetailBean);
                        return;
                    }
                    ToastUtils.showError(R.string.request_error);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    ToastUtils.showError(R.string.request_error);
                }
            });
        }
    }

    private void initHeadView(ProductsDetailBean productsDetailBean) {
        if (productsDetailBean==null) return;
        ImageLoader.getInstance().displayImage(productsDetailBean.data.user.avatar.large, riv, options);
        tvName.setText(productsDetailBean.data.user.username);
        if (!TextUtils.isEmpty(productsDetailBean.data.user.summary)&&!TextUtils.equals("null",productsDetailBean.data.user.summary)) {
            tvDesc.setText(productsDetailBean.data.user.summary);
        }
        tvTime.setText(productsDetailBean.data.created_at);
        if (TextUtils.equals(Constants.TYPE_IMAGE, productsDetailBean.data.kind)) {
            videoContainer.setVisibility(View.GONE);
            ivCover.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(productsDetailBean.data.cover.file.large, ivCover, options);
        } else if (TextUtils.equals(Constants.TYPE_VIDEO, productsDetailBean.data.kind)) {
            videoContainer.setVisibility(View.VISIBLE);
            ivCover.setVisibility(View.GONE);
            videoView.setUp(productsDetailBean.data.cover.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
            videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            tvVideoTime.setText(Util.second2Hour((int) productsDetailBean.data.cover.duration));
            ImageLoader.getInstance().displayImage(productsDetailBean.data.cover.file.large, videoView.thumbImageView, options);
        }
        tvContent.setText(productsDetailBean.data.content);
    }

    private void initHeadView() {
        ImageLoader.getInstance().displayImage(products.user.avatar.large, riv, options);
        tvName.setText(products.user.username);
        if (!TextUtils.isEmpty(products.user.summary)&&!TextUtils.equals("null",products.user.summary)) {
            tvDesc.setText(products.user.summary);
        }
        tvTime.setText(products.created_at);
        if (TextUtils.equals(Constants.TYPE_IMAGE, products.kind)) {
            videoContainer.setVisibility(View.GONE);
            ivCover.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(products.cover.file.large, ivCover, options);
        } else if (TextUtils.equals(Constants.TYPE_VIDEO, products.kind)) {
            videoContainer.setVisibility(View.VISIBLE);
            ivCover.setVisibility(View.GONE);
            videoView.setUp(products.cover.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
            videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            tvVideoTime.setText(Util.second2Hour((int) products.cover.duration));
            ImageLoader.getInstance().displayImage(products.cover.file.large, videoView.thumbImageView, options);
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
        btnSend.setOnClickListener(this);
        pullLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mList.size() == 0) return;
                if (i<2) return;
                ProductsCommentBean.DataBean item = adapter.getItem(i - 2);
                String prefix = String.format(activity.getResources().getString(R.string.reply_to) + ":%s ", item.user.username);
                et.setHint(prefix);
                reply_user_id=item.user.id; //点击后为我回复这个用户
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send://提交评论
                if (!LoginUserInfo.isUserLogin()){
                    ToastUtils.showInfo("请先登录");
                    //跳转登录界面
                    return;
                }

                String content=et.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showInfo(R.string.input_reply_content);
                    return;
                }

                RequestService.postComment(content,id,reply_user_id,parent_id,new CustomCallBack(){
                    @Override
                    public void onSuccess(String result) {
                        PostCommentBean postCommentBean = JsonUtil.fromJson(result, PostCommentBean.class);
                        if (postCommentBean.meta.status_code==Constants.HTTP_OK){
                            et.getText().clear();
                            requestNet();
                            return;
                        }

                        if (postCommentBean.meta.errors!=null){
                            if (postCommentBean.meta.errors.content!=null&&postCommentBean.meta.errors.content.size()>0)
                            ToastUtils.showInfo(postCommentBean.meta.errors.content.get(0));
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ex.printStackTrace();
                        ToastUtils.showError(R.string.request_error);
                    }
                });
                break;
            case R.id.ibtn_favorite:

                break;
            case R.id.ibtn_comment:

                break;
            case R.id.ibtn_share:

                break;
            case R.id.ibtn_more:
                final BottomSheetDialog dialog = new BottomSheetDialog(activity);
                View bottomView = Util.inflateView(R.layout.view_bottom_list, null);
                bottomView.findViewById(R.id.tv_title).setVisibility(View.GONE);
                RecyclerView recyclerView = (RecyclerView) bottomView.findViewById(R.id.bottom_sheet_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                String[] stringArray = getResources().getStringArray(R.array.dialog_bottom);
                SimpleTextAdapter adapter = new SimpleTextAdapter(activity, Arrays.asList(stringArray));
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        switch (position) {
                            case 0: //举报
                                final BottomSheetDialog dialogReport = new BottomSheetDialog(activity);
                                View bottomView = Util.inflateView(R.layout.view_bottom_list, null);
                                bottomView.findViewById(R.id.tv_title).setVisibility(View.GONE);
                                RecyclerView recyclerView = (RecyclerView) bottomView.findViewById(R.id.bottom_sheet_recycler_view);
                                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                                String[] stringArray = getResources().getStringArray(R.array.dialog_report);
                                SimpleTextAdapter textAdapter = new SimpleTextAdapter(activity, Arrays.asList(stringArray));
                                recyclerView.setAdapter(textAdapter);
                                textAdapter.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        switch (position) {
                                            case 0:

                                                break;
                                            case 1:
                                                break;
                                            case 2:
                                                dialogReport.dismiss();
                                                break;
                                            default:
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onItemLongClick(View view, int position) {

                                    }
                                });
                                dialogReport.setContentView(bottomView);
                                dialogReport.show();
                                break;
                            case 1:

                                break;
                            case 2:
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                dialog.setContentView(bottomView);
                dialog.show();
                break;
            case R.id.tv_content:

                break;
            default:
                break;
        }
    }

    @Override
    protected void requestNet() {
        if(TextUtils.isEmpty(id)) return;
        RequestService.getProductsComments(id, new CustomCallBack() {
            @Override
            public void onStarted() {
                if (!activity.isFinishing() && dialog != null) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                if (!activity.isFinishing() && dialog != null) dialog.dismiss();
                ProductsCommentBean response = JsonUtil.fromJson(result, ProductsCommentBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    List<ProductsCommentBean.DataBean> list = response.data;
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
    protected void refreshUI(List list) {
        if (list == null || list.size() == 0) return;
        if (mList.size()>0) mList.clear();
        mList.addAll(list);
        if (adapter == null) {
            adapter = new CommentDetailAdapter(mList, activity, products);
            pullLv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

}

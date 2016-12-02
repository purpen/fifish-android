package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.Image;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ShareAdapter;
import com.qiyuan.fifish.adapter.SuggestionAdapter;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.PublishProductsBean;
import com.qiyuan.fifish.bean.QNBean;
import com.qiyuan.fifish.bean.ShareItem;
import com.qiyuan.fifish.bean.TagsBean;
import com.qiyuan.fifish.bean.UploadImgVideoBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.fragment.MediaInnerFragment;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.GridSpacingItemDecoration;
import com.qiyuan.fifish.ui.view.labelview.AutoLabelUI;
import com.qiyuan.fifish.ui.view.labelview.Label;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布图片
 *
 * @author lilin
 *         created at 2016/10/9 9:06
 */
public class PublishPictureActivity extends BaseActivity implements ShareAdapter.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.et_share_txt)
    EditText et_share_txt;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_add_tag)
    TextView tvAddTag;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;
    @BindView(R.id.label_view)
    AutoLabelUI labelView;
    private String content;
    private String address;
    private ArrayList<String> tags;
    private String token;
    private String uploadUrl;
    private ProductsBean.DataEntity item;
    private Image selectImg;
    private int[] images = {R.mipmap.share_wechat, R.mipmap.share_sina, R.mipmap.share_qq, R.mipmap.share_facebook, R.mipmap.share_instgram};

    public PublishPictureActivity() {
        super(R.layout.activity_share_picture);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(TAG)) {
            item = (ProductsBean.DataEntity) intent.getSerializableExtra(TAG);
        }

        if (intent.hasExtra(MediaInnerFragment.class.getSimpleName())) {
            selectImg = (Image) intent.getSerializableExtra(MediaInnerFragment.class.getSimpleName());
        }
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true, R.string.title_share);
        custom_head.setHeadRightTxtShow(true, R.string.publish_products);
        custom_head.getHeadRightTV().setTextColor(getResources().getColor(R.color.color_2187ff));
        if (selectImg != null) {
            if (selectImg.isVideo) {
                ivPlay.setVisibility(View.VISIBLE);
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(selectImg.path);
                Bitmap bitmap = mmr.getFrameAtTime(0);
                ivCover.setImageBitmap(bitmap);
                mmr.release();
                et_share_txt.setHint(R.string.add_video_des);
            } else {
                ivPlay.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage("file:///" + selectImg.path, ivCover, options);
                et_share_txt.setHint(R.string.add_pic_des);
            }
        }

        String[] strings = getResources().getStringArray(R.array.share_way);
        ArrayList<ShareItem> shareList = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            ShareItem shareItem = new ShareItem();
            shareItem.pic = images[i];
            shareItem.txt = strings[i];
            shareList.add(shareItem);
        }
        ShareAdapter shareAdapter = new ShareAdapter(activity, shareList);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration());
        recyclerView.setAdapter(shareAdapter);
        shareAdapter.setmOnItemClickListener(this);
    }

    @Override
    protected void installListener() {
        custom_head.getHeadRightTV().setOnClickListener(this);
    }

    @Override
    protected void requestNet() {
        if (selectImg.isVideo) {
            RequestService.getVideoToken(new CustomCallBack() {
                @Override
                public void onSuccess(String result) {
                    QNBean response = JsonUtil.fromJson(result, QNBean.class);
                    if (response.meta.status_code == Constants.HTTP_OK) {
                        token = response.data.token;
                        uploadUrl = response.data.upload_url;
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    ToastUtils.showError(R.string.request_error);
                }
            });
        } else {
            RequestService.getPhotoToken(new CustomCallBack() {
                @Override
                public void onSuccess(String result) {
                    QNBean response = JsonUtil.fromJson(result, QNBean.class);
                    if (response.meta.status_code == Constants.HTTP_OK) {
                        token = response.data.token;
                        uploadUrl = response.data.upload_url;
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    ToastUtils.showError(R.string.request_error);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_head_right:
                content = et_share_txt.getText().toString();
                if (TextUtils.isEmpty(content) || content.length() < 5) {
                    ToastUtils.showInfo(R.string.publish_content_length);
                    return;
                }
                upLoadProducts(view);
                break;
            default:
                break;
        }
    }

    /**
     * 上传作品
     *
     * @param view
     */
    private void upLoadProducts(final View view) {//上传的本地视频
        LogUtil.e("token==" + token + "uploadUrl===" + uploadUrl);
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(uploadUrl)) return;
//        final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/app_tmp.mp4");
//        try {
//            FileUtil.inputStreamToFile(getResources().openRawResource(R.raw.video), file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        LogUtil.e(selectImg.path);
        File file = new File(selectImg.path);
        LogUtil.e("" + file.exists());
        view.setEnabled(false);
        RequestService.upLoadFile(file, token, uploadUrl, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                UploadImgVideoBean response = JsonUtil.fromJson(result, UploadImgVideoBean.class);
                if (TextUtils.equals(response.ret, "success")) {
                    LogUtil.e("视频上传成功");
                    addNewProducts(response.id);
                }
            }

            //上传进度
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("total==" + total + ";;current==" + current);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

//    private void upLoadPicture(final View view) {//上传的本地图片
//        final File file = new File("");
//        view.setEnabled(false);
//        RequestService.upLoadFile(file, token, uploadUrl, new CustomCallBack() {
//            @Override
//            public void onSuccess(String result) {
//                LogUtil.e(result);
//                view.setEnabled(true);
//                UploadImgVideoBean response = JsonUtil.fromJson(result, UploadImgVideoBean.class);
//                if (TextUtils.equals(response.ret, "success")) {
//                    addNewProducts(response.id);
//                }
//            }
//
//            //上传进度
//            @Override
//            public void onLoading(long total, long current, boolean isDownloading) {
//                super.onLoading(total, current, isDownloading);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                view.setEnabled(true);
//                ex.printStackTrace();
//                ToastUtils.showError(R.string.request_error);
//            }
//        });
//    }

    /**
     * 上传作品信息
     *
     * @param asset_id
     */
    private void addNewProducts(String asset_id) {
        double lat = 0;
        double lng = 0;
        LogUtil.e("asset_id===" + asset_id);
        List<Label> labels = labelView.getLabels();
        tags = new ArrayList<>();
        for (Label label : labels) {
            tags.add(label.getText().substring(1));
        }
        LogUtil.e("Tags==" + tags.toArray(new String[tags.size()])[0]);
        RequestService.addNewProducts(content, asset_id, "", "", String.valueOf(lat), String.valueOf(lng), "2", tags.toArray(new String[tags.size()]), new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e(result);
                PublishProductsBean response = JsonUtil.fromJson(result, PublishProductsBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    LogUtil.e("作品发布成功");
                    ToastUtils.showSuccess(R.string.publish_success);
                    finish();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }


    @OnClick({R.id.label_view, R.id.tv_add_tag, R.id.tv_add_address})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.label_view:
            case R.id.tv_add_tag:
                startActivityForResult(new Intent(activity, AddLabelActivity.class), Constants.REQUEST_LABEL);
                break;
            case R.id.tv_add_address:
                startActivityForResult(new Intent(activity, MapSearchAddressActivity.class), Constants.REQUEST_ADDRESS);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0://wechat
                share(SHARE_MEDIA.WEIXIN,"测试");
                break;
            case 1: //sina
                share(SHARE_MEDIA.SINA,"测试");
                break;
            case 2: //qq
                share(SHARE_MEDIA.QQ,"测试");
                break;
            case 3: //facebook
                share(SHARE_MEDIA.FACEBOOK,"测试");
                break;
            case 4: //tumblr
                break;
            case 5://whatapp
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private void share(SHARE_MEDIA platform,String content){
        new ShareAction(activity).setPlatform(platform)
                .withText(content)
                .setCallback(umShareListener)
                .share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            Toast.makeText(activity, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case Constants.REQUEST_LABEL:
                String searchTag = data.getStringExtra(SuggestionAdapter.class.getSimpleName());
                if (!TextUtils.isEmpty(searchTag)) {
                    labelView.addLabel("#" + searchTag);
                }

                String enterTag = data.getStringExtra(AddLabelActivity.class.getSimpleName());
                if (!TextUtils.isEmpty(enterTag)) {
                    labelView.addLabel("#" + enterTag);
                }

                TagsBean.DataEntity item = data.getParcelableExtra(AddLabelActivity.class.getSimpleName());
                if (item != null) {
                    labelView.addLabel("#" + item.display_name);
                }
                if (labelView.getLabels().size() > 0) {
                    tvAddTag.setVisibility(View.GONE);
                } else {
                    tvAddTag.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

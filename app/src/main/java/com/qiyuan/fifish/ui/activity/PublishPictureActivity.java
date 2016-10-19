package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.GridSpacingItemDecoration;
import com.qiyuan.fifish.ui.view.labelview.AutoLabelUI;
import com.qiyuan.fifish.ui.view.labelview.Label;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

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
    private String[] tags;
    private String token;
    private String uploadUrl;
    private ProductsBean.DataEntity item;
    private int[] images = {R.mipmap.share_wechat, R.mipmap.share_sina, R.mipmap.share_qq, R.mipmap.share_facebook, R.mipmap.share_tumblr, R.mipmap.share_whatapp};

    public PublishPictureActivity() {
        super(R.layout.activity_share_picture);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(TAG)) {
            item = (ProductsBean.DataEntity) intent.getSerializableExtra(TAG);
        }
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true, R.string.title_share);
        custom_head.setHeadRightTxtShow(true, R.string.publish_products);
        custom_head.getHeadRightTV().setTextColor(getResources().getColor(R.color.color_2187ff));
//        ImageLoader.getInstance().displayImage(item.photo.file.large, ivCover, options);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_head_right:
                upLoadPicture(view);
                break;
            default:
                break;
        }
    }

    private void upLoadPicture(final View view) {//上传的本地图片
        final File file = new File("");
        view.setEnabled(false);
        RequestService.upLoadFile(file,token,uploadUrl, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e(result);
                view.setEnabled(true);
                UploadImgVideoBean response = JsonUtil.fromJson(result, UploadImgVideoBean.class);
                if (TextUtils.equals(response.ret, "success")) {
                    addNewProducts(response.id);
                }
            }

            //上传进度
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                super.onLoading(total, current, isDownloading);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    /**
     * 上传作品信息
     * @param asset_id
     */
    private void addNewProducts(String asset_id) {
        LogUtil.e("asset_id==="+asset_id);
        double lat=0;
        double lng=0;
        List<Label> labels = labelView.getLabels();
        tags=new String[]{};
        for (int i=0;i<labels.size();i++){
            tags[i]=labels.get(i).getText();
        }
        RequestService.addNewProducts(content,asset_id,"","",String.valueOf(lat),String.valueOf(lng),"1",tags,new CustomCallBack(){
            @Override
            public void onSuccess(String result) {
                PublishProductsBean response = JsonUtil.fromJson(result, PublishProductsBean.class);
                if (response.meta.status_code==Constants.HTTP_OK){
                    LogUtil.e("图片发布成功");
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

                break;
            case 1: //sina
                break;
            case 2: //qq
                break;
            case 3: //facebook
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

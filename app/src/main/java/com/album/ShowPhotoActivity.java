package com.album;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bean.Image;
import com.customview.ClipZoomImageView;
import com.customview.GlobalTitleLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.application.AppApplication;
import com.qiyuan.fifish.ui.activity.BaseActivity;
import com.qiyuan.fifish.ui.view.imageCrop.ClipImageLayout;
import com.qiyuan.fifish.util.MySQLiteOpenHelper;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowPhotoActivity extends BaseActivity {
    @BindView(R.id.title_show_photo_activity)
    GlobalTitleLayout mTitle;
    //此控件不具备完全展示功能,即如果图片大于屏幕,则超出部分只能手动滑过去查看,如果事先用了centerinside,控件又会丧失缩放能力,时间关系,后面再行研究
    @BindView(R.id.image_show_photo_activity)
    ClipZoomImageView mIVClip;
//    ClipImageLayout mIVClip;

//    @BindView(R.id.image)
//    MatrixImageView mIVClip;
//    @BindView(R.id.imageview)
//    ImageView mIVClip;
    @BindView(R.id.image_dustbin_show_photo_activity)
    ImageView mIVDustbin;
    private Image imageBean;
    private MySQLiteOpenHelper dbHelper = null;

    public ShowPhotoActivity() {
        super(R.layout.activity_show_photo);
    }

    @Override
    protected void getIntentData() {
        imageBean = (Image) getIntent().getSerializableExtra("ImageBean");
    }

    @Override
    protected void initViews() {
        mTitle.showTitle(false);
        dbHelper = new MySQLiteOpenHelper(this);
        Uri urii = imageBean.path.startsWith("file:") ? Uri.parse(imageBean.path) : Uri.parse("file://" + imageBean.path);
//        Log.e(">>", urii + ">>>DD>>" + new File(imageBean.path) + ">>>>>>" + Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageBean.id + ""));

//        Picasso.with(this)
//                .load(new File(imageBean.path))
        // >>>DD>>file:///storage/emulated/0/MIUI/wallpaper/性感美女 (7)_&_eebc8557-3eca-45be-8bd3-c6efb90c2b91.jpg
        //>>>DD>>/storage/emulated/0/MIUI/wallpaper/性感美女 (7)_&_eebc8557-3eca-45be-8bd3-c6efb90c2b91.jpg
        //>>>>>>content://media/external/images/media/452096
//                .load(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageBean.id + ""))
//                .load(urii)
//                .placeholder(R.mipmap.default_album_option)
//                .resizeDimen(R.dimen.dp180, R.dimen.dp180)
//                .centerCrop()
//                .into(mIVClip);
//        mIVClip.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ImageLoader.getInstance().loadImage(urii.toString(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                mIVClip.setImage(loadedImage);
                mIVClip.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.destroy();
    }

    @OnClick({R.id.image_dustbin_show_photo_activity, R.id.tv_edit_share_show_photo_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_dustbin_show_photo_activity:
                Toast toast = Toast.makeText(this, R.string.delete_ok, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                dbHelper.delete(imageBean);
                finish();
                break;
            case R.id.tv_edit_share_show_photo_activity:
                //
                AppApplication.originalBitmap = mIVClip.clip();
                Intent intent = new Intent();
                intent.setClass(this, FilterMirrorActivity.class);
                intent.putExtra("ImageBean", imageBean);
                startActivity(intent);
                break;
        }
    }
}

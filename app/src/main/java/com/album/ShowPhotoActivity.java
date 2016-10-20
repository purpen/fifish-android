package com.album;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bean.Image;
import com.customview.ClipZoomImageView;
import com.customview.GlobalTitleLayout;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.activity.BaseActivity;
import com.qiyuan.fifish.util.MySQLiteOpenHelper;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowPhotoActivity extends BaseActivity{
    @BindView(R.id.title_show_photo_activity)
    GlobalTitleLayout mTitle;
    @BindView(R.id.image_show_photo_activity)
    ClipZoomImageView mIVClip;
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
        Picasso.with(this)
                .load(new File(imageBean.path))
                .placeholder(R.mipmap.default_album_option)
                .resizeDimen(R.dimen.dp180, R.dimen.dp180)
                .centerCrop()
                .into(mIVClip);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.destroy();
    }

    @OnClick({R.id.image_dustbin_show_photo_activity,R.id.tv_edit_share_show_photo_activity})
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
                Intent intent = new Intent();
                intent.setClass(this,FilterMirrorActivity.class);
                intent.putExtra("ImageBean",imageBean);
                startActivity(intent);
                break;
        }
    }
}

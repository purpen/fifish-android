package com.album;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.Image;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ImageGridAdapter;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.util.ActivityUtil;
import com.qiyuan.fifish.util.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class OptionPhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private CustomHeadView mHeadView;
    private GridView mGridView;
    private TextView mTvImport;
    private RelativeLayout mLayoutImport;
    private List<Image> imageList = new ArrayList<>();
    private ArrayList<Image> resultList = new ArrayList<>();
    private ImageGridAdapter imageGridAdapter;
    private MySQLiteOpenHelper dbHelper = null;
    private boolean isVideo=false,canChoose=false;
    private ObjectAnimator showAnimator, hideAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_photo);

        imageList = (List<Image>) getIntent().getSerializableExtra("list");
        dbHelper = new MySQLiteOpenHelper(this);
        initView();
    }

    private void initView() {
        mHeadView = (CustomHeadView) findViewById(R.id.head_option_photo);
        mHeadView.setHeadCenterTxtShow(true, getString(R.string.option_photo));
        mHeadView.setHeadGoBackShow(true);
        mHeadView.setHeadRightTxtShow(true, getString(R.string.choose));
        mHeadView.setRightTxtOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getString(R.string.choose).equals(mHeadView.getHeadRightTV().getText())) {
                    mHeadView.setHeadRightTxtShow(true,getString(R.string.cancel));
                    imageGridAdapter.showSelectIndicator(true);
                    canChoose=true;
                    showAnim();
                }else {
                    mHeadView.setHeadRightTxtShow(true, getString(R.string.choose));
                    imageGridAdapter.showSelectIndicator(false);
                    canChoose=false;
                    hideAnim();
                }
            }
        });
        mLayoutImport = (RelativeLayout) findViewById(R.id.layout_import_option_photo);
        //隐藏底部黑色背景导入条、因其高为70dp，故此处取70dp
        mLayoutImport.setTranslationY(getResources().getDimension(R.dimen.dp70));
        mTvImport = (TextView) findViewById(R.id.tv_import_option_photo);
        mTvImport.setOnClickListener(this);
        mGridView = (GridView) findViewById(R.id.grid_option_photo);

        isVideo = getIntent().getBooleanExtra("isVideo",true);
        imageGridAdapter = new ImageGridAdapter(this, false, 4, isVideo);
        if (!imageList.isEmpty()) {
            imageGridAdapter.setData(imageList);
            imageGridAdapter.showSelectIndicator(false);
        }
        mGridView.setAdapter(imageGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Image image = (Image) adapterView.getAdapter().getItem(i);
                if(!canChoose) return;//未出现选择框则执行至此为止
                imageGridAdapter.select(image);
                if (resultList.contains(image)) {
                    resultList.remove(image);
                } else {
                    resultList.add(image);
                }
                if (resultList.isEmpty()) {
                    mHeadView.setHeadCenterTxtShow(true, getString(R.string.option_photo));
                    mTvImport.setBackgroundResource(R.drawable.shape_grey_import);
                }else {
                    mHeadView.setHeadCenterTxtShow(true,"已选中"+resultList.size()+"张");
                    mTvImport.setBackgroundResource(R.drawable.shape_blue_import);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.destroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_import_option_photo:
                if (resultList.isEmpty()) {
                    Toast toast = Toast.makeText(this, R.string.least_chose_one, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                } else {
                    for (Image image : resultList) {
                        //if查数据库有则删除，无则insert
                        if (dbHelper.query(image)) {
//                        dbHelper.delete(image);
                        } else {
                            //通常0代表false 1代表true 在此作判断是否视频之用
                            if (isVideo) {
                                dbHelper.insert(image.id,image.path, image.name, image.time,1,image.videoDuration);
                            }else {
                                dbHelper.insert(image.id,image.path, image.name, image.time,0,image.videoDuration);
                            }

                        }
                    }
                    ActivityUtil.getInstance().finishActivity(OptionAlbumActivity.class);
                    finish();
                    break;
                }
        }
    }

    //显示底部黑色背景导入条
    private void showAnim() {
        if (showAnimator == null) {
            showAnimator = ObjectAnimator.ofFloat(mLayoutImport, "translationY", 0).setDuration(300);
        }
        showAnimator.start();
    }
    //隐藏底部黑色背景导入条
    private void hideAnim() {
        if (hideAnimator == null) {
            hideAnimator = ObjectAnimator.ofFloat(mLayoutImport, "translationY", mLayoutImport.getMeasuredHeight()).setDuration(300);
        }
        hideAnimator.start();
    }
}
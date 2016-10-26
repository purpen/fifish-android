package com.qiyuan.fifish.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.album.ShowPhotoActivity;
import com.bean.Image;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.MediaLocalGridAdapter;
import com.qiyuan.fifish.ui.activity.MainActivity;
import com.qiyuan.fifish.ui.activity.PublishPictureActivity;
import com.qiyuan.fifish.util.MySQLiteOpenHelper;
import com.qiyuan.fifish.util.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by android on 2016/9/23.
 */
public class MediaInnerFragment extends BaseFragment {
    @BindView(R.id.grid_media_inner_fragment)
    GridView mGridView;
    @BindView(R.id.no_data)
    TextView mIV;
    @BindView(R.id.layout_bottom)
    RelativeLayout mLayoutBottom;//底部黑背景分享条
    private MediaLocalGridAdapter imageGridAdapter;
    private ArrayList<Image> importedList = new ArrayList<>();//被导入的
    private ArrayList<Image> selectedList = new ArrayList<>();//被选中的
    private MySQLiteOpenHelper dbHelper = null;
    private boolean hasChoosed = false;//判断是否出现选择框
    private ObjectAnimator showAnimator, hideAnimator;
    public static MediaFragment mediaFragment;

    private static OnPhotoCountListener mPhotoCountListener;

    public interface OnPhotoCountListener {
        void setPhotoCount(int count);
    }

    public static MediaInnerFragment getInstance(int status, MediaFragment mediaFragment) {
        mPhotoCountListener = (OnPhotoCountListener) mediaFragment;
        MediaInnerFragment.mediaFragment = mediaFragment;
        MediaInnerFragment fragment = new MediaInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    private BroadcastReceiver mediaInnerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hasChoosed = intent.getBooleanExtra("choosed", true);
            imageGridAdapter.showSelectIndicator(hasChoosed);
            if (hasChoosed) {
                showAnim(); //显示顶部黑色背景标题栏和底部黑背景分享条
            } else {
                hideAnim(); //隐藏顶部黑色背景标题栏和底部黑背景分享条
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_media_inner);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        hasChoosed=false;
        imageGridAdapter.showSelectIndicator(false);
        if(!selectedList.isEmpty()) selectedList.clear();
        dbHelper = new MySQLiteOpenHelper(getActivity());
        importedList = dbHelper.retQuery();
        if (!importedList.isEmpty()) {
            imageGridAdapter.setData(importedList,dbHelper);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mediaInnerReceiver);
        dbHelper.destroy();
    }

    @Override
    protected void requestNet() {
        super.requestNet();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.qiyuan.fifish.ui.fragment.mediafragment");
        getActivity().registerReceiver(mediaInnerReceiver, filter);
    }

    @Override
    protected void initViews() {
        //隐藏底部黑色背景分享条、因其高为48dp，故此处取48dp
        mLayoutBottom.setTranslationY(getResources().getDimension(R.dimen.dp48));
        imageGridAdapter = new MediaLocalGridAdapter(getActivity(), false, 4);
        imageGridAdapter.showSelectIndicator(false);
        mGridView.setAdapter(imageGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Image image = (Image) adapterView.getAdapter().getItem(i);
                if (hasChoosed){//选择框出现的情况下
                    if (selectedList.contains(image)) {
                        selectedList.remove(image);
                    } else {
                        selectedList.add(image);
                    }
                    imageGridAdapter.select(image);
                    if (mPhotoCountListener != null) {
                        mPhotoCountListener.setPhotoCount(selectedList.size());
                    }
                }else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ShowPhotoActivity.class);
                    intent.putExtra("ImageBean",image);
                    getActivity().startActivity(intent);
                }

            }
        });
    }

    //显示顶部黑色背景标题栏和底部黑背景分享条
    private void showAnim() {
        if (showAnimator == null) {
            showAnimator = ObjectAnimator.ofFloat(mLayoutBottom, "translationY", 0).setDuration(300);
            showAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.hideAnim();
                    mediaFragment.hideAnim();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        showAnimator.start();
    }

    //隐藏顶部黑色背景标题栏和底部黑背景分享条
    private void hideAnim() {
        if (hideAnimator == null) {
            hideAnimator = ObjectAnimator.ofFloat(mLayoutBottom, "translationY", mLayoutBottom.getMeasuredHeight()).setDuration(300);
            hideAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.showAnim();
                    mediaFragment.showAnim();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        hideAnimator.start();
    }

    @OnClick({R.id.tv_share_media_inner_fragment, R.id.image_delete_media_inner_fragment})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share_media_inner_fragment:
                if (selectedList.size()==0){
                    ToastUtils.showInfo(R.string.least_chose_one);
                    return;
                }
                Image image0 = selectedList.get(0);
//                if (image0.isVideo){
//                    intent = new Intent(activity, PublishVideoActivity.class);
//                }else {
                Intent intent = new Intent(activity, PublishPictureActivity.class);
//                }
                intent.putExtra(MediaInnerFragment.class.getSimpleName(),image0);
                startActivity(intent);
                break;
            case R.id.image_delete_media_inner_fragment:
                if (!selectedList.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(), R.string.delete_ok, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    for (Image image : selectedList) {
                        dbHelper.delete(image);
                    }
                    selectedList.clear();
                    if (mPhotoCountListener != null) {
                        mPhotoCountListener.setPhotoCount(selectedList.size());
                    }
                    importedList = dbHelper.retQuery();
                    imageGridAdapter.setData(importedList,dbHelper);
                    if(importedList.isEmpty()) hideAnim();
                }else {
                    Toast toast = Toast.makeText(getActivity(), R.string.cant_delete, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
        }
    }
}

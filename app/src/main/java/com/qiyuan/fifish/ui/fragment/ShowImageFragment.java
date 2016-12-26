package com.qiyuan.fifish.ui.fragment;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
/**
 * Created by lilin on 2016/11/22.
 */
public class ShowImageFragment extends DialogFragment {
    @BindView(R.id.photoView)
    PhotoView photoView;
    private String url;
    private DisplayImageOptions options;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_cover)
                .showImageForEmptyUri(R.mipmap.default_cover)
                .showImageOnFail(R.mipmap.default_cover)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        Bundle bundle = getArguments();
        if (bundle != null) url = bundle.getString(ShowImageFragment.class.getSimpleName());
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = Util.inflateView(getActivity(), R.layout.fragment_share_dialog, null);
        ButterKnife.bind(this,view);
        ImageLoader.getInstance().displayImage(url,photoView,options);
        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(photoView);
        mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                dismiss();
            }
        });
        return view;
    }

    public static ShowImageFragment newInstance(Bundle bundle) {
        ShowImageFragment fragment= new ShowImageFragment();
        if (bundle!=null) fragment.setArguments(bundle);
        return fragment;
    }

//    @OnClick(R.id.photoView)
//    public void onClick() {
//        dismiss();
//    }

}

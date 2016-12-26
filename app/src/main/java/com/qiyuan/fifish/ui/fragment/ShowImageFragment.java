package com.qiyuan.fifish.ui.fragment;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) url = bundle.getString(ShowImageFragment.class.getSimpleName());
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = Util.inflateView(getActivity(), R.layout.fragment_share_dialog, null);
        ButterKnife.bind(this,view);
        ImageLoader.getInstance().displayImage(url,photoView);
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

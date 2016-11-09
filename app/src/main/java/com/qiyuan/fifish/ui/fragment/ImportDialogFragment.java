package com.qiyuan.fifish.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.album.OptionAlbumActivity;
import com.qiyuan.fifish.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by android on 2016/9/23.
 * BottomSheet
 */
public class ImportDialogFragment extends DialogFragment implements
        LoaderManager.LoaderCallbacks<Cursor>{
    @BindView(R.id.tv_local_photo)
    TextView mTvPhoto;
    @BindView(R.id.tv_local_video)
    TextView mTvVideo;
    private static final String LOADER_EXTRA_URI = "loader_extra_uri";
    private static final String LOADER_EXTRA_PROJECT = "loader_extra_project";

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_import_dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);

        ButterKnife.bind(this, dialog); // Dialog即View
        return dialog;
    }

    public static ImportDialogFragment newInstance() {
        ImportDialogFragment f = new ImportDialogFragment();
        return f;
    }

    @OnClick({R.id.tv_local_photo, R.id.tv_local_video})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_local_photo:
                Intent in = new Intent();
                in.setClass(getActivity(), OptionAlbumActivity.class);
                in.putExtra("album",0);
                getActivity().startActivity(in);
                dismiss();
                break;
            case R.id.tv_local_video:
                Intent intent = new Intent();
                intent.setClass(getActivity(), OptionAlbumActivity.class);
                intent.putExtra("album",2);
                getActivity().startActivity(intent);
                dismiss();
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Uri uri = Uri.parse(bundle.getString(LOADER_EXTRA_URI));
        String[] projects = bundle.getStringArray(LOADER_EXTRA_PROJECT);
        String order = MediaStore.MediaColumns.DATE_ADDED + " DESC";
        return new CursorLoader(getActivity(), uri, projects, null, null, order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

package com.qiyuan.fifish.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ShareDialogAdapter;
import com.qiyuan.fifish.bean.ShareItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lilin on 2016/11/22.
 */
public class ShareDialogFragment extends DialogFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // must be called before set content
        dialog.setContentView(R.layout.fragment_share_dialog);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        ButterKnife.bind(this,dialog);
        return dialog;
    }
    public static ShareDialogFragment newInstance() {
        ShareDialogFragment f = new ShareDialogFragment();
        return f;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] image = {R.mipmap.share_wechat, R.mipmap.share_qq, R.mipmap.share_sina, R.mipmap.share_facebook,R.mipmap.share_instgram};
        String[] name = {"微信", "QQ","微博","facebook", "instagram",};
        List<ShareItem> shareList = new ArrayList<>();
        ShareItem shareItem;
        for (int i = 0; i < image.length; i++) {
            shareItem = new ShareItem();
            shareItem.txt=name[i];
            shareItem.pic=image[i];
            shareList.add(shareItem);
        }
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new ShareDialogAdapter(getActivity(),shareList));
    }
}

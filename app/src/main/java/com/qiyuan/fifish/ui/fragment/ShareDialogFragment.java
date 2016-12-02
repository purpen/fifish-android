package com.qiyuan.fifish.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ShareDialogAdapter;
import com.qiyuan.fifish.bean.ShareItem;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        ButterKnife.bind(this, dialog);
        initData();
        return dialog;
    }

    public static ShareDialogFragment newInstance() {
        ShareDialogFragment f = new ShareDialogFragment();
        return f;
    }


    private void initData() {
        int[] image = {R.mipmap.share_wechat, R.mipmap.share_qq, R.mipmap.share_sina, R.mipmap.share_facebook, R.mipmap.share_instgram};
        String[] name = {"微信", "QQ", "微博", "facebook", "instagram",};
        List<ShareItem> shareList = new ArrayList<>();
        ShareItem shareItem;
        for (int i = 0; i < image.length; i++) {
            shareItem = new ShareItem();
            shareItem.txt = name[i];
            shareItem.pic = image[i];
            shareList.add(shareItem);
        }
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        ShareDialogAdapter adapter = new ShareDialogAdapter(getActivity(), shareList);
        recyclerView.setAdapter(adapter);
        adapter.setmOnItemClickListener(new ShareDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        share(SHARE_MEDIA.WEIXIN,"测试");
                        break;
                    case 1:
                        share(SHARE_MEDIA.QQ,"测试");
                        break;
                    case 2:
                        share(SHARE_MEDIA.SINA,"测试");
                        break;
                    case 3:
                        share(SHARE_MEDIA.FACEBOOK,"测试");
                        break;
                    case 4:
                        share(SHARE_MEDIA.INSTAGRAM,"测试");
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void share(SHARE_MEDIA platform,String content){
        new ShareAction(getActivity()).setPlatform(platform)
                .withText(content)
                .setCallback(umShareListener)
                .share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(),platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(),platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @OnClick(R.id.btn)
    public void onClick() {
        dismiss();
    }
}

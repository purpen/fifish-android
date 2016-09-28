package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.OnClick;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.SystemNoticeAdapter;
import com.qiyuan.fifish.bean.SystemNoticeData;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;

/**
 * @author lilin
 *         created at 2016/5/5 23:23
 */
public class SystemNoticeActivity extends BaseActivity {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.lv)
    ListView lv;
    private SystemNoticeAdapter adapter;
    private static final String PAGE_SIZE = "9999";
    private int curPage = 1;
    private WaitingDialog dialog;
    private ArrayList<SystemNoticeData.SystemNoticeItem> list;
    private int unread_count;
    public SystemNoticeActivity() {
        super(R.layout.activity_system_notice);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(getClass().getSimpleName())){
            unread_count = intent.getIntExtra(getClass().getSimpleName(),0);
        }
    }

    @Override
    protected void initViews() {
        dialog = new WaitingDialog(this);
        custom_head.setHeadCenterTxtShow(true, "系统通知");
//        WindowUtils.chenjin(this);
    }

    @OnClick(R.id.tv_head_right)
    void performClick(View v){
        switch (v.getId()){
            case R.id.tv_head_right:
                if (list==null || list.size()==0) return;
                list.clear();
                adapter.notifyDataSetChanged();
                Util.makeToast("已清空");
                break;
        }
    }

    @Override
    protected void installListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list!=null && list.size()>0){
                    SystemNoticeData.SystemNoticeItem item = list.get(i);
                    Intent intent;
                    switch (item.evt){
                        case 0://链接
                            Uri uri = Uri.parse(item.url);
                            intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                            break;
                        case 1: //情景
//                            intent=new Intent(activity, QJDetailActivity.class);
//                            intent.putExtra("id",item.url);
//                            startActivity(intent);
                            break;
                        case 2: //场景
//                            intent=new Intent(activity, QJDetailActivity.class);
//                            intent.putExtra("id",item.url);
//                            startActivity(intent);
                            break;
                        case 3: //产品
//                            intent=new Intent(activity, BuyGoodsDetailsActivity.class);
//                            intent.putExtra("id",item.url);
//                            startActivity(intent);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void requestNet() {
//        ClientDiscoverAPI.getSystemNotice(String.valueOf(curPage), PAGE_SIZE, new RequestCallBack<String>() {
//            @Override
//            public void onStart() {
//                if (!activity.isFinishing()&& dialog!=null) dialog.show();
//            }
//
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (!activity.isFinishing()&& dialog!=null) dialog.dismiss();
//                    }
//                }, DataConstants.DIALOG_DELAY);
//                if (responseInfo == null) return;
//                if (TextUtils.isEmpty(responseInfo.result)) return;
//                try {
//                    HttpResponse<SystemNoticeData> response= JsonUtil.json2Bean(responseInfo.result,new TypeToken<HttpResponse<SystemNoticeData>>(){});
//                    if (response.isSuccess()){
//                        list = response.getData().rows;
//                        refreshUI();
//                        return;
//                    }
//                    ToastUtils.showError(response.getMessage());
////                    dialog.showErrorWithStatus(response.getMessage());
//                } catch (JsonSyntaxException e) {
//                    LogUtil.e(TAG, e.getLocalizedMessage());
//                    ToastUtils.showError("对不起，数据异常");
////                    dialog.showErrorWithStatus("对不起,数据异常");
//                }
//
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                dialog.dismiss();
//                ToastUtils.showError("网络异常，请确认网络畅通");
////                dialog.showErrorWithStatus("网络异常，请确认网络畅通");
//            }
//        });
    }

    @Override
    protected void refreshUI() {
        if (list==null) return;
        if (list.size()==0){
//            if (!activity.isFinishing()&&dialog!=null) dialog.showWithStatus("暂无评论");
            return;
        }

        for (int i=0;i<unread_count;i++){
            list.get(i).is_unread=true;
        }

        if (adapter==null){
            adapter=new SystemNoticeAdapter(list,this);
            lv.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

}

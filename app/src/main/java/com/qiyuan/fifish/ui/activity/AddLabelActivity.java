package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.RecentUsedLabelAdapter;
import com.qiyuan.fifish.adapter.SuggestionAdapter;
import com.qiyuan.fifish.bean.SearchLabelData;
import com.qiyuan.fifish.bean.TagsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 添加标签
 */
public class AddLabelActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.delete_label)
    ImageView deleteLabel;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private WaitingDialog dialog;
    private ArrayList<String> expandList;//模糊搜索标签
    private SuggestionAdapter expandAdapter;
    private RecentUsedLabelAdapter adapter;

    @Override
    protected void getIntentData() {
//        activeTagsBean = (ActiveTagsBean) getIntent().getSerializableExtra(CreateQJActivity.class.getSimpleName());
    }

    public AddLabelActivity() {
        super(R.layout.activity_add_label);
    }

    @Override
    protected void initViews() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        expandList = new ArrayList<>();
        expandAdapter = new SuggestionAdapter(activity, expandList);
        recyclerView.setAdapter(expandAdapter);
        dialog = new WaitingDialog(this);
        custom_head.setHeadCenterTxtShow(true, R.string.title_label);
        deleteLabel.setOnClickListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    deleteLabel.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    searchSuggestion(s.toString());
                } else {
                    deleteLabel.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String str = editText.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    finish();
                    return false;
                }
                Intent intent = new Intent();
                intent.putExtra(AddLabelActivity.class.getSimpleName(),str);
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
        });
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void requestNet() {
        if (!activity.isFinishing() && dialog != null) dialog.show();
        RequestService.getHotTags(new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!activity.isFinishing() && dialog != null) dialog.dismiss();
                if (TextUtils.isEmpty(result)) return;
                TagsBean tagsBean = JsonUtil.fromJson(result, TagsBean.class);
                if (tagsBean.meta.status_code == Constants.HTTP_OK) {
                    ArrayList<TagsBean.DataBean> list = tagsBean.data;
                    if (list == null || list.size() == 0) return;
                    adapter = new RecentUsedLabelAdapter(list, activity);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!activity.isFinishing() && dialog != null) dialog.dismiss();
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_label:
                editText.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) return;
        Intent intent = new Intent();
        intent.putExtra(TAG, adapter.getItem(position - 1));
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 搜索建议
     *
     * @param str
     */
    private void searchSuggestion(String str) {
        RequestService.searchSuggestion(str, 20 + "", new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                SearchLabelData response = JsonUtil.fromJson(result, SearchLabelData.class);
                expandList.clear();
                expandList.addAll(response.data.swords);
                expandAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

//    @Override
//    public void onClick(int postion) {
//        Intent intent = new Intent();
//        intent.putExtra(AddLabelActivity.class.getSimpleName(), "#" + expandList.get(postion) + " ");
//        setResult(1, intent);
//        finish();
//    }
}

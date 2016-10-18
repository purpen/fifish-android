package com.qiyuan.fifish.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.util.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {
    @Bind(R.id.custom_head)
    CustomHeadView custom_head;
    @Bind(R.id.et_suggestion)
    EditText et_suggestion;
    @Bind(R.id.et_contact)
    EditText et_contact;

    public FeedbackActivity() {
        super(R.layout.activity_feedback);
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true, "意见反馈");
    }

    @OnClick({R.id.bt_commit})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_commit:
                if (!isUserInputLegal()) {
                    return;
                }
//                ClientDiscoverAPI.commitSuggestion(et_suggestion.getText().toString(), et_contact.getText().toString(), new RequestCallBack<String>() {
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        if (responseInfo == null) {
//                            return;
//                        }
//
//                        if (TextUtils.isEmpty(responseInfo.result)) {
//                            return;
//                        }
//
//                        try {
//                            JSONObject response = new JSONObject(responseInfo.result);
//                            if (response.optBoolean("success")){
//                                Util.makeToast(response.optString("message"));
//                                activity.finish();
//                            }else {
//                                Util.makeToast(response.optString("message"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(HttpException e, String s) {
//                        Util.makeToast(s);
//                    }
//                });
                break;
        }
    }

    private boolean isUserInputLegal() {
        if (TextUtils.isEmpty(et_suggestion.getText().toString().trim())) {
            ToastUtils.showInfo("您的建议不能为空，请重新输入！");
            return false;
        }

        if (et_suggestion.getText().length() > 500) {
            ToastUtils.showInfo("反馈内容过长！");
            return false;
        }
        return true;
    }

}

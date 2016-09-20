package com.qiyuan.fifish.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.FeedBackBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
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
                RequestService.submitFeedBack(et_contact.getText().toString(), et_suggestion.getText().toString(), new CustomCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        if (TextUtils.isEmpty(result)) return;
                        FeedBackBean feedBackBean = JsonUtil.fromJson(result, FeedBackBean.class);
                        if (feedBackBean.meta.status_code == Constants.HTTP_OK) {
                            ToastUtils.showSuccess("感谢您的反馈");
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ex.printStackTrace();
                        ToastUtils.showError(R.string.request_error);
                    }
                });
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

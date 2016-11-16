package com.qiyuan.fifish.ui.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.HttpResponse;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.SPUtil;
import com.qiyuan.fifish.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lilin
 *         created at 2016/5/9 10:36
 */
public class UpdatePasswordActivity extends BaseActivity {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.et_old)
    EditText et_old;
    @BindView(R.id.et_new)
    EditText et_new;
    @BindView(R.id.et_confirm_new)
    EditText et_confirm_new;
    private WaitingDialog dialog;

    public UpdatePasswordActivity() {
        super(R.layout.activity_update_password);
    }

    @Override
    protected void initViews() {
        dialog = new WaitingDialog(this);
        custom_head.setHeadCenterTxtShow(true, "修改密码");
        custom_head.setHeadRightTxtShow(true, R.string.complete);
    }

    @OnClick(R.id.tv_head_right)
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.tv_head_right:
                submitData(v);
                break;
        }
    }

    private void submitData(final View v) {
        String originPsd = et_old.getText().toString().trim();
        String newPsd = et_new.getText().toString().trim();
        String confirmNewPsd = et_confirm_new.getText().toString().trim();
        if (TextUtils.isEmpty(originPsd)) {
            ToastUtils.showInfo(R.string.input_init_password);
            return;
        }

        if (TextUtils.isEmpty(newPsd)) {
            ToastUtils.showInfo(R.string.input_new_password);
            return;
        }

        if (TextUtils.isEmpty(confirmNewPsd)) {
            ToastUtils.showInfo(R.string.input_confirm_password);
            return;
        }

        if (TextUtils.equals(originPsd, newPsd)) {
            ToastUtils.showInfo(R.string.init_equals_new_password);
            return;
        }

        if (!TextUtils.equals(newPsd, confirmNewPsd)) {
            ToastUtils.showInfo(R.string.confirm_not_equal_new_password);
            return;
        }

        RequestService.updatePassword(originPsd, newPsd, confirmNewPsd, new CustomCallBack() {
            @Override
            public void onStarted() {
                if (dialog != null && !activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
                HttpResponse response = JsonUtil.fromJson(result, HttpResponse.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    ToastUtils.showSuccess(R.string.update_password_success);
                    SPUtil.remove(Constants.LOGIN_INFO);
                    startActivity(new Intent(activity, LoginActivity.class));
                } else {
                    ToastUtils.showError(R.string.request_error);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }
}

package com.qiyuan.fifish.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomEditText;
import com.qiyuan.fifish.ui.view.CustomHeadView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lilin
 *         created at 2016/7/26 14:05
 */
public class ForgetPasswordActivity extends BaseActivity {

    @Bind(R.id.custom_head)
    CustomHeadView customHead;
    @Bind(R.id.et_code)
    CustomEditText etCode;
    @Bind(R.id.btn_code)
    Button btnCode;
    @Bind(R.id.et_password)
    CustomEditText etPassword;
    @Bind(R.id.et_phone)
    CustomEditText etPhone;

    public ForgetPasswordActivity() {
        super(R.layout.activity_forget_password);
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true,R.string.title_forget_password);
        etPhone.setCustomEditStyle(R.mipmap.phone, EditorInfo.TYPE_CLASS_PHONE, R.string.phone_num, false);
        etCode.setCustomEditStyle(R.mipmap.check_code, EditorInfo.TYPE_CLASS_TEXT, R.string.input_check_code, false);
        etPassword.setCustomEditStyle(R.mipmap.password, EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD, R.string.new_password, false);
    }

    @OnClick({R.id.et_code, R.id.btn_code, R.id.et_password, R.id.btn_update,R.id.et_phone})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_code:
                break;
            case R.id.btn_code:
                break;
            case R.id.et_password:
                break;
            case R.id.btn_update:
                break;
        }
    }

}

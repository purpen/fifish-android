package com.qiyuan.fifish.ui.activity;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Util;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author lilin
 *         created at 2016/7/26 13:10
 */
public class CompleteUserInfoActivity extends BaseActivity {

    @Bind(R.id.ibtn_close)
    ImageButton ibtnClose;
    @Bind(R.id.rl_head)
    RelativeLayout rlHead;
    @Bind(R.id.riv)
    RoundedImageView riv;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_job)
    EditText etJob;
    @Bind(R.id.et_area)
    EditText etArea;
    @Bind(R.id.btn_update)
    Button btnUpdate;

    public CompleteUserInfoActivity() {
        super(R.layout.activity_complete_user_info);
    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp48));
            params.topMargin = Util.getStatusBarHeight();
            rlHead.setLayoutParams(params);
        }

    }


    @OnClick({R.id.ibtn_close, R.id.riv, R.id.btn_update})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_close:
                break;
            case R.id.riv:
                break;
            case R.id.btn_update:
                break;
        }
    }
}

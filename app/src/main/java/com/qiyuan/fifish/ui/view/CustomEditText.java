package com.qiyuan.fifish.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.Util;

/**
 * @author lilin
 *         created at 2016/7/14 18:33
 */
public class CustomEditText extends RelativeLayout {
    private View view, custom_et_line;
    private EditText custom_et;

    public CustomEditText(Context context) {
        super(context);
        inflatelayout(context);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflatelayout(context);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflatelayout(context);
    }

    private void inflatelayout(Context context) {
        view = Util.inflateView(R.layout.custom_edittext, this);
        custom_et = (EditText) view.findViewById(R.id.custom_et);
        custom_et_line = view.findViewById(R.id.custom_et_line);
    }

    public void setCustomEditStyle(int drawableLeft, int inputType, int hintTxt, boolean lineShow) {
        custom_et.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(drawableLeft), null, null, null);
        custom_et.setHint(hintTxt);
        custom_et.setInputType(inputType);
        if (!lineShow) {
            custom_et_line.setVisibility(View.GONE);
        } else {
            custom_et_line.setVisibility(View.VISIBLE);
        }

    }

    public String getTxt() {
        return custom_et.getText().toString();
    }
}

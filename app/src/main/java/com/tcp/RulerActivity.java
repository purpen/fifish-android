package com.tcp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.qiyuan.fifish.R;

public class RulerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler);
        RulerHorizontalView rulerHorizontalView = (RulerHorizontalView) findViewById(R.id.ruler_view);
        rulerHorizontalView.smoothScrollTo(360);
        final RulerVerticalView rulerVerticalView = (RulerVerticalView) findViewById(R.id.ruler_vertical);
        rulerVerticalView.setWithText(true);
//        rulerVerticalView.setGravity(Gravity.LEFT);
        findViewById(R.id.button_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rulerVerticalView.smoothScrollTo(0);
            }
        });
        findViewById(R.id.button_scroll_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rulerVerticalView.smoothScrollTo(700);
            }
        });

    }

}

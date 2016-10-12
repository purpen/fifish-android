package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.TabLayoutViewPagerAdapter;
import com.qiyuan.fifish.ui.fragment.SupportPhotoFragment;
import com.qiyuan.fifish.ui.fragment.SupportVideoFragment;
import com.qiyuan.fifish.util.ToastUtils;

import butterknife.BindView;

/**
 * keyword 搜索标签
 * @author lilin
 * created at 2016/10/9 11:53
 */
public class SearchTagsActivity extends BaseActivity {
    @BindView(R.id.ibtn)
    ImageButton ibtn;
    @BindView(R.id.search_view)
    EditText searchView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String keyword;
    private TabLayoutViewPagerAdapter adapter;

    public SearchTagsActivity() {
        super(R.layout.activity_search);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(getClass().getSimpleName())) {
            keyword = intent.getStringExtra(getClass().getSimpleName());
            ToastUtils.showInfo(keyword);
        }
    }

    @Override
    protected void initViews() {
        String[] array = getResources().getStringArray(R.array.search_type);
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                tabLayout.addTab(tabLayout.newTab().setText(array[0]), true);
            } else {
                tabLayout.addTab(tabLayout.newTab().setText(array[i]), false);
            }
        }

        Fragment[] fragments = {SupportPhotoFragment.newInstance(), SupportVideoFragment.newInstance(), SupportVideoFragment.newInstance()};
        adapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(), fragments, array);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.length);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count) {
            String keyWord = cs.toString().trim();
            if (!TextUtils.isEmpty(keyWord)) {
                ibtn.setVisibility(View.VISIBLE);
            } else {
                ibtn.setVisibility(View.GONE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void installListener() {
        searchView.addTextChangedListener(tw);

        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //TODO do search
                }
                return false;
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

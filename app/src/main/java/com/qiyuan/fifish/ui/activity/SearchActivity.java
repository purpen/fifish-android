package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.TabLayoutViewPagerAdapter;
import com.qiyuan.fifish.ui.fragment.SearchedPictureFragment;
import com.qiyuan.fifish.ui.fragment.SearchedUserFragment;
import com.qiyuan.fifish.ui.fragment.SearchedVideoFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * keyword 搜索作品和用户
 *
 * @author lilin
 *         created at 2016/10/9 11:53
 */
public class SearchActivity extends BaseActivity {
    public interface OnSearchClickListener {
        void onSearch(String keyword,String evt);
    }

    private ArrayList<OnSearchClickListener> listeners;

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        listeners.add(onSearchClickListener);
    }
    @BindView(R.id.ibtn)
    ImageButton ibtn;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.search_view)
    EditText searchView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String tag;
    private TabLayoutViewPagerAdapter adapter;
    private String evt; //判断是根据输入内容还是标签进行搜索
    public SearchActivity() {
        super(R.layout.activity_search);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(getClass().getSimpleName())) {
            tag = intent.getStringExtra(getClass().getSimpleName());
        }
    }

    @Override
    protected void initViews() {
        Bundle bundle=null;
        if (TextUtils.isEmpty(tag)){
            evt="1";
        }else {
            evt="2";
            searchView.setText(tag);
            bundle = new Bundle();
            bundle.putString(TAG,tag);
        }
        listeners = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.search_type);
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                tabLayout.addTab(tabLayout.newTab().setText(array[0]), true);
            } else {
                tabLayout.addTab(tabLayout.newTab().setText(array[i]), false);
            }
        }
        Fragment[] fragments = {SearchedVideoFragment.newInstance(bundle), SearchedPictureFragment.newInstance(bundle), SearchedUserFragment.newInstance(bundle)};
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.getText().clear();
            }
        });
        searchView.addTextChangedListener(tw);
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (listeners!=null) {
                        for (OnSearchClickListener listener:listeners){
                            listener.onSearch(textView.getText().toString(),evt);
                        }
                    }
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

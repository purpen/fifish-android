package com.qiyuan.fifish.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.fragment.LinkHelpFragment;
import com.qiyuan.fifish.ui.view.CustomViewPager;
import com.qiyuan.fifish.util.Util;

/**
 * @author lilin
 *         created at 2016/4/11 14:01
 */
public class LinkHelpAdapter extends PagerAdapter {
    private String[] help_step_arr;
    private String[] link_help_arr;
    private CustomViewPager viewPager;
    private LinkHelpFragment fragment;
    public LinkHelpAdapter(String[] help_step_arr, String[] link_help_arr, LinkHelpFragment fragment,CustomViewPager viewPager) {
        this.help_step_arr=help_step_arr;
        this.link_help_arr=link_help_arr;
        this.fragment=fragment;
        this.viewPager=viewPager;
    }

    @Override
    public int getCount() {
        return help_step_arr.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = Util.inflateView(R.layout.view_link_help, null);
        TextView tv_tips = (TextView)view.findViewById(R.id.tv_tips);
        Button btn_next = (Button)view.findViewById(R.id.btn_next);
        tv_tips.setText(help_step_arr[position]);
        btn_next.setText(link_help_arr[position]);
        container.addView(view);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0:
                        viewPager.setCurrentItem(1,true);
                        break;
                    case 1:
                        viewPager.setCurrentItem(2,true);
                        break;
                    case 2:
                        viewPager.setCurrentItem(3,true);
                        break;
                    case 3:
                        fragment.dismiss();
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}

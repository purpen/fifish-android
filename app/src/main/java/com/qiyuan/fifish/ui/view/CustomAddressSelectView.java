package com.qiyuan.fifish.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.wheelview.OnWheelChangedListener;
import com.qiyuan.fifish.ui.view.wheelview.StringWheelAdapter;
import com.qiyuan.fifish.ui.view.wheelview.WheelView;
import com.qiyuan.fifish.util.ProvinceUtil;

import java.util.List;

public class CustomAddressSelectView extends LinearLayout {
    private List<String> provinces;
    private List<String> cities;
    private WheelView wv_left;
    private WheelView wv_center;
//    private WheelView wv_right;

    public CustomAddressSelectView(Context context) {
        this(context, null);
    }

    public CustomAddressSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomAddressSelectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initProvinces();
        initViews(context);
        installListeners();
    }

    private void initViews(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_address_select_view, this);
        wv_left = (WheelView) view.findViewById(R.id.wv_left);
        wv_center = (WheelView) view.findViewById(R.id.wv_center);
//        wv_right = (WheelView) view_link_help.findViewById(R.id.wv_right);
        boolean isCyclic = false;
        wv_left.setCyclic(isCyclic);
        wv_center.setCyclic(isCyclic);
//        wv_right.setCyclic(isCyclic);
        int visibleItems = 5;
        wv_left.setVisibleItems(visibleItems);
        wv_center.setVisibleItems(visibleItems);
//        wv_right.setVisibleItems(visibleItems);
        if (provinces != null && provinces.size() > 0) {
            wv_left.setAdapter(new StringWheelAdapter(provinces));
        }

        initCitiesByProvince();

        if (cities != null && cities.size() > 0) {
            wv_center.setAdapter(new StringWheelAdapter(cities));
        }

//        initCountiesByCity();
//        if (counties != null && counties.size() > 0) {
//            wv_right.setAdapter(new StringWheelAdapter(counties));
//            wv_right.setCurrentItem(0);
//        }

    }

    private void initProvinces() {
        provinces = ProvinceUtil.getProvinces();
    }

    private void initCitiesByProvince() {
        if (provinces!=null&& provinces.size()>0){
            cities = ProvinceUtil.getCitiesByProvince(provinces.get(wv_left.getCurrentItem()));
        }
    }

//    private void initCountiesByCity() {
//        if (cities!=null && cities.size()>0){
//            counties=ProvinceUtil.getCountiesByCity(cities.get(wv_center.getCurrentItem()));
//        }
//    }

    private void installListeners() {
        wv_left.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                initCitiesByProvince();
                wv_center.setAdapter(new StringWheelAdapter(cities));
                wv_center.setCurrentItem(0);
//                initCountiesByCity();
//                wv_right.setAdapter(new StringWheelAdapter(counties));
            }
        });
//        wv_center.addChangingListener(new OnWheelChangedListener() {
//            @Override
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                initCountiesByCity();
//                wv_right.setAdapter(new StringWheelAdapter(counties));
//            }
//        });
    }

    public String getAddress() {//|| counties == null || counties.size() == 0
        if (provinces == null || provinces.size() == 0 || cities == null || cities.size() == 0 ) {
            return "";
        }
        String province = provinces.get(wv_left.getCurrentItem());
        String city = cities.get(wv_center.getCurrentItem());
        return String.format("%s %s", province,city);
    }
}

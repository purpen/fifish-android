package com.qiyuan.fifish.util;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.City;
import com.qiyuan.fifish.bean.Province;
import com.qiyuan.fifish.bean.ProvinceCityData;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author lilin
 *         created at 2016/4/27 13:30
 */
public class ProvinceUtil {
    private static ProvinceCityData data;
    private static final String TAG = "ProvinceUtil";
    private static HashMap<String, ArrayList<String>> provinceCityMap = null;
    private static HashMap<String,Integer> idProvinceMap=null;
    private static HashMap<String,Integer> idCitiesMap=null;
    public static void init() {
//        ClientDiscoverAPI.getAllCities(new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                if (TextUtils.isEmpty(responseInfo.result)){
//                    return;
//                }
//                data = JsonUtil.fromJson(responseInfo.result, new TypeToken<HttpResponse<ProvinceCityData>>() {
//                });
//                dealData();
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Util.makeToast(s);
//            }
//        });
    }

    private static void dealData(){
        if (data==null){
            LogUtil.e(TAG+"data==null");
            return;
        }

        if (data.rows==null ||data.rows.size()==0){
            LogUtil.e(TAG+"data.rows==null || data.rows.size()==0");
            return;
        }
        provinceCityMap = new HashMap<>();
        idProvinceMap=new HashMap<>();
        idCitiesMap=new HashMap<>();
        ArrayList<String> cities;
        for (Province province:data.rows){
            idProvinceMap.put(province.city,province._id);
            cities = new ArrayList<>();
            for (City city :province.cities){
                cities.add(city.city);
                idCitiesMap.put(city.city,city._id);
            }
            provinceCityMap.put(province.city,cities);
        }
    }

    public static int getProvinceIdByName(String name){
        return idProvinceMap.get(name);
    }

    public static int getCityIdByName(String name){
        return idCitiesMap.get(name);
    }

    public static ArrayList<String> getProvinces() {
        if (data==null){
            ToastUtils.showInfo(R.string.request_error);
            return null;
        }
        ArrayList<String> provinces = new ArrayList<>();
        for (Province province : data.rows) {
            provinces.add(province.city);
        }
        return provinces;
    }


    public static ArrayList<String> getCitiesByProvince(String province) {
        if (data==null){
            ToastUtils.showInfo(R.string.request_error);
            return null;
        }
        return provinceCityMap.get(province);
    }

}

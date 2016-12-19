//package com.qiyuan.fifish.util;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.search.geocode.GeoCodeResult;
//import com.baidu.mapapi.search.geocode.GeoCoder;
//import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
//import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
//import com.baidu.mapapi.search.poi.PoiDetailResult;
//import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
//import com.baidu.mapapi.search.poi.PoiResult;
//import com.baidu.mapapi.search.poi.PoiSearch;
//import com.baidu.mapapi.search.poi.PoiSortType;
//import com.qiyuan.fifish.application.AppApplication;
//
///**
// * @author lilin
// *         created at 2016/4/12 14:52
// */
//public class MapUtil {
//    private static GeoCoder mGeoCoder;
//    private static PoiSearch mPoiSearch;
//    private static LocationClient mLocationClient;
//
//    public interface MyOnGetGeoCoderResultListener{
//        void onGetReverseGeoCodeResult(ReverseGeoCodeResult result);
//        void onGetGeoCodeResult(GeoCodeResult result);
//    }
//
//    /**
//     *根据纬度经度获取地址
//     * @param lat
//     * @param lon
//     * @param listener
//     */
//    public static void getAddressByCoordinate(double lat, double lon,MyOnGetGeoCoderResultListener listener) {
//        LatLng latLng = new LatLng(lat, lon);
//        getAddressByCoordinate(latLng,listener);
//    }
//
//    /**
//     * 根据纬度经度获取地址
//     * @param latLng
//     * @param listener
//     */
//    public static void getAddressByCoordinate(LatLng latLng,final MyOnGetGeoCoderResultListener listener) {
//        if (mGeoCoder==null){
//            mGeoCoder = GeoCoder.newInstance();
//        }
//
//        mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//            @Override
//            public void onGetGeoCodeResult(GeoCodeResult result) {
//                if (listener!=null){
//                    listener.onGetGeoCodeResult(result);
//                }
//            }
//
//            @Override
//            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//                if (listener!=null){
//                    listener.onGetReverseGeoCodeResult(result);
//                }
//            }
//        });
//        mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption()
//                .location(latLng));
//    }
//
//    public static void destroyGeoCoder() {
//        if (mGeoCoder != null){
//            mGeoCoder.destroy();
//            mGeoCoder=null;
//        }
//    }
//
//
//    public interface MyOnGetPoiSearchResultListener{
//        void onGetPoiResult(PoiResult result);
//        void onGetPoiDetailResult(PoiDetailResult result);
//    }
//
//    /**
//     * 根据关键字周边搜索Poi
//     * @param keyword
//     * @param listener
//     */
//    public static void getPoiNearbyByKeyWord(String keyword, LatLng ll, int radius, int pageNum, int pageCapacity, PoiSortType sortType, final MyOnGetPoiSearchResultListener listener) {
//        if (mPoiSearch==null){
//            mPoiSearch = PoiSearch.newInstance();
//        }
//        mPoiSearch.searchNearby(new PoiNearbySearchOption().keyword(keyword).location(ll).radius(radius).pageNum(pageNum).pageCapacity(pageCapacity).sortType(sortType));
//        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
//            @Override
//            public void onGetPoiResult(PoiResult result) {
//                if (listener!=null){
//                    listener.onGetPoiResult(result);
//                }
//            }
//            @Override
//            public void onGetPoiDetailResult(PoiDetailResult result) {
//                if (listener!=null){
//                    listener.onGetPoiDetailResult(result);
//                }
//            }
//        });
//    }
//    public static void destroyPoiSearch() {
//        if (mPoiSearch != null){
//            mPoiSearch.destroy();
//            mPoiSearch=null;
//        }
//    }
//
//    public interface OnReceiveLocationListener{
//        void onReceiveLocation(BDLocation bdLocation);
//    }
//
//    public static void getCurrentLocation(final OnReceiveLocationListener locationListener) {
//        mLocationClient = new LocationClient(AppApplication.getInstance());
//        mLocationClient.registerLocationListener(new BDLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                if (locationListener!=null){
//                    locationListener.onReceiveLocation(bdLocation);
//                }
//            }
//        });
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        mLocationClient.setLocOption(option);
//        mLocationClient.start();
//    }
//
//    public static void destroyLocationClient() {
//        if (mLocationClient != null){
//            mLocationClient.stop();
//            mLocationClient=null;
//        }
//    }
//}

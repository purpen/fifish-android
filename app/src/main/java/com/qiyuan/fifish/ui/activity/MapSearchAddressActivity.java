//package com.qiyuan.fifish.ui.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ListView;
//
//import com.baidu.location.BDLocation;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.MapStatus;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.MarkerOptions;
//import com.baidu.mapapi.map.MyLocationData;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.search.core.PoiInfo;
//import com.baidu.mapapi.search.core.SearchResult;
//import com.baidu.mapapi.search.geocode.GeoCodeResult;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
//import com.baidu.mapapi.search.poi.PoiDetailResult;
//import com.baidu.mapapi.search.poi.PoiResult;
//import com.baidu.mapapi.search.poi.PoiSortType;
//import com.qiyuan.fifish.R;
//import com.qiyuan.fifish.adapter.BDAddressListAdapter;
//import com.qiyuan.fifish.ui.view.CustomHeadView;
//import com.qiyuan.fifish.ui.view.WaitingDialog;
//import com.qiyuan.fifish.util.MapUtil;
//import com.qiyuan.fifish.util.ToastUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
//
///**
// * 高德地图不支持搜索POI
// * @author lilin
// *  created at 2016/4/12 18:15
// */
//public class MapSearchAddressActivity extends BaseActivity implements View.OnClickListener {
//    private static final int REQUEST_ADDRESS = 101;
//    @BindView(R.id.custom_head)
//    CustomHeadView custom_head;
//    @BindView(R.id.search_view)
//    EditText search_view;
//    @BindView(R.id.ll)
//    ListView ll;
//    @BindView(R.id.ibtn)
//    ImageButton ibtn;
//    @BindView(R.id.mapView)
//    MapView mapView;
//    private BaiduMap mBDMap;
//    private ArrayList<PoiInfo> list;
//    private BDAddressListAdapter adapter;
//    private boolean isFirstLoc = true;
//    private int pageNum = 0;
//    private PoiSortType sortType = PoiSortType.distance_from_near_to_far; //默认排序类型
//    private LatLng latLng;
//    private BitmapDescriptor bitmapDescripter;
//    private WaitingDialog dialog;
//    //当前位置的市和区
//    private String city, district;
//
//
//    public MapSearchAddressActivity() {
//        super(R.layout.activity_bdsearch_address);
//    }
//
//    @Override
//    protected void getIntentData() {
//        super.getIntentData();
////        latLng = getIntent().getParcelableExtra("latLng");
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        if (latLng == null) {
//        displayCurrentLocation();
////        } else {
////            MapUtil.getCurrentLocation(activity, new MapUtil.OnReceiveLocationListener() {
////                @Override
////                public void onReceiveLocation(BDLocation location) {
////                    if (location == null) {
////                        return;
////                    }
////                    MyLocationData locData = new MyLocationData.Builder()
////                            .accuracy(location.getRadius())
////                            // 此处设置开发者获取到的方向信息，顺时针0-360
////                            .direction(100).latitude(location.getLatitude())
////                            .longitude(location.getLongitude()).build();
////                    mBDMap.setMyLocationData(locData);
////
////                    if (isFirstLoc) {
////                        isFirstLoc = false;
////                        LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
////                        MapStatus.Builder builder = new MapStatus.Builder();
////                        builder.target(ll).zoom(15.0f);
////                        mBDMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
////                    }
////                }
////            });
////            loadAndshowGeoCoderResult(latLng);
////        }
//    }
//
//    private void displayCurrentLocation() {
//        MapUtil.getCurrentLocation(new MapUtil.OnReceiveLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation location) {
//                if (location == null) {
//                    return;
//                }
//                MyLocationData locData = new MyLocationData.Builder()
//                        .accuracy(location.getRadius())
//                                // 此处设置开发者获取到的方向信息，顺时针0-360
//                        .direction(100).latitude(location.getLatitude())
//                        .longitude(location.getLongitude()).build();
//                mBDMap.setMyLocationData(locData);
//                if (isFirstLoc) {
//                    isFirstLoc = false;
//                    latLng = new LatLng(location.getLatitude(),
//                            location.getLongitude());
//                    loadAndshowGeoCoderResult(latLng);
//                }
//            }
//        });
//    }
//
//    private void loadAndshowGeoCoderResult(LatLng latLng) {
//        if (latLng == null)
//            return;
//        if (!activity.isFinishing() && dialog != null) dialog.show();
//        MapUtil.getAddressByCoordinate(latLng, new MapUtil.MyOnGetGeoCoderResultListener() {
//            @Override
//            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//                if (!activity.isFinishing() && dialog != null) dialog.dismiss();
//                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                    return;
//                }
//                city = result.getAddressDetail().city;
//                district = result.getAddressDetail().district;
//                if (list == null) {
//                    list = new ArrayList<>();
//                } else {
//                    list.clear();
//                }
//                if(result.getPoiList()!=null){
//                    list.addAll(result.getPoiList());
//                }
//                if (list.size() > 0) {
//                    if (adapter == null) {
//                        adapter = new BDAddressListAdapter(activity, list);
//                        ll.setAdapter(adapter);
//                    } else {
//                        adapter.notifyDataSetChanged();
//                    }
//                    addOverlayer(list);
//                } else {
//                    ToastUtils.showInfo(R.string.map_no_result);
//                }
//            }
//
//            @Override
//            public void onGetGeoCodeResult(GeoCodeResult result) {
//            }
//        });
//    }
//
//    private void addOverlayer(final List<PoiInfo> list) {
//        if (list == null) return;
//        if (list.size() == 0) return;
//        mBDMap.clear();
//        PoiInfo poiInfo = list.get(0);
//        bitmapDescripter = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marker3);
//        LatLng ll = new LatLng(poiInfo.location.latitude, poiInfo.location.longitude);
//        MapStatus.Builder builder = new MapStatus.Builder();
//        builder.target(ll).zoom(15.0f);
//        mBDMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//        MarkerOptions option = new MarkerOptions().position(ll).icon(bitmapDescripter);
//        option.animateType(MarkerOptions.MarkerAnimateType.grow);
//        mBDMap.addOverlay(option);
//    }
//
//    @Override
//    protected void initViews() {
//        custom_head.setHeadCenterTxtShow(true,R.string.add_address);
//        dialog = new WaitingDialog(this);
//        custom_head.setHeadGoBackShow(false);
//        custom_head.setHeadRightTxtShow(true,R.string.cancel);
//        mapView.showZoomControls(false);
//        mBDMap = mapView.getMap();
//        mBDMap.setMyLocationEnabled(true);
//
//    }
//
//    @Override
//    protected void installListener() {
//        search_view.addTextChangedListener(tw);
////        ll.setOnScrollListener(new AbsListView.OnScrollListener() {
////            @Override
////            public void onScrollStateChanged(AbsListView absListView, int i) {
////                if (i == SCROLL_STATE_IDLE || i == SCROLL_STATE_FLING) {
////                    if (absListView.getLastVisiblePosition() == list.size()) {
////                        LogUtil.e("pageNum==", pageNum + "");
////                        loadAndshowPoiResult(et.getText().toString().trim(), latLng);
////                    }
////                }
////            }
////
////            @Override
////            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
////
////            }
////        });
//        search_view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                    String keyWord = search_view.getText().toString().trim();
//                    if (!TextUtils.isEmpty(keyWord)) {
//                        loadAndshowPoiResult(keyWord, latLng);
//                    }
//                }
//                return false;
//            }
//        });
//        ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (city == null || district == null) {
//                    return;
//                }
//                Intent intent = new Intent();
//                intent.putExtra(PoiInfo.class.getSimpleName(), list.get(i));
//                intent.putExtra("city", city);
//                intent.putExtra("district", district);
//                setResult(RESULT_OK, intent);
////                setResult(DataConstants.RESULTCODE_CREATESCENE_BDSEARCH, intent);
//                finish();
//            }
//        });
//        ibtn.setOnClickListener(this);
//        custom_head.getIvLeft().setOnClickListener(this);
//        custom_head.getHeadRightTV().setOnClickListener(this);
////        mBDMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
////            @Override
////            public void onMapClick(LatLng latLng) {
////                Intent intent = new Intent(activity, MapAddressDetailActivity.class);
////                if (list != null && list.size() > 0) {
////                    intent.putExtra(MapAddressDetailActivity.class.getSimpleName(), list.get(0));
////                }
////                startActivityForResult(intent, REQUEST_ADDRESS);
////            }
////
////            @Override
////            public boolean onMapPoiClick(MapPoi mapPoi) {
////                return false;
////            }
////        });
//    }
//
//    @Override
//    protected void onResume() {
//        if (mapView != null)
//            mapView.onResume();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        if (mapView != null)
//            mapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ibtn:
//                search_view.getText().clear();
//                ibtn.setVisibility(View.GONE);
//                break;
//            case R.id.tv_head_right:
//                finish();
//            case R.id.iv_left:
//                if (mBDMap == null) return;
//                mBDMap.clear();
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(latLng).zoom(15.0f);
//                mBDMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                loadAndshowGeoCoderResult(latLng);
//                break;
//        }
//    }
//
//    private TextWatcher tw = new TextWatcher() {
//        @Override
//        public void onTextChanged(CharSequence cs, int start, int before, int count) {
//            String keyWord = cs.toString().trim();
//            if (!TextUtils.isEmpty(keyWord)) {
//                ibtn.setVisibility(View.VISIBLE);
////                pageNum = 0;
////                loadAndshowPoiResult(keyWord, latLng);
//            } else {
//                ibtn.setVisibility(View.GONE);
//            }
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//        }
//    };
//
//    private void loadAndshowPoiResult(String keyWord, LatLng latLng) {
//        if (!activity.isFinishing() && dialog != null) dialog.show();
//        int radius = 1000;
//        int pageCapacity = 10;
//        MapUtil.getPoiNearbyByKeyWord(keyWord, latLng, radius, pageNum, pageCapacity, sortType, new MapUtil.MyOnGetPoiSearchResultListener() {
//            @Override
//            public void onGetPoiResult(PoiResult result) {
//                if (!activity.isFinishing() && dialog != null) dialog.dismiss();
//                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                    return;
//                }
//                pageNum++;
//                if (list == null) {
//                    list = new ArrayList<>();
//                } else {
//                    list.clear();
//                }
//                list.addAll(result.getAllPoi());
//                if (list.size() > 0) {
//                    if (adapter == null) {
//                        adapter = new BDAddressListAdapter(activity, list);
//                        ll.setAdapter(adapter);
//                    } else {
//                        adapter.notifyDataSetChanged();
//                    }
//                } else {
//                    ToastUtils.showInfo(R.string.map_no_result);
//                }
//            }
//
//            @Override
//            public void onGetPoiDetailResult(PoiDetailResult result) {
//
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) return;
//        if (requestCode == REQUEST_ADDRESS) {
//            ArrayList<PoiInfo> list = data.getParcelableArrayListExtra("list");
//            this.list.clear();
//            this.list.addAll(list);
//            adapter.notifyDataSetChanged();
//            addOverlayer(list);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        MapUtil.destroyGeoCoder();
//        MapUtil.destroyPoiSearch();
//        MapUtil.destroyLocationClient();
//        if (bitmapDescripter != null) {
//            bitmapDescripter.recycle();
//        }
//        mapView.onDestroy();
//        super.onDestroy();
//    }
//
//}

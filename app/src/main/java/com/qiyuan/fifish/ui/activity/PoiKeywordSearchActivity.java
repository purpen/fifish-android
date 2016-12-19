package com.qiyuan.fifish.ui.activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.PoiAddressAdapter;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.ToastUtils;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PoiKeywordSearchActivity extends BaseActivity implements TextWatcher,
        OnPoiSearchListener, AMapLocationListener, InputtipsListener {
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.custom_head)
    CustomHeadView customHead;
    @BindView(R.id.ibtn)
    ImageButton ibtn;
    @BindView(R.id.list_view)
    ListView listView;
    private String keyWord = "";// 要输入的poi搜索关键字
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private WaitingDialog dialog;
    private List<PoiItem> mList;
    private PoiAddressAdapter poiAddressAdapter;

    public PoiKeywordSearchActivity() {
        super(R.layout.activity_poi_keyword_search);
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true,R.string.title_add_location);
        dialog = new WaitingDialog(activity);
        mList = new ArrayList<>();
        poiAddressAdapter = new PoiAddressAdapter(this, mList);
        listView.setAdapter(poiAddressAdapter);
        initLocationClient();
    }

    @Override
    protected void installListener() {
        autoCompleteTextView.addTextChangedListener(this);//文本输入框监听
        autoCompleteTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    keyWord = autoCompleteTextView.getText().toString().trim();
                    if (!TextUtils.isEmpty(keyWord)) {
                        doSearchQuery();
                    }else {
                        ToastUtils.showInfo(R.string.input_keyword);
                    }
                }
                return false;
            }
        });
    }

    private void initLocationClient() {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(Constants.LOCATION_SERVICE_INTERVAL);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            if (dialog != null && !activity.isFinishing()) dialog.show();
            mlocationClient.startLocation();
        }
    }

//    /**
//     * 点击下一页按钮
//     */
//    public void nextButton() {
//        if (query != null && poiSearch != null && poiResult != null) {
//            if (poiResult.getPageCount() - 1 > currentPage) {
//                currentPage++;
//                query.setPageNum(currentPage);// 设置查后一页
//                poiSearch.searchPOIAsyn();
//            } else {
//                ToastUtils.showInfo(R.string.map_no_result);
//            }
//        }
//    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        if (mList != null) mList.clear();
        if (dialog != null && !dialog.isShowing() && !activity.isFinishing()) dialog.show();
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if (!TextUtils.isEmpty(newText)) {
            ibtn.setVisibility(View.VISIBLE);
            InputtipsQuery inputquery = new InputtipsQuery(newText, "");
            Inputtips inputTips = new Inputtips(activity,inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }else {
            ibtn.setVisibility(View.GONE);
        }
    }


    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (dialog != null && dialog.isShowing() && !activity.isFinishing()) dialog.dismiss();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems != null && poiItems.size() > 0) {
                        refreshUI(poiItems);
                    } else {
                        ToastUtils.showInfo(R.string.map_no_result);
                    }
                }
            } else {
                ToastUtils.showInfo(R.string.map_no_result);
            }
        } else {
            ToastUtils.showError(rCode);
        }

    }

    @Override
    protected void refreshUI(List list) {
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        if (poiAddressAdapter == null) {
            poiAddressAdapter = new PoiAddressAdapter(this, mList);
            listView.setAdapter(poiAddressAdapter);
        } else {
            poiAddressAdapter.notifyDataSetChanged();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (poiAddressAdapter==null) return;
                PoiItem item = poiAddressAdapter.getItem(i);
                Intent intent=new Intent();
                intent.putExtra(TAG,item.getTitle());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                query = new PoiSearch.Query(keyWord, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
                query.setPageSize(20);// 设置每页最多返回多少条poiitem
                query.setPageNum(0);// 设置查第一页
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度
                poiSearch = new PoiSearch(this, query);
                poiSearch.setOnPoiSearchListener(this);
                // 设置搜索区域为以点为圆心，其周围5000米范围
                poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 5000, true));//
                poiSearch.searchPOIAsyn();// 异步搜索
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }


    @Override
    public void onPoiItemSearched(PoiItem item, int rCode) {

    }


    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
            List<String> listString = new ArrayList<>();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());
            }
            ArrayAdapter<String> aAdapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    R.layout.route_inputs, listString);
            autoCompleteTextView.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showError(rCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @OnClick(R.id.ibtn)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn:
                autoCompleteTextView.getText().clear();
                break;
            default:
                break;
        }
    }
}

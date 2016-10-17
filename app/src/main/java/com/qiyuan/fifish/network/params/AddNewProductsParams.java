package com.qiyuan.fifish.network.params;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;

@HttpRequest(
        host = "http://api.qysea.com",
        path = "stuffs/store",
        builder = DefaultParamsBuilder.class)
public class AddNewProductsParams extends RequestParams {
    public String content;
    public String city;
    public String asset_id;
    public String address;
    public String kind;
    public String lat;
    public String lng;
    public String[] tags;
    public AddNewProductsParams() {}
}

package com.qiyuan.fifish.network;

import android.text.TextUtils;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lilin
 *         created at 2016/8/5 17:05
 */
public class RequestManager{
    private HashMap<String, Callback.Cancelable> hashMap=new HashMap<>();

    private RequestManager() {
    }

    private static class RequestManagerHolder {
        private static RequestManager requestManager = new RequestManager();
    }

    public static RequestManager getInstance() {
        return RequestManagerHolder.requestManager;
    }

    public void add(String tag, Callback.Cancelable cancelable) {
        if (null==hashMap || TextUtils.isEmpty(tag) || null==cancelable ) return;
        hashMap.put(tag, cancelable);
    }

    public void cancel(String tag) {
        if (hashMap == null || TextUtils.isEmpty(tag)) return;
        if (hashMap.containsKey(tag)){
            hashMap.get(tag).cancel();
            hashMap.remove(tag);
        }
    }

    public void cancelAll() {
        if (hashMap == null || hashMap.isEmpty()) return;
        Iterator<Map.Entry<String, Callback.Cancelable>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()){
            iterator.next().getValue().cancel();
        }
        hashMap.clear();
    }
}

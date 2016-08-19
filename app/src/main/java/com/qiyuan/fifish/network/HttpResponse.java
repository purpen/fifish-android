package com.qiyuan.fifish.network;

import java.util.ArrayList;

/**
 * @author lilin
 *         created at 2016/4/5 17:39
 */
public class HttpResponse<T>{
    public Meta meta;
    public Data data;

    private class Meta {
        public String message;
        public int status_code;
        public Error errors;
    }

    private class Data {
        public String token;
    }

    private class Error {
        public ArrayList<String> account;
    }

}

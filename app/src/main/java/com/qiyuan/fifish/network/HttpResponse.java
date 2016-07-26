package com.qiyuan.fifish.network;

/**
 * @author lilin
 * created at 2016/4/5 17:39
 */
public class HttpResponse<T> {
    private boolean success;
    private boolean is_error;
    private String status;
    private String message;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return is_error;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}

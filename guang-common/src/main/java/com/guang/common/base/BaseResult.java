package com.guang.common.base;

/**
 * @author huxianguang
 * @create 2017-10-25-下午8:14
 * 统一返回结果类
 **/
public class BaseResult {

    public int code;
    public String message;
    public Object data;

    public BaseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

package com.razbank.razbank.utils;

public enum Response {

    OK("ok", 0,null),
    ERROR("error", 1,null);

    private String value;
    private  int code;
    private  String method;

    Response(String value, int code, String method) {
        this.value = value;
        this.code = code;
        this.method = method;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

package com.razbank.razbank.utils;

public enum ResponseInfo {

    OK("ok", 0),
    ERROR("error", 1);

    private String value;
    private  int code;

    ResponseInfo(String value, int code) {
        this.value = value;
        this.code = code;
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

}

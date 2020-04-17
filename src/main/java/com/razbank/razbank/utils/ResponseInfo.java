package com.razbank.razbank.utils;

import lombok.Getter;


@Getter
public enum ResponseInfo {

    OK("OK", 0),
    GENERIC_ERROR("GENERIC ERROR", 1),
    CONTROLLER_ERROR("CONTROLLER ERROR", 2),
    SERVICE_ERROR("SERVICE ERROR", 3),
    REQUEST_ERROR("REQUEST ERROR", 4),
    COMMAND_ERROR("COMMAND ERROR", 5);

    private String value;
    private  int code;

    ResponseInfo(String value, int code) {
        this.value = value;
        this.code = code;
    }

}

package com.razbank.razbank.exceptions;

import com.razbank.razbank.utils.ResponseInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RazBankException extends Exception {

    private final ResponseInfo responseInfo;
    private final String where;
    private final Object param;

    public RazBankException(String message, ResponseInfo responseInfo, String where, Object param) {
        super(message);
        this.responseInfo = responseInfo;
        this.where = where;
        this.param = param;
    }


}

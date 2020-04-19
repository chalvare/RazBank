package com.razbank.razbank.exceptions.generic;

import com.razbank.razbank.utils.ResponseInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RazBankException extends RuntimeException{

    private ResponseInfo responseInfo;
    private String where;
    private Object param;

    public RazBankException() {
        super();
    }

    public RazBankException(String message, ResponseInfo responseInfo, String where, Object param) {
        super(message);
        this.responseInfo=responseInfo;
        this.where = where;
        this.param = param;
    }

    public RazBankException(String message) {
        super(message);
    }

    public RazBankException(String message, Throwable cause) {
        super(message, cause);
    }

    public RazBankException(Throwable cause) {
        super(cause);
    }

    protected RazBankException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

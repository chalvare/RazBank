package com.razbank.razbank.exceptions;

import com.razbank.razbank.utils.ResponseInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>RazBank Exception</h1>
 * Custom exception for RazBank
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-17
 */
@Getter
@Setter
public class RazBankException extends RuntimeException {

    private final ResponseInfo responseInfo;
    private final String where;
    private final Object param;

    /**
     * Constructor
     *
     * @param message of the error
     * @param responseInfo info about error
     * @param where indicates where error occurs
     * @param param additional information such as object
     */
    public RazBankException(String message, ResponseInfo responseInfo, String where, Object param) {
        super(message);
        this.responseInfo = responseInfo;
        this.where = where;
        this.param = param;
    }


}

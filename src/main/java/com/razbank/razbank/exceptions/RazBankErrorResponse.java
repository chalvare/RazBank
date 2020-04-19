package com.razbank.razbank.exceptions;

import com.razbank.razbank.utils.ResponseInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>RazBank Exception response</h1>
 * Custom exception for RazBank
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-19
 */
@Getter
@Setter
@AllArgsConstructor
public class RazBankErrorResponse {

    private String message;
    private ResponseInfo responseInfo;
    private String where;
    private Object param;
    
}

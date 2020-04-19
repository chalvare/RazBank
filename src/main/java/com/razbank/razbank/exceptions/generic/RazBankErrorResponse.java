package com.razbank.razbank.exceptions.generic;

import com.razbank.razbank.utils.ResponseInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RazBankErrorResponse {

    private String message;
    private ResponseInfo responseInfo;
    private String where;
    private Object param;
    
}

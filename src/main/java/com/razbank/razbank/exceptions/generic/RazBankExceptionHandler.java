package com.razbank.razbank.exceptions.generic;

import com.razbank.razbank.exceptions.RazBankErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RazBankExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RazBankErrorResponse>handleException(RazBankException e){

        RazBankErrorResponse error = new RazBankErrorResponse(e.getMessage(), e.getResponseInfo(), e.getWhere(), e.getParam());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}

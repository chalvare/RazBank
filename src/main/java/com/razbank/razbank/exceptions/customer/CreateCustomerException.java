package com.razbank.razbank.exceptions.customer;

public class CreateCustomerException extends RuntimeException {

    public CreateCustomerException() {
        super();
    }

    public CreateCustomerException(String message) {
        super(message);
    }

    public CreateCustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateCustomerException(Throwable cause) {
        super(cause);
    }

    protected CreateCustomerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

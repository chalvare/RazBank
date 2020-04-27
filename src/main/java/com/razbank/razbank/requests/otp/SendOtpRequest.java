package com.razbank.razbank.requests.otp;

import com.razbank.razbank.entities.otp.Otp;
import com.razbank.razbank.requests.Request;

public interface SendOtpRequest extends Request {
    void setOtp(Otp otp);
}

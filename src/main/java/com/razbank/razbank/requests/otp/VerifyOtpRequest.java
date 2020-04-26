package com.razbank.razbank.requests.otp;

import com.razbank.razbank.entities.otp.Otp;
import com.razbank.razbank.requests.Request;

public interface VerifyOtpRequest extends Request {
    void buildOtp(Otp otp);
}

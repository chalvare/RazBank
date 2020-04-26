package com.razbank.razbank.commands.otp;

import com.razbank.razbank.commands.Command;
import com.razbank.razbank.requests.otp.VerifyOtpRequest;
import com.razbank.razbank.requests.otp.VerifyOtpRequestImpl;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class VerifyOtpCommand implements Command {

    private VerifyOtpRequestImpl otpRequest;

    public abstract void verifyOtp();
    public abstract void setVerifyOtpRequest(VerifyOtpRequest verifyOtpRequest);
}

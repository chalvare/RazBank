package com.razbank.razbank.commands.otp;

import com.razbank.razbank.commands.Command;
import com.razbank.razbank.requests.otp.SendOtpRequest;
import com.razbank.razbank.requests.otp.SendOtpRequestImpl;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SendOtpCommand implements Command {

    private SendOtpRequestImpl otpRequest;

    public abstract void sendOtp();
    public abstract void setSendOtpRequest(SendOtpRequest sendOtpRequest);
}

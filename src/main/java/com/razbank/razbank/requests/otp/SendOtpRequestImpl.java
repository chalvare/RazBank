package com.razbank.razbank.requests.otp;

import com.razbank.razbank.entities.otp.Otp;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SendOtpRequestImpl implements SendOtpRequest {

    private Otp otp;

    @Override
    public void setOtp(Otp otp) {
        this.otp=otp;
    }
}

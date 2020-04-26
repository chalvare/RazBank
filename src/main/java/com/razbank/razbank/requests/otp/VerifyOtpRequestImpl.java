package com.razbank.razbank.requests.otp;

import com.razbank.razbank.entities.otp.Otp;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class VerifyOtpRequestImpl implements VerifyOtpRequest {

    private Otp otp;

    @Override
    public void buildOtp(Otp otp) {
        this.otp=otp;
    }
}

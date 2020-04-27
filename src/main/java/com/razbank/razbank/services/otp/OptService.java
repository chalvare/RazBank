package com.razbank.razbank.services.otp;

import com.razbank.razbank.dtos.otp.OtpDTO;
import com.razbank.razbank.responses.otp.SaveOtpResponse;
import com.razbank.razbank.responses.otp.VerifyOtpResponse;

public interface OptService {

    SaveOtpResponse save(OtpDTO otpDTO);
    VerifyOtpResponse verify(String otpCode, int customerId);
}

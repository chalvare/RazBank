package com.razbank.razbank.responses.otp;

import com.razbank.razbank.entities.otp.Otp;
import com.razbank.razbank.responses.GenericResponse;
import com.razbank.razbank.utils.ResponseInfo;
import lombok.Data;

@Data
public class VerifyOtpResponse extends GenericResponse {
   private Otp otp;

   public void buildVerifyOtpResponse(VerifyOtpResponse response, Otp otp, String message) {
      response.setOtp(otp);
      response.setResponseInfo(ResponseInfo.OK);
      response.setMessage(message);
   }
}
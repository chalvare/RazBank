package com.razbank.razbank.dtos.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpDTO {

    private int id;

    private int customerId;

    private String phone;

    private String otpCode;

    private long expiryTime;
}

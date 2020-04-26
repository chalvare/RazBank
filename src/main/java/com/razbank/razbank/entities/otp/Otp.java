package com.razbank.razbank.entities.otp;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import lombok.experimental.Tolerate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "otp")
@Data
@Builder
@With
@Component
public class Otp implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "phone")
    @NotBlank(message = "phone is mandatory")
    private String phone;

    @Column(name = "otp_code")
    @NotBlank(message = "otp is mandatory")
    private String otpCode;

    @Column(name = "expiry_time")
    private long expiryTime;

    @Tolerate
    public Otp() {}
}

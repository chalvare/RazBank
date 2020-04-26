package com.razbank.razbank.controllers.otp;

import com.razbank.razbank.services.otp.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <h1>Otp Controller Implementation</h1>
 * Class which works as a controller accounts
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-22
 */
@Controller
@RequestMapping("/otp")
public class OtpController {

    private static final Logger logger = LoggerFactory.getLogger(OtpController.class);
    private OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    /**
     * Send OTP to customer
     *
     * @param customerId object
     * @return List<Account> object
     */
    @GetMapping("/otp")
    public void sendOtp() {
        otpService.save();

    }

    @DeleteMapping("/otp")
    public void verifyOtp() {
        otpService.verify();

    }

}

package com.razbank.razbank.controllers.otp;

import com.razbank.razbank.dtos.otp.OtpDTO;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.responses.otp.SaveOtpResponse;
import com.razbank.razbank.responses.otp.VerifyOtpResponse;
import com.razbank.razbank.services.otp.OtpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <h1>Otp Controller Implementation</h1>
 * Class which works as a controller accounts
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-26
 */
@Controller
@RequestMapping("/otp")
public class OtpController {
    //TODO Configurar el controller de otp para usarlo despues de la contraseña en el onboarding
    private static final String SESSION = "SESSION";
    private OtpServiceImpl otpServiceImpl;

    @Autowired
    public OtpController(OtpServiceImpl otpServiceImpl) {
        this.otpServiceImpl = otpServiceImpl;
    }

    /**
     * Send OTP to customer
     *
     * @param request object
     * @return List<Account> object
     */
    @GetMapping("/otp")
    public String sendOtp(HttpServletRequest request) {
        Customer c = getCustomerFromSession(request);
        OtpDTO otpDTO = new OtpDTO();
        otpDTO.setCustomerId(c.getId());
        otpDTO.setPhone(c.getContactInformation().getPhone());
        SaveOtpResponse response = otpServiceImpl.save(otpDTO);
        if (response.getResponseInfo().getCode() == 0) {
            return "OK";
        } else {
            return "KO";
        }
    }

    /**
     * Method which verify and delete otp from customer in database
     *
     * @param otp     string
     * @param request HttpServletRequest
     * @return String
     */
    @DeleteMapping("/otp")
    public String verifyOtp(@RequestBody String otp, HttpServletRequest request) {
        Customer c = getCustomerFromSession(request);
        VerifyOtpResponse response = otpServiceImpl.verify(otp, c.getId());
        if (response.getResponseInfo().getCode() == 0) {
            return "OK";
        } else {
            return "KO";
        }

    }

    /**
     * Method which gets customer from session
     *
     * @param request HttpServletRequest
     * @return Customer object
     */
    private Customer getCustomerFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Customer) session.getAttribute(SESSION);
    }

}

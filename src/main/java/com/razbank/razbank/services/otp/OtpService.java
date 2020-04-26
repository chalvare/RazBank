package com.razbank.razbank.services.otp;

import com.razbank.razbank.commands.otp.SendOtpCommandImpl;
import com.razbank.razbank.commands.otp.VerifyOtpCommandImpl;
import com.razbank.razbank.entities.otp.Otp;
import com.razbank.razbank.exceptions.generic.RazBankException;
import com.razbank.razbank.requests.otp.SendOtpRequest;
import com.razbank.razbank.requests.otp.SendOtpRequestImpl;
import com.razbank.razbank.requests.otp.VerifyOtpRequest;
import com.razbank.razbank.requests.otp.VerifyOtpRequestImpl;
import com.razbank.razbank.utils.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import static com.razbank.razbank.commands.otp.SendOtpCommandImpl.MINUTES;

@Service
public class OtpService {

    //TODO faltan responses y la interfaz del servicio y programar bien save()
    private static final String CLASSNAME = OtpService.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);
    private final SendOtpCommandImpl sendOtpCommand;
    private final VerifyOtpCommandImpl verifyOtpCommand;

    @Autowired
    public OtpService(SendOtpCommandImpl sendOtpCommand, VerifyOtpCommandImpl verifyOtpCommand) {
        this.sendOtpCommand = sendOtpCommand;
        this.verifyOtpCommand = verifyOtpCommand;
    }

    public void save( ) {
        logger.info("SERVICE: {}", CLASSNAME);

        Otp otpPrueba=null;
        try {
            SendOtpRequest sendOtpRequest = new SendOtpRequestImpl();

            Random rand = SecureRandom.getInstanceStrong();
            String code = String.format("%04d", rand.nextInt(10000));
            long expiryTime = System.currentTimeMillis()+(1000*60*MINUTES);
            otpPrueba = Otp.builder()
                    .customerId(1)
                    .phone("+34676658224")
                    .otpCode(code)
                    .expiryTime(expiryTime)
                    .build();

            sendOtpRequest.buildOtp(otpPrueba);

            sendOtpCommand.setSendOtpRequest(sendOtpRequest);
            sendOtpCommand.execute();

        } catch (RazBankException | NoSuchAlgorithmException e) {
            String method = OtpService.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.SERVICE_ERROR, CLASSNAME + ":" + method, otpPrueba);
        }


    }

    public void verify( ) {
        logger.info("SERVICE: {}", CLASSNAME);

        Otp otpPrueba=null;
        try {
            VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequestImpl();

            otpPrueba = Otp.builder()
                    .customerId(1)
                    .otpCode("0663")
                    .build();

            verifyOtpRequest.buildOtp(otpPrueba);

            verifyOtpCommand.setVerifyOtpRequest(verifyOtpRequest);
            verifyOtpCommand.execute();

        } catch (RazBankException e) {
            String method = OtpService.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.SERVICE_ERROR, CLASSNAME + ":" + method, otpPrueba);
        }


    }

}

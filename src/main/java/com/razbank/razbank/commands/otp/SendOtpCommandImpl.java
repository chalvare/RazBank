package com.razbank.razbank.commands.otp;

import com.razbank.razbank.entities.otp.Otp;
import com.razbank.razbank.exceptions.generic.RazBankException;
import com.razbank.razbank.repositories.opt.OtpRepository;
import com.razbank.razbank.requests.otp.SendOtpRequest;
import com.razbank.razbank.requests.otp.SendOtpRequestImpl;
import com.razbank.razbank.utils.ResponseInfo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Component
@Getter
@Setter
public class SendOtpCommandImpl extends SendOtpCommand {

    private static final String CLASSNAME = SendOtpCommandImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(SendOtpCommandImpl.class);

    @Value("${twilio.otp.accountsid}")
    private String accountSid;
    @Value("${twilio.otp.authid}")
    private String authId;

    @Value("${twilio.otp.phone}")
    public String phone;

    public static final int MINUTES = 10;
    private final OtpRepository otpRepository;
    private SendOtpRequestImpl sendOtpRequest;
    private Otp otp;
    private boolean success;

    @Autowired
    public SendOtpCommandImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public void sendOtp() {
        Otp ot = null;
        try{
            ot = sendOtpRequest.getOtp();
            otp = generateOtpCode(ot);
            Twilio.init(accountSid, authId);
            Message.creator(new PhoneNumber(otp.getPhone()),
                    new PhoneNumber(phone),
                    otp.getOtpCode()).create();
            otpRepository.save(otp);
            this.setOtp(ot);
            this.setSuccess(true);
        }catch (Exception e) {
            this.setSuccess(false);
            String method = SendOtpCommandImpl.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.COMMAND_ERROR, CLASSNAME + ":" + method, otp);
        }

    }

    private Otp generateOtpCode(Otp otp) throws NoSuchAlgorithmException {
        Random rand = SecureRandom.getInstanceStrong();
        String code = String.format("%04d", rand.nextInt(10000));
        long expiryTime = System.currentTimeMillis()+(1000*60*MINUTES);
        return otp.withOtpCode(code).withExpiryTime(expiryTime);
    }

    @Override
    public void setSendOtpRequest(SendOtpRequest sendOtpRequest) {
        this.sendOtpRequest = (SendOtpRequestImpl) sendOtpRequest;
    }

    @Override
    public void execute() {
        sendOtp();
    }
}

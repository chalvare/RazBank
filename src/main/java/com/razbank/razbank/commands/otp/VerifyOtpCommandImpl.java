package com.razbank.razbank.commands.otp;

import com.razbank.razbank.entities.otp.Otp;
import com.razbank.razbank.exceptions.generic.RazBankException;
import com.razbank.razbank.repositories.opt.OtpRepository;
import com.razbank.razbank.requests.otp.VerifyOtpRequest;
import com.razbank.razbank.requests.otp.VerifyOtpRequestImpl;
import com.razbank.razbank.utils.ResponseInfo;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class VerifyOtpCommandImpl extends VerifyOtpCommand {

    private static final String CLASSNAME = VerifyOtpCommandImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(VerifyOtpCommandImpl.class);

    private final OtpRepository otpRepository;
    private VerifyOtpRequestImpl verifyOtpRequest;
    private boolean success;

    @Autowired
    public VerifyOtpCommandImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public void verifyOtp() {
        Otp otp = null;
        try {
            otp = verifyOtpRequest.getOtp();
            final int id = otp.getCustomerId();
            Otp otpDB = otpRepository.findFirstByCustomerIdOrderByExpiryTimeDesc(id)
                    .orElseThrow(() -> new RazBankException("Customer id not found - " + id));

            if (checkOtp(otp, otpDB)) {
                long deletedRows = otpRepository.deleteByCustomerId(id);
                logger.info("OTP - number of deleted records: {}",deletedRows);
                this.setSuccess(true);
            } else {
                this.setSuccess(false);
            }
        } catch (Exception e) {
            this.setSuccess(false);
            String method = SendOtpCommandImpl.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.COMMAND_ERROR, CLASSNAME + ":" + method, otp);
        }

    }

    private boolean checkOtp(Otp otp, Otp otpDb) {
        return otp.getOtpCode().equalsIgnoreCase(otpDb.getOtpCode()) &&
                otpDb.getExpiryTime() > System.currentTimeMillis();
    }

    @Override
    public void setVerifyOtpRequest(VerifyOtpRequest verifyOtpRequest) {
        this.verifyOtpRequest = (VerifyOtpRequestImpl) verifyOtpRequest;

    }

    @Override
    public void execute() {
        verifyOtp();
    }
}

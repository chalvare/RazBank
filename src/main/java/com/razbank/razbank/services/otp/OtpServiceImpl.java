package com.razbank.razbank.services.otp;

import com.razbank.razbank.commands.otp.SendOtpCommandImpl;
import com.razbank.razbank.commands.otp.VerifyOtpCommandImpl;
import com.razbank.razbank.dtos.otp.OtpDTO;
import com.razbank.razbank.entities.otp.Otp;
import com.razbank.razbank.exceptions.generic.RazBankException;
import com.razbank.razbank.requests.otp.SendOtpRequest;
import com.razbank.razbank.requests.otp.SendOtpRequestImpl;
import com.razbank.razbank.requests.otp.VerifyOtpRequest;
import com.razbank.razbank.requests.otp.VerifyOtpRequestImpl;
import com.razbank.razbank.responses.otp.SaveOtpResponse;
import com.razbank.razbank.responses.otp.VerifyOtpResponse;
import com.razbank.razbank.utils.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h1>Otp Service Implementation</h1>
 * Class which works as a controller service
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-26
 */
@Service
public class OtpServiceImpl implements OptService{

    //TODO faltan responses y la interfaz del servicio y programar bien save() y veritfy
    private static final String CLASSNAME = OtpServiceImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);
    private final SendOtpCommandImpl sendOtpCommand;
    private final VerifyOtpCommandImpl verifyOtpCommand;

    /**
     * Constructor
     *
     * @param sendOtpCommand object
     * @param verifyOtpCommand object
     */
    @Autowired
    public OtpServiceImpl(SendOtpCommandImpl sendOtpCommand, VerifyOtpCommandImpl verifyOtpCommand) {
        this.sendOtpCommand = sendOtpCommand;
        this.verifyOtpCommand = verifyOtpCommand;
    }

    /**
     * Service which saves otp in database and sends otp to customer
     *
     * @param otpDTO object
     * @return saveOtpResponse of service
     */
    @Override
    public SaveOtpResponse save(OtpDTO otpDTO) {
        logger.info("SERVICE: {}", CLASSNAME);
        SaveOtpResponse response = new SaveOtpResponse();
        Otp otp=null;
        try {
            SendOtpRequest sendOtpRequest = new SendOtpRequestImpl();
            //otp = buildOtp(otpDTO);//TODO descomentar esto y eliminar lo de abajo
            otp=Otp.builder().id(1).phone("+34676658224").expiryTime(1234567890L).build();
            sendOtpRequest.setOtp(otp);
            sendOtpCommand.setSendOtpRequest(sendOtpRequest);
            sendOtpCommand.execute();

        } catch (RazBankException e) {
            String method = OtpServiceImpl.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.SERVICE_ERROR, CLASSNAME + ":" + method, otp);
        }

        if (sendOtpCommand.isSuccess()) {
            logger.error("SUCCESS");
            response.buildSaveOtpResponse(response, sendOtpCommand.getOtp(), ResponseInfo.OK.getValue());
        }

        return response;


    }


    /**
     * Service which verifies otp from customer
     *
     * @param otpCode object
     * @param customerId object
     * @return verifyOtpResponse
     */
    @Override
    public VerifyOtpResponse verify(String otpCode, int customerId ) {
        logger.info("SERVICE: {}", CLASSNAME);
        VerifyOtpResponse response = new VerifyOtpResponse();
        Otp otp=null;
        try {
            VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequestImpl();

            otp = buildOtp("0663",1);//todo sustituir por variables de entrada

            verifyOtpRequest.buildOtp(otp);

            verifyOtpCommand.setVerifyOtpRequest(verifyOtpRequest);
            verifyOtpCommand.execute();

        } catch (RazBankException e) {
            String method = OtpServiceImpl.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.SERVICE_ERROR, CLASSNAME + ":" + method, otp);
        }
        if (sendOtpCommand.isSuccess()) {
            logger.error("SUCCESS");
            response.buildVerifyOtpResponse(response, otp, ResponseInfo.OK.getValue());
        }

        return response;

    }

    /**
     * Otp builder
     *
     * @param otpDTO object
     * @return Otp
     */
    private Otp buildOtp(OtpDTO otpDTO) {
        return Otp.builder()
                .customerId(otpDTO.getCustomerId())
                .phone(otpDTO.getPhone())
                .build();
    }

    /**
     * Otp builder
     *
     * @param otpCode object
     * @param customerId object
     * @return Otp
     */
    private Otp buildOtp(String otpCode, int customerId) {
        return Otp.builder()
                .customerId(customerId)
                .otpCode(otpCode)
                .build();
    }


}

package com.razbank.razbank.riskengines.crs;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Locale;

public class CrsEnginePhone implements CrsEngine {

    private static final Logger logger = LoggerFactory.getLogger(CrsEnginePhone.class);

    private static final String ESPANYA = "ES";

    @Override
    public boolean execute(CrsPersonInfo crsPersonInfo) {
        try {
            PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber spanishNumber=pnu.parse(crsPersonInfo.getContactInformation().getPhone(), Locale.getDefault().getCountry());
            return (pnu.isValidNumberForRegion(spanishNumber,Locale.getDefault().getCountry())
                    && Locale.getDefault().getCountry().equalsIgnoreCase(ESPANYA));
        } catch (NumberParseException e) {
            logger.error("Executing CrsEnginePhone: ", e);
        }
        return false;
    }

}

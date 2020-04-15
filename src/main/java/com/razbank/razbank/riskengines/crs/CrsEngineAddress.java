package com.razbank.razbank.riskengines.crs;

import java.util.Locale;

public class CrsEngineAddress implements CrsEngine {

    @Override
    public boolean execute(CrsPersonInfo crsPersonInfo) {

        return crsPersonInfo.getContactInformation().getCountry().equalsIgnoreCase(Locale.getDefault().getDisplayCountry());

    }

}

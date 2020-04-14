package com.razbank.razbank.riskengines.crs;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;
import java.util.Set;

public class CrsEnginePhone implements CrsEngine {
    @Override
    public boolean execute(CrsPersonInfo crsPersonInfo) {
        try {
            Locale.getDefault().getCountry();
            Phonenumber.PhoneNumber phone = PhoneNumberUtil.getInstance().parse("676658224", Locale.getDefault().getCountry());
            String region= PhoneNumberUtil.getInstance().getRegionCodeForNumber(phone);
            Locale locale = new Locale("es", region);
            String countryofphone=locale.getDisplayCountry();
            String country = "españa";//crsPersonInfo.getContactInformation().getCountry();

        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        //españa


        return false;
    }


    public  void infor(){
        Set<String> set = PhoneNumberUtil.getInstance().getSupportedRegions();

        String[] arr = set.toArray(new String[set.size()]);

        for (int i = 0; i < arr.length; i++) {
            Locale locale = new Locale("es", arr[i]);
            System.out.println( "lib country:" + arr[i] + "  "+ locale.getDisplayCountry());
        }
    }
}

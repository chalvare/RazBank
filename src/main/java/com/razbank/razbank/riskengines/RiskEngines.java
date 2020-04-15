package com.razbank.razbank.riskengines;

import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.riskengines.crs.CrsEngine;
import com.razbank.razbank.riskengines.crs.CrsEngineAddress;
import com.razbank.razbank.riskengines.crs.CrsEnginePhone;
import com.razbank.razbank.riskengines.crs.CrsPersonInfo;

import java.util.List;

public class RiskEngines {

        private RiskEngines(){
            throw new IllegalStateException("Utility class");
        }

        public static boolean crsEnginesExecution(List<RiskEnginesEnum> engines, Customer customer){
            CrsEngine crsEngine;
            CrsPersonInfo crsPersonInfo = new CrsPersonInfo();
            boolean result = true;
            for(RiskEnginesEnum engine: engines){
                switch (engine){
                    case CRS_PHONE:
                        crsEngine = new CrsEnginePhone();
                        break;
                    case CRS_ADDRESS:
                        crsEngine = new CrsEngineAddress();
                        break;
                    default:
                        return false;
                }
                result=result&&crsEngine.execute(crsPersonInfo.build(customer));

            }
            return result;

        }
}

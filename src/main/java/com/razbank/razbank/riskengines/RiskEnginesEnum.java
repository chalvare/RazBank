package com.razbank.razbank.riskengines;

import lombok.Getter;

@Getter
public enum RiskEnginesEnum {

    CRS_PHONE("CRS_PHONE"),
    CRS_ADDRESS("CRS_ADDRESS");

    private String name;

    RiskEnginesEnum(String name) {
        this.name = name;
    }

}

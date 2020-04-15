package com.razbank.razbank.riskengines;

import lombok.Getter;

@Getter
public enum RestrictionEnum {

    ZCRS("ZCRS");

    private String name;

    RestrictionEnum(String name) {
        this.name = name;
    }

}

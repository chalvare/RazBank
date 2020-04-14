package com.razbank.razbank.riskengines;

import lombok.Getter;

@Getter
public enum Restriction {

    ZCRS("ZCRS");

    private String name;

    Restriction(String name) {
        this.name = name;
    }

}

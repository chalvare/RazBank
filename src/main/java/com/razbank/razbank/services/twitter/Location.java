package com.razbank.razbank.services.twitter;

import lombok.Getter;

@Getter
public enum Location {


    MADRID(new double[][]{
            new double[]{40.211, -4.575},
            new double[]{40.688, -3.385}});

    private double[][] co;

    Location(double[][] co) {
        this.co = co;
    }



}

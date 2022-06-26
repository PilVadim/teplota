package com.pilvadim.teplota.model.temporal;

public class FloatStorage {

    private Integer number = 0;
    private Float sum = 0.0f;

    public Float getAverage(){
        return Math.round( (sum/number) * 100 ) / 100.0f;
    }

    public void addSum( Float sum ) {
        this.sum += sum;
        this.number += 1;
    }

}

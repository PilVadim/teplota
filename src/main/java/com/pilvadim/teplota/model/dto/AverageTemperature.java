package com.pilvadim.teplota.model.dto;

import java.time.LocalDateTime;

public class AverageTemperature {

    private final Float celsius;
    private final LocalDateTime moment;

    public Float getCelsius() {
        return celsius;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public AverageTemperature( LocalDateTime moment, Float celsius ) {
        this.moment = moment;
        this.celsius = celsius;
    }
}

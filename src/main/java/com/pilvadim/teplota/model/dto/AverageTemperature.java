package com.pilvadim.teplota.model.dto;

import java.time.LocalDateTime;

public class AverageTemperature {

    private Float celsius;
    private LocalDateTime moment;

    public Float getCelsius() {
        return celsius;
    }

    public void setCelsius(Float celsius) {
        this.celsius = celsius;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }
}

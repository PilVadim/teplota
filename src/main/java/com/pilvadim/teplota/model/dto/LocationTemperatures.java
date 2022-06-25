package com.pilvadim.teplota.model.dto;

import com.pilvadim.teplota.model.Temperature;

import java.util.ArrayList;
import java.util.List;

public class LocationTemperatures {

    private List<Temperature> all;
    private List<AverageTemperature> averageDays;
    private List<AverageTemperature> averageWeeks;
    private List<AverageTemperature> averageMonths;

    public List<Temperature> getAll() {
        return all;
    }

    public List<AverageTemperature> getAverageDays() {
        return averageDays;
    }

    public List<AverageTemperature> getAverageWeeks() {
        return averageWeeks;
    }

    public List<AverageTemperature> getAverageMonths() {
        return averageMonths;
    }

    public LocationTemperatures() {

        this.all = new ArrayList<>();
        this.averageDays = new ArrayList<>();
        this.averageWeeks = new ArrayList<>();
        this.averageMonths = new ArrayList<>();

    }
}

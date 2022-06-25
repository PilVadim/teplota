package com.pilvadim.teplota.model.dto;

import com.pilvadim.teplota.model.Temperature;

import java.util.List;

public class LocationTemperatures {

    Integer id;
    String name;
    List<Temperature> temperatures;
    List<AverageTemperature> averageDays;
    List<AverageTemperature> averageWeeks;
    List<AverageTemperature> averageMonths;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Temperature> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    public List<AverageTemperature> getAverageDays() {
        return averageDays;
    }

    public void setAverageDays(List<AverageTemperature> averageDays) {
        this.averageDays = averageDays;
    }

    public List<AverageTemperature> getAverageWeeks() {
        return averageWeeks;
    }

    public void setAverageWeeks(List<AverageTemperature> averageWeeks) {
        this.averageWeeks = averageWeeks;
    }

    public List<AverageTemperature> getAverageMonths() {
        return averageMonths;
    }

    public void setAverageMonths(List<AverageTemperature> averageMonths) {
        this.averageMonths = averageMonths;
    }
}

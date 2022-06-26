package com.pilvadim.teplota.service;

import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.model.dto.GroupedTemperatures;
import com.pilvadim.teplota.repository.TemperatureRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Provides services to work with Temperature
 */
@Service
public class TemperatureService {

    final TemperatureRepo tr;
    final AggregationService as;

    public TemperatureService(TemperatureRepo tr,
                              AggregationService as) {
        this.tr = tr;
        this.as = as;
    }

    public List<GroupedTemperatures> getTemperaturesForPeriod(LocalDateTime start, LocalDateTime end ){
        return as.aggregateTemperatures( tr.getTemperaturesForPeriod( start,end ) );
    }

    public void save( Temperature t ){
        System.out.println( tr.save( t ) );
    }

}

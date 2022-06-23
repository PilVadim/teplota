package com.pilvadim.teplota.service;

import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.repository.TemperatureRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TemperatureService {

    final TemperatureRepo tr;

    public TemperatureService(TemperatureRepo tr) {
        this.tr = tr;
    }

    public List<Temperature> getTemperaturesForPeriod(LocalDateTime start, LocalDateTime end ){
        return tr.getTemperaturesForPeriod( start,end );
    }

}

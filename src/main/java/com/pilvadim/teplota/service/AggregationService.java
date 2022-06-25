package com.pilvadim.teplota.service;

import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.model.dto.GroupedTemperatures;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregationService {

    List<GroupedTemperatures> aggregateTemperatures( List<Temperature> temperatures ){
        return null;
    }

}

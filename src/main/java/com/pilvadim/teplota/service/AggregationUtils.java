package com.pilvadim.teplota.service;

import com.pilvadim.teplota.enums.AggregationType;
import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.model.dto.AverageTemperature;
import com.pilvadim.teplota.model.temporal.FloatStorage;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Additional aggregation methods
 */
@Service
public class AggregationUtils {

    /**
     * Adds temperature value to aggregator map by type
     * @param t Temperature for distribution
     * @param at Type of aggregation
     * @param aggregator Map to store intermediate data
     */
    public void addCelsiusToAggregator( Temperature t,
                                        AggregationType at,
                                        Map< LocalDateTime, FloatStorage > aggregator ){

        LocalDateTime ldtM = getKey( t.getMoment(), at );
        if ( ! aggregator.containsKey(ldtM) )
            aggregator.put( ldtM, new FloatStorage() );
        aggregator.get( ldtM ).addSum( t.getCelsius() );

    }

    /**
     * Returns key for aggregation map by type
     * @param ldt LocalDateTime moment from Temperature object
     * @param at AggregationType
     * @return LocalDateTime key
     */
    public LocalDateTime getKey(LocalDateTime ldt, AggregationType at) {
        switch (at){
            case DAY:
                return ldt.toLocalDate().atTime(LocalTime.MIN);
            case WEEK:
                return ldt.toLocalDate().atTime(LocalTime.MIN).with(DayOfWeek.MONDAY);
            case MONTH:
                return ldt.toLocalDate().atTime(LocalTime.MIN).withDayOfMonth(1);
            default:
                return ldt;
        }
    }

    /**
     * Convert aggregation map into list of average values
     * @param averages List of average value
     * @param aggregation Map of accumulated data for calculate average
     */
    public void addAggregated( List<AverageTemperature> averages,
                                Map<LocalDateTime, FloatStorage> aggregation ) {

        for ( LocalDateTime ldt : aggregation.keySet() ){
            averages.add( new AverageTemperature( ldt, aggregation.get(ldt).getAverage() ) );
        }

    }

}

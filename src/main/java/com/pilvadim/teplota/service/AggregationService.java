package com.pilvadim.teplota.service;

import com.pilvadim.teplota.enums.AggregationType;
import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.model.dto.AverageTemperature;
import com.pilvadim.teplota.model.dto.GroupedTemperatures;
import com.pilvadim.teplota.model.dto.LocationTemperatures;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AggregationService {

    final PlaceService ps;

    public AggregationService(PlaceService ps) {
        this.ps = ps;
    }

    public List<GroupedTemperatures> aggregateTemperatures( List<Temperature> temperatures ){

        List<GroupedTemperatures> results = new ArrayList<>();
        List<Place> places = getTemperaturesPlaces( temperatures );

        for ( Place pl : places ) {
            results.add( groupTemperatures( temperatures, pl ) );
        }

        return results;
    }

    private List<Place> getTemperaturesPlaces( List<Temperature> temperatures ){

        Set<Integer> locationIds = new HashSet<>();
        temperatures.forEach(t -> locationIds.add(t.getPlaceId()));

        return ps.getAllPlacesByIds( new ArrayList<>(locationIds) );

    }

    private GroupedTemperatures groupTemperatures( List<Temperature> temperatures, Place pl ) {

        GroupedTemperatures gt = new GroupedTemperatures( pl );

        List<Temperature> placeTemps = temperatures.stream()
                .filter( t -> pl.getId().equals(t.getPlaceId()) )
                .sorted( Comparator.comparing(Temperature::getMoment) )
                .collect( Collectors.toList() );

        Map< LocalDateTime, FloatStorage > daysAggregation = new LinkedHashMap<>();
        Map< LocalDateTime, FloatStorage > weeksAggregation = new LinkedHashMap<>();
        Map< LocalDateTime, FloatStorage > monthsAggregation = new LinkedHashMap<>();

        for ( Temperature t : placeTemps ){
            addCelsiusToAggregator( t, AggregationType.DAY, daysAggregation );
            addCelsiusToAggregator( t, AggregationType.WEEK, weeksAggregation );
            addCelsiusToAggregator( t, AggregationType.MONTH, monthsAggregation );
        }

        LocationTemperatures lt = gt.getTemperatures();

        lt.getAll().addAll(temperatures);

        addAggregated( lt.getAverageDays(), daysAggregation );
        addAggregated( lt.getAverageWeeks(), weeksAggregation );
        addAggregated( lt.getAverageMonths(), monthsAggregation );

        return gt;
    }

    private void addCelsiusToAggregator( Temperature t,
                                         AggregationType at,
                                         Map< LocalDateTime, FloatStorage > aggregator ){

        LocalDateTime ldtM = getKey( t.getMoment(), at );
        if ( ! aggregator.containsKey(ldtM) )
            aggregator.put( ldtM, new FloatStorage() );
        aggregator.get( ldtM ).addSum( t.getCelsius() );

    }

    private LocalDateTime getKey(LocalDateTime ldt, AggregationType at) {
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

    private void addAggregated( List<AverageTemperature> averages,
                                Map<LocalDateTime, FloatStorage> aggregation ) {

        for ( LocalDateTime ldt : aggregation.keySet() ){
            averages.add( new AverageTemperature( ldt, aggregation.get(ldt).getAverage() ) );
        }

    }

    private class FloatStorage {
        private Integer number = 0;
        private Float sum = 0.0f;

        Float getAverage(){
            return Math.round( (sum/number) * 100 ) / 100.0f;
        }

        public void addSum( Float sum ) {
            this.sum += sum;
            this.number += 1;
        }

    }

}

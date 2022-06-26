package com.pilvadim.teplota.service;

import com.pilvadim.teplota.enums.AggregationType;
import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.model.dto.GroupedTemperatures;
import com.pilvadim.teplota.model.dto.LocationTemperatures;
import com.pilvadim.teplota.model.temporal.FloatStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AggregationService {

    final PlaceService ps;
    final AggregationUtils au;

    public AggregationService(PlaceService ps,
                              AggregationUtils au) {
        this.ps = ps;
        this.au = au;
    }

    /**
     * Initiates average calculating and grouping for temperatures
     * @param temperatures List of Temperature input
     * @return GroupedTemperatures
     */
    public List<GroupedTemperatures> aggregateTemperatures( List<Temperature> temperatures ){

        List<GroupedTemperatures> results = new ArrayList<>();
        List<Place> places = getTemperaturesPlaces( temperatures );

        for ( Place pl : places ) {
            results.add( groupTemperatures( temperatures, pl ) );
        }

        return results;
    }

    /**
     * Gets list of Places which are used in Temperatures input
     * @param temperatures List of Temperatures
     * @return List of Places
     */
    private List<Place> getTemperaturesPlaces( List<Temperature> temperatures ){

        Set<Integer> locationIds = new HashSet<>();
        temperatures.forEach( t -> locationIds.add(t.getPlaceId()) );

        return ps.getAllPlacesByIds( new ArrayList<>(locationIds) );

    }

    /**
     * Generates GroupedTemperatures from filtered, sorted and distributed temperatures
     * @param temperatures full List of Temperatures
     * @param pl current Place
     * @return GroupedTemperatures
     */
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
            au.addCelsiusToAggregator( t, AggregationType.DAY, daysAggregation );
            au.addCelsiusToAggregator( t, AggregationType.WEEK, weeksAggregation );
            au.addCelsiusToAggregator( t, AggregationType.MONTH, monthsAggregation );
        }

        LocationTemperatures lt = gt.getTemperatures();

        lt.getAll().addAll(placeTemps);

        au.addAggregated( lt.getAverageDays(), daysAggregation );
        au.addAggregated( lt.getAverageWeeks(), weeksAggregation );
        au.addAggregated( lt.getAverageMonths(), monthsAggregation );

        return gt;
    }
}

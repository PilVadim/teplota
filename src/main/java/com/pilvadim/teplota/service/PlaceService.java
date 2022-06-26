package com.pilvadim.teplota.service;

import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.repository.PlaceRepo;
import com.pilvadim.teplota.service.exception.WeatherBadRequestException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

/**
 * Provides services to receive whole list of places, enabled places, add new place, update existing place
 */
@Service
@SessionScope
public class PlaceService {

    final PlaceRepo pr;

    public PlaceService(PlaceRepo pr) {
        this.pr = pr;
    }

    /**
     * Receives whole list of places
     * @return list of current places
     */
    public List<Place> getAllPlaces(){
        return pr.getAllPlaces();
    }

    /**
     * Receives list of enabled places
     * @return list of enabled places
     */
    public List<Place> getAllEnabledPlaces(){
        return pr.getAllEnabledPlaces();
    }

    /**
     * Receives list of places by Ids list
     * @return list of enabled places
     */
    public List<Place> getAllPlacesByIds( List<Integer> ids ){
        return pr.getAllPlacesByIds( ids.toArray(new Integer[0]) );
    }

    /**
     * Validates and inserts a new place in the database
     * @param pl new place
     * @return id of a new place
     */
    public Integer addPlace( Place pl ){
        validateInsert( pl );
        synchronized (this) {
            return pr.insert(pl);
        }
    }

    private void validateInsert(Place pl) {

        if ( pl.getId() != null )
            throw new WeatherBadRequestException( "Id field should be null" );

        String ef = getEmptyFields( pl );
        if ( ! ef.isEmpty() )
            throw new WeatherBadRequestException( "Fields " + ef + "should be filled" );

    }

    private String getEmptyFields( Place pl ){
        String emptyFields = "";

        if ( pl.getName() == null )
            emptyFields += "name, ";

        if ( pl.getPeriod() == null )
            emptyFields += "period, ";

        if ( pl.getEnabled() == null )
            emptyFields += "enabled, ";

        if ( pl.getLatitude() == null )
            emptyFields += "latitude, ";

        if ( pl.getLongitude() == null )
            emptyFields += "longitude, ";

        return emptyFields;
    }

    /**
     * Validates and updates place in the database
     * @param pl place for update
     * @return id of a updated place
     */
    public Integer updatePlace( Place pl ){
        validateUpdate( pl );
        return pr.update( pl );
    }

    private void validateUpdate( Place pl ) {

        if ( pl.getId() == null )
            throw new WeatherBadRequestException( "Id field should not be null" );

        if ( pr.getPlace( pl.getId() ) == null )
            throw new WeatherBadRequestException( "Place with id:" + pl.getId() + " not found" );

        String ef = getEmptyFields( pl );
        if ( ! ef.isEmpty() )
            throw new WeatherBadRequestException( "Fields " + ef + "should be filled" );

    }

}

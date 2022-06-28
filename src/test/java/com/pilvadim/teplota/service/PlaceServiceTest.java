package com.pilvadim.teplota.service;

import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.repository.PlaceRepo;
import com.pilvadim.teplota.service.exception.WeatherBadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PlaceServiceTest {

    private final Place placeUpdate = new Place();
    {
        placeUpdate.setId(1);
        placeUpdate.setName("Name");
        placeUpdate.setEnabled(true);
        placeUpdate.setLatitude(4.0f);
        placeUpdate.setLongitude(4.0f);
        placeUpdate.setPeriod(10);
    }

    private final Place placeSave = new Place();
    {
        placeSave.setName("Name");
        placeSave.setEnabled(true);
        placeSave.setLatitude(4.0f);
        placeSave.setLongitude(4.0f);
        placeSave.setPeriod(10);
    }

    @Autowired
    PlaceService ps;

    @MockBean
    private PlaceRepo pr;

    @Test
    void addPlaceCorrect() {
        doNothing().when( pr ).save( any(Place.class) );
        ps.addPlace( placeSave );
    }

    @Test
    void addPlaceError() {
        doNothing().when( pr ).save( any(Place.class) );
        try {
            ps.addPlace(placeUpdate);
        } catch (WeatherBadRequestException e) {
            assertEquals(e.getMessage(), "Id field should be null");
        }
    }

    @Test
    void updatePlace() {
        doNothing().when( pr ).update( any(Place.class) );
        when( pr.getPlace( 1 ) ).thenReturn( placeUpdate );
        ps.updatePlace(placeUpdate);

    }

    @Test
    void updatePlaceError() {
        doNothing().when( pr ).update( any(Place.class) );
        when( pr.getPlace( 1 ) ).thenReturn( placeUpdate );
        try {
            ps.updatePlace(placeSave);
        } catch (WeatherBadRequestException e) {
            assertEquals(e.getMessage(), "Id field should not be null");
        }
    }

}
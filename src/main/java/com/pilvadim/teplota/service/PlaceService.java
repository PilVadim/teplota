package com.pilvadim.teplota.service;

import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.repository.PlaceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {

    final PlaceRepo pr;

    public PlaceService(PlaceRepo pr) {
        this.pr = pr;
    }

    public List<Place> getAllPlaces(){
        return pr.getPlaces();
    }

}

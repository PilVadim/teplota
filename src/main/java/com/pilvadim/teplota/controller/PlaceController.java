package com.pilvadim.teplota.controller;

import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="Place controller class")
@RestController
@RequestMapping("/api/v1")
public class PlaceController {

    final PlaceService ps;

    public PlaceController(PlaceService ps) {
        this.ps = ps;
    }

    @ApiOperation(value = "Get list of all available places", response = Place.class)
    @GetMapping("/places")
    public List<Place> getAll(){
        return ps.getAllPlaces();
    }

}

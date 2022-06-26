package com.pilvadim.teplota.controller;

import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="Place controller class")
@RestController
@RequestMapping("/api/v1")
public class PlaceController {

    final PlaceService ps;

    public PlaceController(PlaceService ps) {
        this.ps = ps;
    }

    @ApiOperation(value = "Gets a list of all available places", response = Place.class)
    @GetMapping("/places")
    public List<Place> getAll(){
        return ps.getAllPlaces();
    }

    @ApiOperation(value = "(Secured) Validates and Adds new place, returns id of new place", response = Integer.class)
    @PostMapping("/place")
    public Integer addPlace( @RequestBody Place pl ){
        return ps.addPlace(pl);
    }

    @ApiOperation(value = "(Secured) Validates and Updates existing place, returns id of existing place", response = Integer.class)
    @PutMapping("/place")
    public Integer updatePlace(@RequestBody Place pl ){
        return ps.updatePlace( pl );
    }

}

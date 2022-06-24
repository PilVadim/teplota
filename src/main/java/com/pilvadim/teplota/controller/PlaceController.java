package com.pilvadim.teplota.controller;

import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.service.PlaceService;
import com.pilvadim.teplota.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="Place controller class")
@RestController
@RequestMapping("/api/v1")
public class PlaceController {

    final PlaceService ps;
    final ScheduleService schs;

    public PlaceController(PlaceService ps,
                           ScheduleService schs) {
        this.ps = ps;
        this.schs = schs;
    }

    @ApiOperation(value = "Get list of all available places", response = Place.class)
    @GetMapping("/places")
    public List<Place> getAll(){
        return ps.getAllPlaces();
    }


    @GetMapping("/placesC")
    public void getAllC(){
        schs.startTask();
    }

    @GetMapping("/placesF")
    public void getAllF(){
        schs.stopProcesses();
    }

}

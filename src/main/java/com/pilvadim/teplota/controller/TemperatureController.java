package com.pilvadim.teplota.controller;

import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.service.TemperatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Api(value="Temperature controller class")
@RestController
@RequestMapping("/api/v1")
public class TemperatureController {

    final TemperatureService ts;

    public TemperatureController(TemperatureService ts) {
        this.ts = ts;
    }

    @ApiOperation(value = "Get list of all temperatures for period", response = Temperature.class)
    @GetMapping("/temperatures")
    public List<Temperature> getTemperaturesForPeriod(@RequestParam("start")
                                                      @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") LocalDateTime start,
                                                      @RequestParam("end")
                                                      @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") LocalDateTime end ){
        return ts.getTemperaturesForPeriod( start, end );

    }

}

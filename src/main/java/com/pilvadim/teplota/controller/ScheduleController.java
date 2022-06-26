package com.pilvadim.teplota.controller;

import com.pilvadim.teplota.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="Schedule controller class")
@RestController
@RequestMapping("/api/v1")
public class ScheduleController {

    final ScheduleService schs;

    public ScheduleController(ScheduleService schs) {
        this.schs = schs;
    }

    @ApiOperation(value = "(Secured) Starts collecting temperatures data, returns report", response = String.class)
    @PostMapping("/start")
    public String start(){
        try {
            return schs.startProcesses();
        } catch (Exception e){
            return "Not started " + e.getMessage();
        }
    }

    @ApiOperation(value = "(Secured) Stops collecting temperatures data", response = String.class)
    @PostMapping("/stop")
    public String stop(){
        try {
            schs.stopProcesses();
            return "Successfully stopped";
        } catch (Exception e){
            return "Not stopped " + e.getMessage();
        }
    }

}

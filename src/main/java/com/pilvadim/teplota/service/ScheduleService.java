package com.pilvadim.teplota.service;

import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.model.response.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ScheduleService {

    final UrlService urlService;
    final PlaceService placeService;

    public ScheduleService(UrlService urlService,
                           PlaceService placeService) {
        this.urlService = urlService;
        this.placeService = placeService;
    }

    List<ScheduledExecutorService> processes = Collections.synchronizedList(new ArrayList<>());

    private Runnable getProcess( Place place ){
        return () -> {
            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = urlService.generateUrl( place );
            WeatherResponse response = restTemplate.getForObject(resourceUrl, WeatherResponse.class);

            if ( response != null ) {
                System.out.println( response.getMain().getTemp() );//saveTemperature( place, response.getMain().getTemp() );
            }
        };
    };

    public void startTask(){

        stopProcesses();
        List<Place> places = placeService.getAllEnabledPlaces();

        for ( Place p : places ) {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate( getProcess( p ),
                    0,
                    p.getPeriod(),
                    TimeUnit.SECONDS);
            System.out.println(p.getName() + " started"); //TODO Add logging
            processes.add( executorService );
        }
    }

    public void stopProcesses(){

        for ( ScheduledExecutorService ses : processes ){
            ses.shutdownNow();
            System.out.println("I'm stopping"); //TODO Add logging
        }

        processes.clear();

    }

}

package com.pilvadim.teplota.service;

import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.model.response.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ScheduleService {

    final UrlService urlServ;
    final PlaceService placeServ;
    final TemperatureService temperatureServ;

    public ScheduleService(UrlService urlService,
                           PlaceService placeService,
                           TemperatureService temperatureServ) {
        this.urlServ = urlService;
        this.placeServ = placeService;
        this.temperatureServ = temperatureServ;
    }

    List<ScheduledExecutorService> processes = Collections.synchronizedList(new ArrayList<>());

    /**
     * Initiates stop for all current processes, receives current enabled places,
     * generates a new scheduled processes and starts them
     * @return report
     */
    public String startProcesses(){

        StringBuilder report = new StringBuilder();
        stopProcesses();

        List<Place> places = placeServ.getAllEnabledPlaces();

        for ( Place p : places ) {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate( getProcess( p ),
                    0,
                    p.getPeriod(),
                    TimeUnit.SECONDS);
            processes.add( executorService );
            report.append( p.getId() + ") " + p.getName() + " - is started\n" );
        }

        return report.toString();
    }

    /**
     * Generates a Runnable lambda which sends requests to the Weather service and saves result in the database
     * @param place location where temperature is requested
     * @return Runnable lambda for scheduler
     */
    private Runnable getProcess( Place place ){
        return () -> {
            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = urlServ.generateUrl( place );
            WeatherResponse response = restTemplate.getForObject(resourceUrl, WeatherResponse.class);

            if ( response != null ) {
                saveTemperature( place.getId(), response );
            }
        };
    }

    /**
     * Creates a Temperature object, Saves temperature in the database.
     * @param placeId Integer id of the current place
     * @param wr WeatherResponse short response from the weather service
     */
    private void saveTemperature( Integer placeId, WeatherResponse wr ) {

        Temperature t = new Temperature();
        t.setValue( wr.getMain().getTemp() );
        t.setPlaceId( placeId );
        t.setMoment( LocalDateTime.ofInstant( Instant.ofEpochSecond( wr.getDt() ),
                TimeZone.getDefault().toZoneId() ) );

        temperatureServ.save(t);

    }

    /**
     * Stops all scheduled processes and cleans list
     */
    public void stopProcesses(){

        for ( ScheduledExecutorService ses : processes ){
            ses.shutdownNow();
        }

        processes.clear();

    }

}

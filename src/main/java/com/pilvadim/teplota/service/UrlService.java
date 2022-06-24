package com.pilvadim.teplota.service;

import com.pilvadim.teplota.model.Place;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;


/**
 * Provides URL services
 */
@Service
public class UrlService {

    private static final String LATITUDE_TAG = "<lat>";
    private static final String LONGITUDE_TAG = "<lon>";
    private static final String APPID_TAG = "<appid>";

    @Value("${openweathermap.weatherSourceUrl}")
    String weatherSourceUrl;

    @Value("${openweathermap.appid}")
    String appId;

    public String generateUrl( Place place ){

        return weatherSourceUrl.replace( LATITUDE_TAG, formatCoordinates( place.getLatitude() ) )
                               .replace( LONGITUDE_TAG, formatCoordinates( place.getLongitude() ) )
                               .replace( APPID_TAG, appId );

    }

    /**
     * Format Float to 000.0000 String
     * @param input Float
     * @return formatted String
     */
    private String formatCoordinates( Float input ){
        NumberFormat formatter = new DecimalFormat("0.0000");
        return formatter.format(input);
    }

}

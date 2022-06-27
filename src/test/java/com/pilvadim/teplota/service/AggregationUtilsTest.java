package com.pilvadim.teplota.service;

import com.pilvadim.teplota.enums.AggregationType;
import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.model.temporal.FloatStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(AggregationUtils.class)
class AggregationUtilsTest {

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private final LocalDateTime input = LocalDateTime.parse("10.06.2022 10:10:00",df);
    private final LocalDateTime dayResult = LocalDateTime.parse("10.06.2022 00:00:00",df);
    private final LocalDateTime weekResult = LocalDateTime.parse("06.06.2022 00:00:00",df);
    private final LocalDateTime monthResult = LocalDateTime.parse("01.06.2022 00:00:00",df);

    @Autowired
    AggregationUtils au;

    @Test
    void addCelsiusToAggregatorTest() {

        Temperature t = new Temperature();
        t.setMoment( input );
        t.setCelsius(10.0f);

        Map< LocalDateTime, FloatStorage> daysAggregation = new LinkedHashMap<>();
        Map< LocalDateTime, FloatStorage> weeksAggregation = new LinkedHashMap<>();
        Map< LocalDateTime, FloatStorage> monthAggregation = new LinkedHashMap<>();

        au.addCelsiusToAggregator( t, AggregationType.DAY, daysAggregation );
        au.addCelsiusToAggregator( t, AggregationType.WEEK, weeksAggregation );
        au.addCelsiusToAggregator( t, AggregationType.MONTH, monthAggregation );

        assertTrue( daysAggregation.containsKey(dayResult) );
        assertTrue( weeksAggregation.containsKey(weekResult) );
        assertTrue( monthAggregation.containsKey(monthResult) );

        assertEquals( daysAggregation.get(dayResult).getAverage(), 10.0f );
        assertEquals( weeksAggregation.get(weekResult).getAverage(), 10.0f );
        assertEquals( monthAggregation.get(monthResult).getAverage(), 10.0f );

    }

    @Test
    void getKeyTest() {

        assertEquals( au.getKey(input, AggregationType.DAY), dayResult );
        assertEquals( au.getKey(input, AggregationType.WEEK), weekResult );
        assertEquals( au.getKey(input, AggregationType.MONTH), monthResult );
    }

}
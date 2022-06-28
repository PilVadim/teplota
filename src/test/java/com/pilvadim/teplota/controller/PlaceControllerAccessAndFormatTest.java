package com.pilvadim.teplota.controller;

import com.pilvadim.teplota.TeplotaApplication;
import com.pilvadim.teplota.model.Place;
import com.pilvadim.teplota.model.Temperature;
import com.pilvadim.teplota.repository.PlaceRepo;
import com.pilvadim.teplota.service.PlaceService;
import com.pilvadim.teplota.service.TemperatureService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes= TeplotaApplication.class)
@TestPropertySource("classpath:applicationTest.properties")
class PlaceControllerAccessAndFormatTest {

    private final String PLACES = "/api/v1/places";
    private final String PLACE = "/api/v1/place";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService ps;
    @MockBean
    private TemperatureService ts;

    MultiValueMap<String,String> paramsPeriod = new LinkedMultiValueMap<>();
    {
        paramsPeriod.add("start", "01.06.2022 00:00:00");
        paramsPeriod.add("end", "01.07.2022 00:00:00");
    }

    String placeBodyUpdate = "{\"id\":2,\"name\": \"MoscowT\",\"latitude\": 55.7522," +
            "\"longitude\": 37.6156,\"period\": 10,\"enabled\": true}";

    String placeBodyAdd = "{\"name\": \"MoscowT\",\"latitude\": 55.7522," +
            "\"longitude\": 37.6156,\"period\": 10,\"enabled\": true}";

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void getAllTestUser() throws Exception {
        when( ts.getTemperaturesForPeriod(any(),any()) ).thenReturn(null);
        mockMvc.perform( MockMvcRequestBuilders.get(PLACES).params( paramsPeriod ))
                .andExpect( status().isOk());
    }

    @Test
    void getAllTestNoUser() throws Exception {
        when( ts.getTemperaturesForPeriod(any(),any()) ).thenReturn(null);
        mockMvc.perform( MockMvcRequestBuilders.get(PLACES).params( paramsPeriod ))
                .andExpect( status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void addPlaceTestUser() throws Exception  {
        when( ps.addPlace( any(Place.class) ) ).thenReturn(1);
        mockMvc.perform( MockMvcRequestBuilders.post(PLACE)
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyAdd ))
                        .andExpect( status().isOk());
    }

    @Test
    void addPlaceTestNoUser() throws Exception  {
        when( ps.addPlace( any(Place.class) ) ).thenReturn(1);
        mockMvc.perform( MockMvcRequestBuilders.post(PLACE)
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyAdd ))
                        .andExpect( status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void updatePlaceTestUser() throws Exception  {
        when( ps.updatePlace( any(Place.class) ) ).thenReturn(1);
        mockMvc.perform( MockMvcRequestBuilders.put(PLACE)
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyUpdate ))
                        .andExpect( status().isOk());
    }

    @Test
    void updatePlaceTestNoUser() throws Exception  {
        when( ps.updatePlace( any(Place.class) ) ).thenReturn(1);
        mockMvc.perform( MockMvcRequestBuilders.put(PLACE)
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyUpdate ))
                        .andExpect( status().isUnauthorized());
    }
}
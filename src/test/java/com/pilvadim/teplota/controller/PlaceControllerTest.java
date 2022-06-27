package com.pilvadim.teplota.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    MultiValueMap<String,String> paramsPeriod = new LinkedMultiValueMap<>();
    {
        paramsPeriod.add("start", "01.06.2022 00:00:00");
        paramsPeriod.add("end", "01.07.2022 00:00:00");
    }

    String placeBodyUpdate = "{\"id\":6,\"name\": \"Moscow\",\"latitude\": 55.7522," +
            "\"longitude\": 37.6156,\"period\": 10,\"enabled\": true}";

    String placeBodyAdd = "{\"name\": \"Moscow\",\"latitude\": 55.7522," +
            "\"longitude\": 37.6156,\"period\": 10,\"enabled\": true}";

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void getAllTestUser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get("/api/v1/places").params( paramsPeriod ))
                .andExpect( status().isOk());
    }

    @Test
    void getAllTestNoUser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get("/api/v1/places").params( paramsPeriod ))
                .andExpect( status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void addPlaceTestUser() throws Exception  {
        mockMvc.perform( MockMvcRequestBuilders.post("/api/v1/place")
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyAdd ))
                        .andExpect( status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void addPlaceTestUserBadRequest() throws Exception  {
        mockMvc.perform( MockMvcRequestBuilders.post("/api/v1/place")
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyUpdate ))
                .andExpect( status().isBadRequest());
    }

    @Test
    void addPlaceTestNoUser() throws Exception  {
        mockMvc.perform( MockMvcRequestBuilders.post("/api/v1/place")
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyAdd ))
                        .andExpect( status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void updatePlaceTestUser() throws Exception  {
        mockMvc.perform( MockMvcRequestBuilders.put("/api/v1/place")
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyUpdate ))
                        .andExpect( status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    void updatePlaceTestUserBadRequest() throws Exception  {
        mockMvc.perform( MockMvcRequestBuilders.put("/api/v1/place")
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyAdd ))
                .andExpect( status().isBadRequest());
    }

    @Test
    void updatePlaceTestNoUser() throws Exception  {
        mockMvc.perform( MockMvcRequestBuilders.put("/api/v1/place")
                        .contentType( APPLICATION_JSON )
                        .content( placeBodyUpdate ))
                        .andExpect( status().isUnauthorized());
    }
}
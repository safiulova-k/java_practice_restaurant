package com.safiulova.restaurant_rating.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safiulova.restaurant_rating.dto.restaurant.RestaurantRequestDto;
import com.safiulova.restaurant_rating.dto.restaurant.RestaurantResponseDto;
import com.safiulova.restaurant_rating.entity.CuisineType;
import com.safiulova.restaurant_rating.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean RestaurantService restaurantService;

    @Test
    void getAll_returns200() throws Exception {
        when(restaurantService.findAll()).thenReturn(List.of(
                new RestaurantResponseDto(1L, "restik1", null, CuisineType.ITALIAN,
                        new BigDecimal("650.00"), new BigDecimal("0.00"))
        ));

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void post_create_returns201() throws Exception {
        RestaurantRequestDto dto = new RestaurantRequestDto(
                "BaklanPizza", null, CuisineType.ITALIAN, new BigDecimal("400.00"));

        when(restaurantService.create(any(RestaurantRequestDto.class)))
                .thenReturn(new RestaurantResponseDto(
                        10L, "BaklanPizza", null, CuisineType.ITALIAN,
                        new BigDecimal("400.00"), new BigDecimal("0.00")));

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }
    @Test
    void put_update_returns200() throws Exception {
        RestaurantRequestDto dto = new RestaurantRequestDto(
                "Updated", null, CuisineType.ITALIAN, new BigDecimal("400.00"));

        when(restaurantService.update(eq(1L), any(RestaurantRequestDto.class)))
                .thenReturn(Optional.of(
                        new RestaurantResponseDto(
                                1L, "Updated", null,
                                CuisineType.ITALIAN,
                                new BigDecimal("400.00"),
                                new BigDecimal("0.00")
                        )
                ));

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void delete_returns204() throws Exception {
        when(restaurantService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isNoContent());
    }

}

package com.safiulova.restaurant_rating.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safiulova.restaurant_rating.dto.visitor.VisitorRequestDto;
import com.safiulova.restaurant_rating.dto.visitor.VisitorResponseDto;
import com.safiulova.restaurant_rating.entity.Gender;
import com.safiulova.restaurant_rating.service.VisitorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitorController.class)
class VisitorControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean VisitorService visitorService;

    @Test
    void getAll_returns200() throws Exception {
        when(visitorService.findAll()).thenReturn(List.of(
                new VisitorResponseDto(1L, "Antonina", 20, Gender.FEMALE),
                new VisitorResponseDto(2L, null, 30, Gender.MALE)
        ));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void post_create_returns201() throws Exception {
        VisitorRequestDto dto = new VisitorRequestDto("Luda", 85, Gender.FEMALE);
        when(visitorService.create(any(VisitorRequestDto.class)))
                .thenReturn(new VisitorResponseDto(10L, "Luda", 85, Gender.FEMALE));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }
    @Test
    void put_update_returns200() throws Exception {
        VisitorRequestDto dto = new VisitorRequestDto("Galina", 45, Gender.FEMALE);

        when(visitorService.update(eq(1L), any(VisitorRequestDto.class)))
                .thenReturn(Optional.of(
                        new VisitorResponseDto(1L, "Galina", 45, Gender.FEMALE)
                ));

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galina"));
    }

    @Test
    void delete_returns204() throws Exception {
        when(visitorService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}

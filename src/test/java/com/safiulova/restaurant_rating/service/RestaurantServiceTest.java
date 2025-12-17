package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.dto.restaurant.RestaurantRequestDto;
import com.safiulova.restaurant_rating.dto.restaurant.RestaurantResponseDto;
import com.safiulova.restaurant_rating.entity.CuisineType;
import com.safiulova.restaurant_rating.entity.Restaurant;
import com.safiulova.restaurant_rating.mapper.RestaurantMapper;
import com.safiulova.restaurant_rating.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void findAll_returnsListOfDtos() {
        Restaurant r1 = new Restaurant();
        r1.setId(1L);
        Restaurant r2 = new Restaurant();
        r2.setId(2L);

        when(restaurantRepository.findAll()).thenReturn(List.of(r1, r2));
        when(restaurantMapper.toDto(r1)).thenReturn(new RestaurantResponseDto(
                1L, "Restik1", null, CuisineType.ITALIAN, new BigDecimal("350"), new BigDecimal("0.00")));
        when(restaurantMapper.toDto(r2)).thenReturn(new RestaurantResponseDto(
                2L, "Restik2", "desc", CuisineType.RUSSIAN, new BigDecimal("1500"), new BigDecimal("4.50")));

        List<RestaurantResponseDto> result = restaurantService.findAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals(2L, result.get(1).id());
    }

    @Test
    void create_setsRatingZero_saves_andReturnsDto() {
        RestaurantRequestDto dto = new RestaurantRequestDto(
                "BaklanPizza", null, CuisineType.ITALIAN, new BigDecimal("400.00"));

        Restaurant entity = new Restaurant();

        Restaurant saved = new Restaurant();
        saved.setId(1L);

        when(restaurantMapper.toEntity(dto)).thenReturn(entity);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(saved);
        when(restaurantMapper.toDto(saved)).thenReturn(new RestaurantResponseDto(
                1L, "BaklanPizza", null, CuisineType.ITALIAN, new BigDecimal("400.00"), BigDecimal.ZERO));

        RestaurantResponseDto result = restaurantService.create(dto);

        assertEquals(1L, result.id());

        // сервис ставит id = null и rating = 0
        assertNull(entity.getId());
        assertEquals(BigDecimal.ZERO, entity.getRating());
    }
}

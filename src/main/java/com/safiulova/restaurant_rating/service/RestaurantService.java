package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.dto.restaurant.RestaurantRequestDto;
import com.safiulova.restaurant_rating.dto.restaurant.RestaurantResponseDto;
import com.safiulova.restaurant_rating.entity.Restaurant;
import com.safiulova.restaurant_rating.mapper.RestaurantMapper;
import com.safiulova.restaurant_rating.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public List<RestaurantResponseDto> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDto)
                .toList();
    }

    public Optional<RestaurantResponseDto> findById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toDto);
    }

    public Optional<Restaurant> findEntityById(Long id) {
        return restaurantRepository.findById(id);
    }

    public RestaurantResponseDto create(RestaurantRequestDto dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurant.setId(null);
        restaurant.setRating(BigDecimal.ZERO);
        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(saved);
    }

    public Optional<RestaurantResponseDto> update(Long id, RestaurantRequestDto dto) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
        if (restaurantOpt.isEmpty()) {
            return Optional.empty();
        }

        Restaurant restaurant = restaurantOpt.get();
        restaurantMapper.updateEntityFromDto(dto, restaurant);

        Restaurant saved = restaurantRepository.save(restaurant);
        return Optional.of(restaurantMapper.toDto(saved));
    }

    public boolean delete(Long id) {
        if (!restaurantRepository.existsById(id)) {
            return false;
        }
        restaurantRepository.deleteById(id);
        return true;
    }

    public void updateRating(Long restaurantId, BigDecimal newRating) {
        restaurantRepository.findById(restaurantId)
                .ifPresent(r -> {
                    r.setRating(newRating);
                    restaurantRepository.save(r);
                });
    }
}

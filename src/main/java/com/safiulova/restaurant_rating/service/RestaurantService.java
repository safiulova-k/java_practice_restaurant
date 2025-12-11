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
        return findEntityById(id).map(restaurantMapper::toDto);
    }

    public Optional<Restaurant> findEntityById(Long id) {
        return restaurantRepository.findAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public RestaurantResponseDto create(RestaurantRequestDto dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurant.setId(generateNewId());
        restaurant.setRating(BigDecimal.ZERO);
        restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    public Optional<RestaurantResponseDto> update(Long id, RestaurantRequestDto dto) {
        Optional<Restaurant> restaurantOpt = findEntityById(id);
        if (restaurantOpt.isEmpty()) {
            return Optional.empty();
        }

        Restaurant restaurant = restaurantOpt.get();
        restaurantMapper.updateEntityFromDto(dto, restaurant);

        return Optional.of(restaurantMapper.toDto(restaurant));
    }

    public boolean delete(Long id) {
        Optional<Restaurant> restaurantOpt = findEntityById(id);
        if (restaurantOpt.isEmpty()) {
            return false;
        }

        restaurantRepository.remove(restaurantOpt.get());
        return true;
    }

    public void updateRating(Long restaurantId, BigDecimal newRating) {
        findEntityById(restaurantId)
                .ifPresent(r -> r.setRating(newRating));
    }

    private long generateNewId() {
        return restaurantRepository.findAll().stream()
                .mapToLong(Restaurant::getId)
                .max()
                .orElse(0L) + 1L;
    }
}

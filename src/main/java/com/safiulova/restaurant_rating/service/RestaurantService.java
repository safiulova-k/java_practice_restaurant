package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.entity.Restaurant;
import com.safiulova.restaurant_rating.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public void save(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public void remove(Restaurant restaurant) {
        restaurantRepository.remove(restaurant);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }
}
package com.safiulova.restaurant_rating.mapper;

import com.safiulova.restaurant_rating.dto.restaurant.RestaurantRequestDto;
import com.safiulova.restaurant_rating.dto.restaurant.RestaurantResponseDto;
import com.safiulova.restaurant_rating.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    Restaurant toEntity(RestaurantRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    void updateEntityFromDto(RestaurantRequestDto dto, @MappingTarget Restaurant restaurant);

    RestaurantResponseDto toDto(Restaurant restaurant);
}

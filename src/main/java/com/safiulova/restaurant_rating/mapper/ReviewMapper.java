package com.safiulova.restaurant_rating.mapper;

import com.safiulova.restaurant_rating.dto.review.ReviewRequestDto;
import com.safiulova.restaurant_rating.dto.review.ReviewResponseDto;
import com.safiulova.restaurant_rating.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "visitor", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Review toEntity(ReviewRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "visitor", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    void updateEntityFromDto(ReviewRequestDto dto, @MappingTarget Review review);

    @Mapping(target = "visitorId", source = "visitor.id")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    ReviewResponseDto toDto(Review review);
}

package com.safiulova.restaurant_rating.mapper;

import com.safiulova.restaurant_rating.dto.review.ReviewRequestDto;
import com.safiulova.restaurant_rating.dto.review.ReviewResponseDto;
import com.safiulova.restaurant_rating.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toEntity(ReviewRequestDto dto);

    void updateEntityFromDto(ReviewRequestDto dto, @MappingTarget Review review);

    ReviewResponseDto toDto(Review review);
}

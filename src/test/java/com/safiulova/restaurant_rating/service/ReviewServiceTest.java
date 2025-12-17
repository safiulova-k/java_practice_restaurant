package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.dto.review.ReviewRequestDto;
import com.safiulova.restaurant_rating.dto.review.ReviewResponseDto;
import com.safiulova.restaurant_rating.entity.Restaurant;
import com.safiulova.restaurant_rating.entity.Review;
import com.safiulova.restaurant_rating.entity.Visitor;
import com.safiulova.restaurant_rating.mapper.ReviewMapper;
import com.safiulova.restaurant_rating.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private ReviewMapper reviewMapper;
    @Mock private RestaurantService restaurantService;
    @Mock private VisitorService visitorService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void create_setsLinks_saves_recalculatesRating() {
        ReviewRequestDto dto = new ReviewRequestDto(1L, 2L, 4, "text");

        Review entity = new Review();
        when(reviewMapper.toEntity(dto)).thenReturn(entity);

        Visitor visitor = new Visitor(); visitor.setId(1L);
        Restaurant restaurant = new Restaurant(); restaurant.setId(2L);

        when(visitorService.findEntityById(1L)).thenReturn(Optional.of(visitor));
        when(restaurantService.findEntityById(2L)).thenReturn(Optional.of(restaurant));

        Review saved = new Review();
        saved.setVisitor(visitor);
        saved.setRestaurant(restaurant);
        when(reviewRepository.save(any(Review.class))).thenReturn(saved);

        // среднее (5+3)/2 = 4.00
        Review r1 = new Review(); r1.setScore(5);
        Review r2 = new Review(); r2.setScore(3);
        when(reviewRepository.findByRestaurantId(2L)).thenReturn(List.of(r1, r2));

        when(reviewMapper.toDto(saved)).thenReturn(new ReviewResponseDto(1L, 2L, 4, "text"));

        ReviewResponseDto result = reviewService.create(dto);

        assertEquals(1L, result.visitorId());
        assertEquals(2L, result.restaurantId());

        verify(restaurantService).updateRating(eq(2L), eq(new BigDecimal("4.00")));
    }
}

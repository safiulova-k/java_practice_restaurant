package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.dto.review.ReviewRequestDto;
import com.safiulova.restaurant_rating.dto.review.ReviewResponseDto;
import com.safiulova.restaurant_rating.entity.Review;
import com.safiulova.restaurant_rating.mapper.ReviewMapper;
import com.safiulova.restaurant_rating.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final RestaurantService restaurantService;

    public List<ReviewResponseDto> findAll() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    public Optional<ReviewResponseDto> findById(Long visitorId, Long restaurantId) {
        return reviewRepository.findById(visitorId, restaurantId)
                .map(reviewMapper::toDto);
    }

    public ReviewResponseDto create(ReviewRequestDto dto) {
        Review review = reviewMapper.toEntity(dto);
        reviewRepository.save(review);
        recalculateRestaurantRating(review.getRestaurantId());
        return reviewMapper.toDto(review);
    }

    public Optional<ReviewResponseDto> update(Long visitorId,
                                              Long restaurantId,
                                              ReviewRequestDto dto) {
        Optional<Review> reviewOpt = reviewRepository.findById(visitorId, restaurantId);
        if (reviewOpt.isEmpty()) {
            return Optional.empty();
        }

        Review review = reviewOpt.get();
        reviewMapper.updateEntityFromDto(dto, review);
        reviewRepository.save(review);
        recalculateRestaurantRating(review.getRestaurantId());
        return Optional.of(reviewMapper.toDto(review));
    }

    public boolean delete(Long visitorId, Long restaurantId) {
        Optional<Review> reviewOpt = reviewRepository.findById(visitorId, restaurantId);
        if (reviewOpt.isEmpty()) {
            return false;
        }

        Review review = reviewOpt.get();
        reviewRepository.remove(review);
        recalculateRestaurantRating(review.getRestaurantId());
        return true;
    }

    private void recalculateRestaurantRating(Long restaurantId) {
        List<Review> restaurantReviews = reviewRepository.findAll().stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .toList();

        BigDecimal newRating;
        if (restaurantReviews.isEmpty()) {
            newRating = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        } else {
            double avg = restaurantReviews.stream()
                    .mapToInt(Review::getScore)
                    .average()
                    .orElse(0.0);
            newRating = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);
        }

        restaurantService.updateRating(restaurantId, newRating);
    }
}

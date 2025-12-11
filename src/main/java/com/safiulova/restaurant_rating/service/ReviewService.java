package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.dto.review.ReviewRequestDto;
import com.safiulova.restaurant_rating.dto.review.ReviewResponseDto;
import com.safiulova.restaurant_rating.entity.Restaurant;
import com.safiulova.restaurant_rating.entity.Review;
import com.safiulova.restaurant_rating.entity.Visitor;
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
    private final VisitorService visitorService;

    public List<ReviewResponseDto> findAll() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    // Поиск отзыва по связке (visitorId, restaurantId), используется метод репозитория findByVisitorIdAndRestaurantId(...)
    public Optional<ReviewResponseDto> findById(Long visitorId, Long restaurantId) {
        return reviewRepository.findByVisitorIdAndRestaurantId(visitorId, restaurantId)
                .map(reviewMapper::toDto);
    }

    public ReviewResponseDto create(ReviewRequestDto dto) {
        Review review = reviewMapper.toEntity(dto);

        Long visitorId = dto.visitorId();
        Long restaurantId = dto.restaurantId();

        Visitor visitor = visitorService.findEntityById(visitorId)
                .orElseThrow(() -> new RuntimeException("Посетитель не найден: " + visitorId));

        Restaurant restaurant = restaurantService.findEntityById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Ресторан не найден: " + restaurantId));

        review.setId(null);
        review.setVisitor(visitor);
        review.setRestaurant(restaurant);

        Review saved = reviewRepository.save(review);

        recalculateRestaurantRating(restaurant.getId());

        return reviewMapper.toDto(saved);
    }

    public Optional<ReviewResponseDto> update(Long visitorId,
                                              Long restaurantId,
                                              ReviewRequestDto dto) {

        Optional<Review> reviewOpt =
                reviewRepository.findByVisitorIdAndRestaurantId(visitorId, restaurantId);

        if (reviewOpt.isEmpty()) {
            return Optional.empty();
        }

        Review review = reviewOpt.get();
        reviewMapper.updateEntityFromDto(dto, review);

        Review saved = reviewRepository.save(review);

        recalculateRestaurantRating(restaurantId);

        return Optional.of(reviewMapper.toDto(saved));
    }

    public boolean delete(Long visitorId, Long restaurantId) {
        Optional<Review> reviewOpt =
                reviewRepository.findByVisitorIdAndRestaurantId(visitorId, restaurantId);

        if (reviewOpt.isEmpty()) {
            return false;
        }

        Review review = reviewOpt.get();
        Long restaurantIdForRating = review.getRestaurant().getId();

        reviewRepository.delete(review);

        recalculateRestaurantRating(restaurantIdForRating);

        return true;
    }

    private void recalculateRestaurantRating(Long restaurantId) {
        List<Review> restaurantReviews = reviewRepository.findByRestaurantId(restaurantId);

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

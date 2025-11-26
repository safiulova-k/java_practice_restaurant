package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.entity.Review;
import com.safiulova.restaurant_rating.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantService restaurantService;

    public void save(Review review) {
        reviewRepository.save(review);
        recalculateRestaurantRating(review.getRestaurantId());
    }

    public void remove(Review review) {
        reviewRepository.remove(review);
        recalculateRestaurantRating(review.getRestaurantId());
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    // пересчитываем среднюю оценку ресторана, используется при добавлении и удалении оценки
    private void recalculateRestaurantRating(Long restaurantId) {
        List<Review> allReviews = reviewRepository.findAll();

        // отбираем оценки по нужному ресторану
        List<Review> restaurantReviews = allReviews.stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .toList();

        BigDecimal newRating;

        if (restaurantReviews.isEmpty()) {
            // если отзывов еще не было, то рейтинг будет 0.00
            newRating = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        } else {
            double avg = restaurantReviews.stream()
                    .mapToInt(Review::getScore)
                    .average()
                    .orElse(0.0);

            newRating = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);
        }

        restaurantService.findById(restaurantId)
                .ifPresent(restaurant -> restaurant.setRating(newRating));
    }
}
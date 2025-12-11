package com.safiulova.restaurant_rating.repository;

import com.safiulova.restaurant_rating.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // найти все отзывы конкретного посетителя
    List<Review> findByVisitorId(Long visitorId);
    // найти все отзывы конкретного ресторана
    List<Review> findByRestaurantId(Long restaurantId);
    // один отзыв по посетителю и ресторану
    Optional<Review> findByVisitorIdAndRestaurantId(Long visitorId, Long restaurantId);
}

package com.safiulova.restaurant_rating;

import com.safiulova.restaurant_rating.entity.*;
import com.safiulova.restaurant_rating.service.RestaurantService;
import com.safiulova.restaurant_rating.service.ReviewService;
import com.safiulova.restaurant_rating.service.VisitorService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TestDataInitializer {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @PostConstruct
    public void init() {
        System.out.println("Добавление тестовых данных через @PostConstruct");

        Visitor v1 = new Visitor(1L, "Анжела", 49, Gender.FEMALE);
        Visitor v2 = new Visitor(2L, null, 30, Gender.OTHER); // для анонимного отзыва
        Visitor v3 = new Visitor(3L, "Иннокентий", 27, Gender.MALE);

        visitorService.save(v1);
        visitorService.save(v2);
        visitorService.save(v3);

        Restaurant r1 = new Restaurant(
                1L,
                "БакланПицца",
                "Изысканная итальянская кухня прямиком из Тольятти",
                CuisineType.ITALIAN,
                new BigDecimal("270.00"),
                BigDecimal.ZERO
        );

        Restaurant r2 = new Restaurant(
                2L,
                "Wok and Roll",
                null,
                CuisineType.CHINESE,
                new BigDecimal("1600.00"),
                BigDecimal.ZERO
        );

        restaurantService.save(r1);
        restaurantService.save(r2);


        Review rev1 = new Review(1L, 1L, 5, "Вкусно, как пицца из столовки!");
        Review rev2 = new Review(2L, 1L, 1, "В подарок положили таракана");
        Review rev3 = new Review(3L, 2L, 4, "Норм");
        Review rev4 = new Review(2L, 2L, 3, null);
        // при сохранении сразу пересчитается рейтинг ресторанов, поэтому в выводе он уже будет не ноль
        reviewService.save(rev1);
        reviewService.save(rev2);
        reviewService.save(rev3);
        reviewService.save(rev4);
    }
}
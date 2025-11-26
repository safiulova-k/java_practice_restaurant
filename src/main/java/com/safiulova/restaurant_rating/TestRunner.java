package com.safiulova.restaurant_rating;

import com.safiulova.restaurant_rating.service.RestaurantService;
import com.safiulova.restaurant_rating.service.ReviewService;
import com.safiulova.restaurant_rating.service.VisitorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestRunner implements CommandLineRunner {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @Override
    public void run(String... args) {
        System.out.println("\nТестирование через CommandLineRunner");

        System.out.println("Все посетители:");
        visitorService.findAll().forEach(System.out::println);

        System.out.println("\nВсе рестораны:");
        restaurantService.findAll().forEach(System.out::println);

        System.out.println("\nВсе оценки:");
        reviewService.findAll().forEach(System.out::println);
    }
}
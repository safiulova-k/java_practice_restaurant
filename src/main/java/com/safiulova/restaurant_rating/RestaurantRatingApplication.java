package com.safiulova.restaurant_rating;

import com.safiulova.restaurant_rating.service.VisitorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestaurantRatingApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(RestaurantRatingApplication.class, args);

		System.out.println("\nТестирование через context.getBean()");
		VisitorService visitorService = context.getBean(VisitorService.class);
		System.out.println(visitorService.findAll());
	}
}

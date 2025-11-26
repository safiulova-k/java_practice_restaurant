package com.safiulova.restaurant_rating.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @Nullable
    private String description;

    @NotNull
    private CuisineType cuisineType;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal averageCheck;

    @NotNull
    private BigDecimal rating;
}
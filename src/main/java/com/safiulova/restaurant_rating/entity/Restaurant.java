package com.safiulova.restaurant_rating.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Nullable
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal averageCheck;

    @NotNull
    private BigDecimal rating;
}

package com.safiulova.restaurant_rating.dto.restaurant;

import com.safiulova.restaurant_rating.entity.CuisineType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Schema(description = "Запрос на создание/обновление ресторана")
public record RestaurantRequestDto(

        @Schema(
                description = "Название ресторана",
                example = "БакланПицца"
        )
        @NotNull
        String name,

        @Schema(
                description = "Описание",
                example = "Вкусно, как в столовой",
                nullable = true
        )
        @Nullable
        String description,

        @Schema(
                description = "Тип кухни",
                example = "ITALIAN"
        )
        @NotNull
        CuisineType cuisineType,

        @Schema(
                description = "Средний чек на одного человека",
                example = "300.00"
        )
        @NotNull
        @DecimalMin("0.0")
        BigDecimal averageCheck
) {}

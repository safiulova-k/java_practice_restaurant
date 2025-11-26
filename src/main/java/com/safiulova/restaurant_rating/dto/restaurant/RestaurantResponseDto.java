package com.safiulova.restaurant_rating.dto.restaurant;

import com.safiulova.restaurant_rating.entity.CuisineType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Schema(description = "Информация о ресторане в ответе API")
public record RestaurantResponseDto(

        @Schema(
                description = "Идентификатор ресторана",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Название ресторана",
                example = "БакланПицца"
        )
        String name,

        @Schema(
                description = "Описание ресторана",
                example = "Вкусно, как в столовой",
                nullable = true
        )
        @Nullable
        String description,

        @Schema(
                description = "Тип кухни",
                example = "ITALIAN"
        )
        CuisineType cuisineType,

        @Schema(
                description = "Средний чек на одного человека",
                example = "300.00"
        )
        BigDecimal averageCheck,

        @Schema(
                description = "Средняя оценка пользователей (рассчитывается по отзывам)",
                example = "4.50"
        )
        BigDecimal rating
) {}

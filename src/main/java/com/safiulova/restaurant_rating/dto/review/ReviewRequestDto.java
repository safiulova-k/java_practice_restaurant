package com.safiulova.restaurant_rating.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

@Schema(description = "Запрос на создание/обновление отзыва")
public record ReviewRequestDto(

        @Schema(
                description = "ID посетителя, оставляющего отзыв",
                example = "1"
        )
        @NotNull
        Long visitorId,

        @Schema(
                description = "ID ресторана, на который оставляют отзыв",
                example = "2"
        )
        @NotNull
        Long restaurantId,

        @Schema(
                description = "Оценка ресторана от 1 до 5",
                example = "4"
        )
        @NotNull
        @Min(1)
        @Max(5)
        Integer score,

        @Schema(
                description = "Текст отзыва. Может быть пустым",
                example = "Вкусно, но можно было и побыстрее",
                nullable = true
        )
        @Nullable
        String text
) {}

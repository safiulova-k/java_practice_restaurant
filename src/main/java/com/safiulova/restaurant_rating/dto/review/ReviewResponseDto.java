package com.safiulova.restaurant_rating.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

@Schema(description = "Информация об отзыве в ответе API")
public record ReviewResponseDto(

        @Schema(
                description = "ID посетителя, оставившего отзыв",
                example = "1"
        )
        Long visitorId,

        @Schema(
                description = "ID ресторана, на который оставлен отзыв",
                example = "2"
        )
        Long restaurantId,

        @Schema(
                description = "Оценка ресторана от 1 до 5",
                example = "4"
        )
        Integer score,

        @Schema(
                description = "Текст отзыва. Может отсутствовать",
                example = "Вкусно, но можно было и побыстрее",
                nullable = true
        )
        @Nullable
        String text
) {}

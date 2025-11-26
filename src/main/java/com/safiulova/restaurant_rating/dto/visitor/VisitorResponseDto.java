package com.safiulova.restaurant_rating.dto.visitor;

import com.safiulova.restaurant_rating.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

@Schema(description = "Информация о посетителе в ответе API")
public record VisitorResponseDto(

        @Schema(description = "Идентификатор посетителя", example = "1")
        Long id,

        @Schema(description = "Имя посетителя или null, если отзыв анонимный",
                example = "Серафима",
                nullable = true)
        @Nullable String name,

        @Schema(description = "Возраст посетителя", example = "55")
        Integer age,

        @Schema(description = "Пол посетителя", example = "FEMALE")
        Gender gender
) {}

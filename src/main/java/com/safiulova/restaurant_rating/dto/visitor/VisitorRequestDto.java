package com.safiulova.restaurant_rating.dto.visitor;

import com.safiulova.restaurant_rating.entity.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

@Schema(description = "Запрос на создание/обновление посетителя")
public record VisitorRequestDto(

        @Schema(description = "Имя посетителя. Может отсутствовать для анонимного отзыва",
                example = "Серафима",
                nullable = true)
        @Nullable String name,

        @Schema(description = "Возраст посетителя", example = "55")
        @NotNull @Min(0) Integer age,

        @Schema(description = "Пол посетителя", example = "FEMALE")
        @NotNull Gender gender
) {}

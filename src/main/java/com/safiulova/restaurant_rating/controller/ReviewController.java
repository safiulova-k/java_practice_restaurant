package com.safiulova.restaurant_rating.controller;

import com.safiulova.restaurant_rating.dto.review.ReviewRequestDto;
import com.safiulova.restaurant_rating.dto.review.ReviewResponseDto;
import com.safiulova.restaurant_rating.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Операции с отзывами о ресторанах")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Получить список всех отзывов",
            description = "Возвращает все отзывы с оценками и текстом(если он есть).")
    public List<ReviewResponseDto> getAll() {
        return reviewService.findAll();
    }

    @GetMapping("/{visitorId}/{restaurantId}")
    @Operation(summary = "Получить отзыв по ID посетителя и ресторана",
            description = "Возвращает отзыв конкретного посетителя для конкретного ресторана.")
    public ResponseEntity<ReviewResponseDto> getById(
            @Parameter(description = "ID посетителя", example = "1")
            @PathVariable Long visitorId,
            @Parameter(description = "ID ресторана", example = "1")
            @PathVariable Long restaurantId) {
        return reviewService.findById(visitorId, restaurantId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать новый отзыв",
            description = "Создаёт новый отзыв. После сохранения автоматически пересчитывается рейтинг ресторана.")
    public ResponseEntity<ReviewResponseDto> create(
            @RequestBody @Valid ReviewRequestDto dto) {
        ReviewResponseDto created = reviewService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{visitorId}/{restaurantId}")
    @Operation(summary = "Обновить отзыв",
            description = "Обновляет оценку и текст отзыва. После изменения пересчитывается рейтинг ресторана.")
    public ResponseEntity<ReviewResponseDto> update(
            @Parameter(description = "ID посетителя", example = "1")
            @PathVariable Long visitorId,
            @Parameter(description = "ID ресторана", example = "1")
            @PathVariable Long restaurantId,
            @RequestBody @Valid ReviewRequestDto dto) {
        return reviewService.update(visitorId, restaurantId, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{visitorId}/{restaurantId}")
    @Operation(summary = "Удалить отзыв",
            description = "Удаляет отзыв и пересчитывает рейтинг ресторана.")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID посетителя", example = "1")
            @PathVariable Long visitorId,
            @Parameter(description = "ID ресторана", example = "1")
            @PathVariable Long restaurantId) {
        boolean deleted = reviewService.delete(visitorId, restaurantId);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

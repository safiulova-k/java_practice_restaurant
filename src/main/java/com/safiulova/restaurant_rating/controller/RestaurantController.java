package com.safiulova.restaurant_rating.controller;

import com.safiulova.restaurant_rating.dto.restaurant.RestaurantRequestDto;
import com.safiulova.restaurant_rating.dto.restaurant.RestaurantResponseDto;
import com.safiulova.restaurant_rating.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurants", description = "Операции с ресторанами")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "Получить список всех ресторанов",
            description = "Возвращает список ресторанов с их средней оценкой и средним чеком.")
    public List<RestaurantResponseDto> getAll() {
        return restaurantService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ресторан по ID",
            description = "Возвращает ресторан по его ID.")
    public ResponseEntity<RestaurantResponseDto> getById(
            @Parameter(description = "Идентификатор ресторана", example = "1")
            @PathVariable Long id) {
        return restaurantService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать новый ресторан",
            description = "Создаёт новый ресторан. Рейтинг устанавливается автоматически (0.00).")
    public ResponseEntity<RestaurantResponseDto> create(
            @RequestBody @Valid RestaurantRequestDto dto) {
        RestaurantResponseDto created = restaurantService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные ресторана",
            description = "Обновляет информацию о ресторане, не изменяя его рейтинг.")
    public ResponseEntity<RestaurantResponseDto> update(
            @Parameter(description = "Идентификатор ресторана", example = "1")
            @PathVariable Long id,
            @RequestBody @Valid RestaurantRequestDto dto) {
        return restaurantService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ресторан",
            description = "Удаляет ресторан по его идентификатору.")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Идентификатор ресторана", example = "1")
            @PathVariable Long id) {
        boolean deleted = restaurantService.delete(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

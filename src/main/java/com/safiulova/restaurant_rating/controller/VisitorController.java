package com.safiulova.restaurant_rating.controller;

import com.safiulova.restaurant_rating.dto.visitor.VisitorRequestDto;
import com.safiulova.restaurant_rating.dto.visitor.VisitorResponseDto;
import com.safiulova.restaurant_rating.service.VisitorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Visitors", description = "Операции с посетителями ресторанов")
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping
    @Operation(summary = "Получить список всех посетителей",
            description = "Возвращает список всех зарегистрированных посетителей.")
    public List<VisitorResponseDto> getAll() {
        return visitorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить посетителя по ID",
            description = "Возвращает посетителя по его идентификатору.")
    public ResponseEntity<VisitorResponseDto> getById(
            @Parameter(description = "Идентификатор посетителя", example = "1")
            @PathVariable Long id) {
        return visitorService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать нового посетителя",
            description = "Создаёт нового посетителя. Имя может быть пустым.")
    public ResponseEntity<VisitorResponseDto> create(
            @RequestBody @Valid VisitorRequestDto dto) {
        VisitorResponseDto created = visitorService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные посетителя",
            description = "Обновляет данные посетителя по его идентификатору.")
    public ResponseEntity<VisitorResponseDto> update(
            @Parameter(description = "Идентификатор посетителя", example = "1")
            @PathVariable Long id,
            @RequestBody @Valid VisitorRequestDto dto) {
        return visitorService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить посетителя",
            description = "Удаляет посетителя по его идентификатору.")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Идентификатор посетителя", example = "1")
            @PathVariable Long id) {
        boolean deleted = visitorService.delete(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

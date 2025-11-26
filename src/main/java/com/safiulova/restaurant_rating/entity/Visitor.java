package com.safiulova.restaurant_rating.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {

    @NotNull
    private Long id;

    @Nullable
    private String name;

    @NotNull
    @Min(0)
    private int age;

    @NotNull
    private Gender gender;
}
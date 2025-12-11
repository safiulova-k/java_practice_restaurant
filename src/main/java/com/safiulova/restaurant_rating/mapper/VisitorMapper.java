package com.safiulova.restaurant_rating.mapper;

import com.safiulova.restaurant_rating.dto.visitor.VisitorRequestDto;
import com.safiulova.restaurant_rating.dto.visitor.VisitorResponseDto;
import com.safiulova.restaurant_rating.entity.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VisitorMapper {

    @Mapping(target = "id", ignore = true)
    Visitor toEntity(VisitorRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(VisitorRequestDto dto, @MappingTarget Visitor visitor);

    VisitorResponseDto toDto(Visitor visitor);
}

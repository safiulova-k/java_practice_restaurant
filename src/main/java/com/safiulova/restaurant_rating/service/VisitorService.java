package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.dto.visitor.VisitorRequestDto;
import com.safiulova.restaurant_rating.dto.visitor.VisitorResponseDto;
import com.safiulova.restaurant_rating.entity.Visitor;
import com.safiulova.restaurant_rating.mapper.VisitorMapper;
import com.safiulova.restaurant_rating.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorMapper visitorMapper;

    public List<VisitorResponseDto> findAll() {
        return visitorRepository.findAll().stream()
                .map(visitorMapper::toDto)
                .toList();
    }

    public Optional<VisitorResponseDto> findById(Long id) {
        return visitorRepository.findAll().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .map(visitorMapper::toDto);
    }

    public VisitorResponseDto create(VisitorRequestDto dto) {
        Visitor visitor = visitorMapper.toEntity(dto);
        visitor.setId(generateNewId());
        visitorRepository.save(visitor);
        return visitorMapper.toDto(visitor);
    }

    public Optional<VisitorResponseDto> update(Long id, VisitorRequestDto dto) {
        Optional<Visitor> visitorOpt = visitorRepository.findAll().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();

        if (visitorOpt.isEmpty()) {
            return Optional.empty();
        }

        Visitor visitor = visitorOpt.get();
        visitorMapper.updateEntityFromDto(dto, visitor);

        return Optional.of(visitorMapper.toDto(visitor));
    }

    public boolean delete(Long id) {
        Optional<Visitor> visitorOpt = visitorRepository.findAll().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();

        if (visitorOpt.isEmpty()) {
            return false;
        }

        visitorRepository.remove(visitorOpt.get());
        return true;
    }

    private long generateNewId() {
        return visitorRepository.findAll().stream()
                .mapToLong(Visitor::getId)
                .max()
                .orElse(0L) + 1L;
    }
}

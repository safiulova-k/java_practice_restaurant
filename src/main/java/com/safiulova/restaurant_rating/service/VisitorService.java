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
        return visitorRepository.findById(id)
                .map(visitorMapper::toDto);
    }

    public Optional<Visitor> findEntityById(Long id) {
        return visitorRepository.findById(id);
    }

    public VisitorResponseDto create(VisitorRequestDto dto) {
        Visitor visitor = visitorMapper.toEntity(dto);
        visitor.setId(null);
        Visitor saved = visitorRepository.save(visitor);
        return visitorMapper.toDto(saved);
    }

    public Optional<VisitorResponseDto> update(Long id, VisitorRequestDto dto) {
        Optional<Visitor> visitorOpt = visitorRepository.findById(id);
        if (visitorOpt.isEmpty()) {
            return Optional.empty();
        }

        Visitor visitor = visitorOpt.get();
        visitorMapper.updateEntityFromDto(dto, visitor);

        Visitor saved = visitorRepository.save(visitor);
        return Optional.of(visitorMapper.toDto(saved));
    }

    public boolean delete(Long id) {
        if (!visitorRepository.existsById(id)) {
            return false;
        }
        visitorRepository.deleteById(id);
        return true;
    }
}

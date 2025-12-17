package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.dto.visitor.VisitorRequestDto;
import com.safiulova.restaurant_rating.dto.visitor.VisitorResponseDto;
import com.safiulova.restaurant_rating.entity.Gender;
import com.safiulova.restaurant_rating.entity.Visitor;
import com.safiulova.restaurant_rating.mapper.VisitorMapper;
import com.safiulova.restaurant_rating.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private VisitorMapper visitorMapper;

    @InjectMocks
    private VisitorService visitorService;

    @Test
    void findAll_returnsListOfDtos() {
        Visitor v1 = new Visitor();
        v1.setId(1L);
        Visitor v2 = new Visitor();
        v2.setId(2L);

        when(visitorRepository.findAll()).thenReturn(List.of(v1, v2));
        when(visitorMapper.toDto(v1)).thenReturn(new VisitorResponseDto(1L, "Antonina", 20, Gender.FEMALE));
        when(visitorMapper.toDto(v2)).thenReturn(new VisitorResponseDto(2L, null, 30, Gender.MALE));

        List<VisitorResponseDto> result = visitorService.findAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals(2L, result.get(1).id());

        verify(visitorRepository).findAll();
        verify(visitorMapper).toDto(v1);
        verify(visitorMapper).toDto(v2);
    }

    @Test
    void findById_found_returnsOptionalDto() {
        long id = 10L;
        Visitor v = new Visitor();
        v.setId(id);

        when(visitorRepository.findById(id)).thenReturn(Optional.of(v));
        when(visitorMapper.toDto(v)).thenReturn(new VisitorResponseDto(id, "Luda", 85, Gender.FEMALE));

        Optional<VisitorResponseDto> result = visitorService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().id());

        verify(visitorRepository).findById(id);
        verify(visitorMapper).toDto(v);
    }

    @Test
    void findById_notFound_returnsEmpty() {
        long id = 404L;
        when(visitorRepository.findById(id)).thenReturn(Optional.empty());

        Optional<VisitorResponseDto> result = visitorService.findById(id);

        assertTrue(result.isEmpty());
        verify(visitorRepository).findById(id);
        verifyNoInteractions(visitorMapper);
    }

    @Test
    void create_savesEntity_andReturnsDto() {
        VisitorRequestDto dto = new VisitorRequestDto("Luda", 85, Gender.FEMALE);

        Visitor entity = new Visitor();
        entity.setName("Luda");
        entity.setAge(85);
        entity.setGender(Gender.FEMALE);

        Visitor saved = new Visitor();
        saved.setId(1L);
        saved.setName("Luda");
        saved.setAge(85);
        saved.setGender(Gender.FEMALE);

        when(visitorMapper.toEntity(dto)).thenReturn(entity);
        when(visitorRepository.save(any(Visitor.class))).thenReturn(saved);
        when(visitorMapper.toDto(saved)).thenReturn(new VisitorResponseDto(1L, "Luda", 85, Gender.FEMALE));

        VisitorResponseDto result = visitorService.create(dto);

        assertEquals(1L, result.id());
        assertEquals("Luda", result.name());

        // сервис сам выставляет id = null перед сохранением
        assertNull(entity.getId());

        verify(visitorMapper).toEntity(dto);
        verify(visitorRepository).save(entity);
        verify(visitorMapper).toDto(saved);
    }

    @Test
    void update_found_updatesEntity_saves_andReturnsDto() {
        long id = 5L;
        VisitorRequestDto dto = new VisitorRequestDto("New", 25, Gender.FEMALE);

        Visitor existing = new Visitor();
        existing.setId(id);

        Visitor saved = new Visitor();
        saved.setId(id);

        when(visitorRepository.findById(id)).thenReturn(Optional.of(existing));
        doNothing().when(visitorMapper).updateEntityFromDto(dto, existing);
        when(visitorRepository.save(existing)).thenReturn(saved);
        when(visitorMapper.toDto(saved)).thenReturn(new VisitorResponseDto(id, "New", 25, Gender.FEMALE));

        Optional<VisitorResponseDto> result = visitorService.update(id, dto);

        assertTrue(result.isPresent());
        assertEquals("New", result.get().name());

        verify(visitorRepository).findById(id);
        verify(visitorMapper).updateEntityFromDto(dto, existing);
        verify(visitorRepository).save(existing);
        verify(visitorMapper).toDto(saved);
    }

    @Test
    void update_notFound_returnsEmpty() {
        long id = 999L;
        VisitorRequestDto dto = new VisitorRequestDto("X", 1, Gender.MALE);
        when(visitorRepository.findById(id)).thenReturn(Optional.empty());

        Optional<VisitorResponseDto> result = visitorService.update(id, dto);

        assertTrue(result.isEmpty());
        verify(visitorRepository).findById(id);
        verifyNoInteractions(visitorMapper);
        verify(visitorRepository, never()).save(any());
    }

    @Test
    void delete_exists_deletesAndReturnsTrue() {
        long id = 1L;
        when(visitorRepository.existsById(id)).thenReturn(true);

        boolean result = visitorService.delete(id);

        assertTrue(result);
        verify(visitorRepository).existsById(id);
        verify(visitorRepository).deleteById(id);
    }

    @Test
    void delete_notExists_returnsFalse() {
        long id = 2L;
        when(visitorRepository.existsById(id)).thenReturn(false);

        boolean result = visitorService.delete(id);

        assertFalse(result);
        verify(visitorRepository).existsById(id);
        verify(visitorRepository, never()).deleteById(anyLong());
    }
}

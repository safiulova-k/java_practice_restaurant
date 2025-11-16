package com.safiulova.restaurant_rating.service;

import com.safiulova.restaurant_rating.entity.Visitor;
import com.safiulova.restaurant_rating.repository.VisitorRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public void save(Visitor visitor) {
        visitorRepository.save(visitor);
    }

    public void remove(Visitor visitor) {
        visitorRepository.remove(visitor);
    }

    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }
}
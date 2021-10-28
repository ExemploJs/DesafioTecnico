package com.example.user.history.service;

import com.example.user.history.model.History;
import com.example.user.history.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository repository;

    @Autowired
    public HistoryService(final HistoryRepository repository) {
        this.repository = repository;
    }

    public List<History> findByUserId(final Long userId) {
        return this.repository.findByUserId(userId);
    }
}

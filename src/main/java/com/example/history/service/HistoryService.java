package com.example.history.service;

import com.example.history.entity.History;
import com.example.history.repository.HistoryRepository;
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

    public List<History> findByAccountId(final Long accountId) {
        return this.repository.findByAccountId(accountId);
    }
}

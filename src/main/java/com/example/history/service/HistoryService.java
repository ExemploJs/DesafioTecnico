package com.example.history.service;

import com.example.history.HistoryResponse;
import com.example.history.entity.History;
import com.example.history.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private final HistoryRepository repository;

    @Autowired
    public HistoryService(final HistoryRepository repository) {
        this.repository = repository;
    }

    public List<HistoryResponse> findByAccountId(final Long accountId) {
        final List<History> histories = this.repository.findByAccountId(accountId);
        return histories
                .stream()
                .map(h ->
                        new HistoryResponse(h.getOperation().getOperation(), h.getMessage(), h.getCurrentBalance()))
                .collect(Collectors.toList());
    }
}

package com.example.history.controller;

import com.example.history.entity.History;
import com.example.history.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(final HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/history/{accountId}")
    public List<History> findByAccountId(@PathVariable("accountId") final Long userId) {
        return this.historyService.findByAccountId(userId);
    }
}

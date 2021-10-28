package com.example.user.history.controller;

import com.example.user.history.model.History;
import com.example.user.history.service.HistoryService;
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

    @GetMapping("/history/{userId}")
    public List<History> findByUserId(@PathVariable("userId") final Long userId) {
        return this.historyService.findByUserId(userId);
    }
}

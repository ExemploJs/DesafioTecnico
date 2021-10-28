package com.example.user.consumer;

import com.example.user.history.model.History;
import com.example.user.history.repository.HistoryRepository;
import com.example.user.history.request.HistoryRequest;
import com.example.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class HistoryConsumer {

    private final HistoryRepository historyRepository;
    private final UserService userService;

    @Autowired
    public HistoryConsumer(final HistoryRepository historyRepository,
                           final UserService userService) {
        this.historyRepository = historyRepository;
        this.userService = userService;
    }

    @KafkaListener(topics = "test-topic")
    void listener(final String data) throws JsonProcessingException {
        final String newData = parseData(data);
        final ObjectMapper mapper = new ObjectMapper();
        final HistoryRequest historyRequest = mapper.readValue(newData, HistoryRequest.class);

        final History history = this.getHistory(historyRequest);
        this.historyRepository.save(history);
    }

    private History getHistory(HistoryRequest historyRequest) {
        final History history = new History();
        history.setUser(this.userService.findById(historyRequest.getUserId()));
        history.setMessage(historyRequest.getMessage());
        history.setOperation(historyRequest.getOperation());
        return history;
    }

    private String parseData(final String data) {
        return data.substring(1, data.length() - 1).replaceAll("\\\\", "");
    }
}

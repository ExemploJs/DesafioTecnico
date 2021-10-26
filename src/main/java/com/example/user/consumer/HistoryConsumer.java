package com.example.user.consumer;

import com.example.user.history.model.History;
import com.example.user.history.model.history.request.HistoryRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class HistoryConsumer {

    @KafkaListener(topics = "test-topic")
    void listener(final String data) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        mapper
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final HistoryRequest history = mapper.convertValue(data, HistoryRequest.class);
        System.out.println(history);

    }
}

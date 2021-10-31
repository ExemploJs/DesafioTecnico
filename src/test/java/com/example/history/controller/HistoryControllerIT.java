package com.example.history.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HistoryControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    @SqlGroup({
            @Sql("/test-simple-data.sql"),
            @Sql(scripts = "/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void findByAccountId() {
        final String baseUrl = "http://localhost:" + this.randomServerPort + "/history/1";
        final ResponseEntity<String> getResponse = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(200, getResponse.getStatusCodeValue());

        assertEquals("[{\"operation\":\"Depósito\"," +
                "\"message\":\"DEPÓSITO REALIZADO\"," +
                "\"currentBalance\":10000.00}]", getResponse.getBody());
    }
}
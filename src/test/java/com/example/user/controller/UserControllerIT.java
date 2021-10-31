package com.example.user.controller;

import com.example.exception.handler.response.HandlerResponse;
import com.example.user.test.utils.UserCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    @Sql(scripts = "/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void createUser() throws URISyntaxException {
        final ResponseEntity<String> response = UserCreator.createUser(this.restTemplate, this.randomServerPort, "test create user 123");
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    @SqlGroup({
            @Sql("/test-user-creation-data2.sql"),
            @Sql(scripts = "/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void findByUserName() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/user/jamie";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals("{\"userName\":\"jamie\"}", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @SqlGroup({
            @Sql("/test-user-creation-data2.sql"),
            @Sql(scripts = "/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void findByUserNameWithError() throws JsonProcessingException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/user/java 123";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl, String.class);

        final ObjectMapper mapper = new ObjectMapper();
        final HandlerResponse handlerResponse = mapper.readValue(response.getBody(), HandlerResponse.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), handlerResponse.getCode());
        assertEquals("Usuário não encontrado/cadastrado!", handlerResponse.getMessage());
        assertTrue(Objects.nonNull(handlerResponse.getTimestamp()));
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @SqlGroup({
            @Sql("/test-user-creation-data2.sql"),
            @Sql(scripts = "/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void findAll() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/user";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals("[{\"userName\":\"jamie\"},{\"userName\":\"Nasir\"},{\"userName\":\"Lionel\"}]", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
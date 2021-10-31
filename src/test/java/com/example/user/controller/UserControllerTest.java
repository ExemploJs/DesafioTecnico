package com.example.user.controller;

import com.example.exception.handler.response.HandlerResponse;
import com.example.user.test.utils.UserCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Before
    public void setup() throws URISyntaxException {
        UserCreator.createUser(this.restTemplate, this.randomServerPort, "jamie");
        UserCreator.createUser(this.restTemplate, this.randomServerPort, "Nasir");
        UserCreator.createUser(this.restTemplate, this.randomServerPort, "Lionel");
    }

    @After
    public void tearDown() throws URISyntaxException {
        deleteAll();
    }

    @Test
    public void createUser() throws URISyntaxException {
        final ResponseEntity<String> response = UserCreator.createUser(this.restTemplate, this.randomServerPort, "test create user 123");
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void findByUserName() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/user/jamie";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals("{\"userName\":\"jamie\"}", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
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
    public void findAll() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/user";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals("[{\"userName\":\"jamie\"},{\"userName\":\"Nasir\"},{\"userName\":\"Lionel\"}]", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    public void deleteAll() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user";
        this.restTemplate.delete(new URI(baseUrl));
    }
}
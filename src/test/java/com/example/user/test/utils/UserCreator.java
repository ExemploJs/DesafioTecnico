package com.example.user.test.utils;

import com.example.user.request.UserRequest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class UserCreator {

    public static ResponseEntity<String> createUser(final TestRestTemplate restTemplate,
                                             final int randomServerPort, final String userName) throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/user";
        return restTemplate.postForEntity(new URI(baseUrl),
                new HttpEntity<>(new UserRequest(userName)),
                String.class);
    }
}

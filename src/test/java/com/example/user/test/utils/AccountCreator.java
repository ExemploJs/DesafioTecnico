package com.example.user.test.utils;

import com.example.user.account.request.AccountCreationRequest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class AccountCreator {

    public static ResponseEntity<String> create(final TestRestTemplate restTemplate, final int randomServerPort,
                                                final AccountCreationRequest request) throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/user/1/account";
        return restTemplate.postForEntity(new URI(baseUrl),
                new HttpEntity<>(request), String.class);
    }

    public static ResponseEntity<String> create(final TestRestTemplate restTemplate, final int randomServerPort,
                                                final AccountCreationRequest request, final String userId) throws URISyntaxException {
        final String baseUrl = String.format("http://localhost:" + randomServerPort + "/user/%s/account", userId);
        return restTemplate.postForEntity(new URI(baseUrl),
                new HttpEntity<>(request), String.class);
    }
}

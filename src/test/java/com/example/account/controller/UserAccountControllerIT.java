package com.example.account.controller;

import com.example.account.request.AccountCreationRequest;
import com.example.user.test.utils.AccountCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAccountControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    @SqlGroup({
            @Sql("/test-user-creation-data.sql"),
            @Sql(scripts = "/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void create() throws URISyntaxException {
        final ResponseEntity<String> response = AccountCreator.create(this.restTemplate, this.randomServerPort, new AccountCreationRequest("125245", "4342",
                "0", "4", new BigDecimal("15000")));
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    @SqlGroup({
            @Sql("/test-simple-data.sql"),
            @Sql(scripts = "/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void inactivate() {
        final ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + this.randomServerPort + "/user/1/account/inactivate",
                HttpMethod.PUT, new HttpEntity<>(null), String.class);

        assertEquals(200, response.getStatusCodeValue());

        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        final ResponseEntity<String> getResponse = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(200, getResponse.getStatusCodeValue());
        assertEquals("{\"accountNumber\":\"99999\"," +
                "\"agency\":\"1231\"," +
                "\"accountDigit\":\"1\"," +
                "\"agencyDigit\":\"4\"," +
                "\"balance\":9000.00," +
                "\"active\":false}", getResponse.getBody());
    }

    @Test
    @SqlGroup({
            @Sql("/test-simple-data.sql"),
            @Sql(scripts = "/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void get() {
        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("{\"accountNumber\":\"99999\"," +
                "\"agency\":\"1231\"," +
                "\"accountDigit\":\"1\"," +
                "\"agencyDigit\":\"4\"," +
                "\"balance\":9000.00," +
                "\"active\":true}", response.getBody());
    }
}
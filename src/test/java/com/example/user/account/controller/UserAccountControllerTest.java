package com.example.user.account.controller;

import com.example.user.account.request.AccountCreationRequest;
import com.example.user.test.utils.AccountCreator;
import com.example.user.test.utils.UserCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserAccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Before
    public void setup() throws URISyntaxException {
        UserCreator.createUser(this.restTemplate, this.randomServerPort, "accounttest");
        AccountCreator.create(this.restTemplate, this.randomServerPort, new AccountCreationRequest("99999", "1231",
                "1", "4", new BigDecimal("9000")));
    }

    @After
    public void tearDown() throws URISyntaxException {
        deleteAll();
    }

    @Test
    public void create() throws URISyntaxException {
        final ResponseEntity<String> response = AccountCreator.create(this.restTemplate, this.randomServerPort, new AccountCreationRequest("125245", "4342",
                "0", "4", new BigDecimal("15000")));
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void inactivate() {
        final ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + this.randomServerPort + "/user/1/account/inactivate",
                HttpMethod.PUT, new HttpEntity<>(null), String.class);

        assertEquals(200, response.getStatusCodeValue());

        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        final ResponseEntity<String> getResponse = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(200, getResponse.getStatusCodeValue());
        assertEquals("[{\"accountNumber\":\"99999\"," +
                "\"agency\":\"1231\"," +
                "\"accountDigit\":\"1\"," +
                "\"agencyDigit\":\"4\"," +
                "\"balance\":9000.00," +
                "\"active\":false}]", getResponse.getBody());
    }

    @Test
    public void get() {
        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("[{\"accountNumber\":\"99999\"," +
                "\"agency\":\"1231\"," +
                "\"accountDigit\":\"1\"," +
                "\"agencyDigit\":\"4\"," +
                "\"balance\":9000.00," +
                "\"active\":true}]", response.getBody());
    }

    private void deleteAll() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        this.restTemplate.delete(new URI(baseUrl));
    }
}
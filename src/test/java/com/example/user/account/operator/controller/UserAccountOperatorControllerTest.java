package com.example.user.account.operator.controller;


import com.example.exception.handler.response.HandlerResponse;
import com.example.user.account.operator.request.BillRequest;
import com.example.user.account.operator.request.RepresentativeRequest;
import com.example.user.account.operator.request.TransferRequest;
import com.example.user.account.request.AccountCreationRequest;
import com.example.user.test.utils.AccountCreator;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserAccountOperatorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Before
    public void setup() throws URISyntaxException {
        UserCreator.createUser(this.restTemplate, this.randomServerPort, "accounttest");
        UserCreator.createUser(this.restTemplate, this.randomServerPort, "accounttest2");
        AccountCreator.create(this.restTemplate, this.randomServerPort, new AccountCreationRequest("99999", "1231",
                "1", "4", new BigDecimal("9000")));
        AccountCreator.create(this.restTemplate, this.randomServerPort, new AccountCreationRequest("11111", "3342",
                "0", "2", new BigDecimal("55000")), "2");
    }

    @After
    public void tearDown() throws URISyntaxException {
        deleteAllAccounts();
        deleteAllUsers();
    }

    @Test
    public void withdraw() {
        final ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + this.randomServerPort + "/account/1/withdraw",
                HttpMethod.PUT, new HttpEntity<>(new RepresentativeRequest(new BigDecimal("2500"))), String.class);

        assertEquals(200, response.getStatusCodeValue());

        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        final ResponseEntity<String> getResponse = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(200, getResponse.getStatusCodeValue());
        assertEquals("[{\"accountNumber\":\"99999\"," +
                "\"agency\":\"1231\"," +
                "\"accountDigit\":\"1\"," +
                "\"agencyDigit\":\"4\"," +
                "\"balance\":6500.00," +
                "\"active\":true}]", getResponse.getBody());
    }

    @Test
    public void withdrawErrorWhenTheValueForWithDrawIsGreaterThanBalance() throws JsonProcessingException {
        final ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + this.randomServerPort + "/account/1/withdraw",
                HttpMethod.PUT, new HttpEntity<>(new RepresentativeRequest(new BigDecimal("9000"))), String.class);

        final ObjectMapper mapper = new ObjectMapper();
        final HandlerResponse handlerResponse = mapper.readValue(response.getBody(), HandlerResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("A conta não tem este valor para saque!", handlerResponse.getMessage());
        assertTrue(Objects.nonNull(handlerResponse.getTimestamp()));
    }

    @Test
    public void deposit() {
        final ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + this.randomServerPort + "/account/1/deposit",
                HttpMethod.PUT, new HttpEntity<>(new RepresentativeRequest(new BigDecimal("4000"))), String.class);

        assertEquals(200, response.getStatusCodeValue());

        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        final ResponseEntity<String> getResponse = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(200, getResponse.getStatusCodeValue());
        assertEquals("[{\"accountNumber\":\"99999\"," +
                "\"agency\":\"1231\"," +
                "\"accountDigit\":\"1\"," +
                "\"agencyDigit\":\"4\"," +
                "\"balance\":13000.00," +
                "\"active\":true}]", getResponse.getBody());
    }

    @Test
    public void transfer() {
        final ResponseEntity<String> response = this.restTemplate
                .exchange("http://localhost:" + this.randomServerPort + "/account/1/transfer/2",
                HttpMethod.PUT, new HttpEntity<>(new TransferRequest("Transferência teste!", new BigDecimal("4000"))),
                        String.class);

        assertEquals(200, response.getStatusCodeValue());

        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        final ResponseEntity<String> getResponse = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(200, getResponse.getStatusCodeValue());
        assertEquals("[{\"accountNumber\":\"99999\"," +
                "\"agency\":\"1231\"," +
                "\"accountDigit\":\"1\"," +
                "\"agencyDigit\":\"4\"," +
                "\"balance\":5000.00," +
                "\"active\":true}]", getResponse.getBody());

        final String baseUrl2 = "http://localhost:" + this.randomServerPort + "/user/2/account";
        final ResponseEntity<String> getResponse2 = this.restTemplate.getForEntity(baseUrl2, String.class);

        assertEquals(200, getResponse2.getStatusCodeValue());
        assertEquals("[{\"accountNumber\":\"11111\"," +
                "\"agency\":\"3342\"," +
                "\"accountDigit\":\"0\"," +
                "\"agencyDigit\":\"2\"," +
                "\"balance\":59000.00," +
                "\"active\":true}]", getResponse2.getBody());
    }

    @Test
    public void transferSameUserIdError() throws JsonProcessingException {
        final ResponseEntity<String> response = this.restTemplate
                .exchange("http://localhost:" + this.randomServerPort + "/account/1/transfer/1",
                        HttpMethod.PUT, new HttpEntity<>(new TransferRequest("Transferência teste!", new BigDecimal("4000"))),
                        String.class);

        final ObjectMapper mapper = new ObjectMapper();
        final HandlerResponse handlerResponse = mapper.readValue(response.getBody(), HandlerResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("O id de usuário de origem não pode ser o mesmo do usuário destino!", handlerResponse.getMessage());
        assertTrue(Objects.nonNull(handlerResponse.getTimestamp()));
    }

    @Test
    public void payBill() {
        final ResponseEntity<String> response = this.restTemplate
                .exchange("http://localhost:" + this.randomServerPort + "/account/1/bill",
                        HttpMethod.PUT, new HttpEntity<>(new BillRequest("151938", "Pagamento de Conta de Água!", new BigDecimal("270"))),
                        String.class);

        assertEquals(200, response.getStatusCodeValue());

        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        final ResponseEntity<String> getResponse = this.restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(200, getResponse.getStatusCodeValue());
        assertEquals("[{\"accountNumber\":\"99999\"," +
                "\"agency\":\"1231\"," +
                "\"accountDigit\":\"1\"," +
                "\"agencyDigit\":\"4\"," +
                "\"balance\":8730.00," +
                "\"active\":true}]", getResponse.getBody());
    }

    @Test
    public void payBillErrorWhenNull() {
        final ResponseEntity<String> response = this.restTemplate
                .exchange("http://localhost:" + this.randomServerPort + "/account/1/bill",
                        HttpMethod.PUT, new HttpEntity<>(new BillRequest(null, "Pagamento de Conta de Água!", new BigDecimal("270"))),
                        String.class);

        assertEquals(400, response.getStatusCodeValue());
    }

    public void deleteAllUsers() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user";
        this.restTemplate.delete(new URI(baseUrl));
    }

    private void deleteAllAccounts() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + this.randomServerPort + "/user/1/account";
        this.restTemplate.delete(new URI(baseUrl));
    }
}
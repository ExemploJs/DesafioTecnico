package com.example.user;

import com.example.user.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void createUser() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/user";
        final URI uri = new URI(baseUrl);

        final User user = new User();
        user.setUserName("test create user 123");

        final ResponseEntity<String> response = this.restTemplate.postForEntity(uri, new HttpEntity<>(user), String.class);

        assertEquals(201, response.getStatusCodeValue());
    }

}
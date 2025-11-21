package com.bjoernkw.mailtrigger.controllers;

import com.bjoernkw.mailtrigger.mailer.MailService;
import com.bjoernkw.mailtrigger.mailer.MailTriggerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MailTriggerControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private MailService mailService;

    @Autowired
    private MailTriggerConfig mailTriggerConfig;

    private RestTestClient restTestClient;

    @BeforeEach
    public void setup() {
        restTestClient =
                RestTestClient
                        .bindToController(
                                new MailTriggerController(mailService, mailTriggerConfig)
                        )
                        .build();
    }


    @Test
    void testSendMail() {
        Map<String, String> replacements = new HashMap<>();

        restTestClient
                .post()
                .uri("http://localhost:" + port + "/api/v1/sendMail/test_channel")
                .contentType(MediaType.APPLICATION_JSON)
                .body(replacements)
                .exchange()
                .expectStatus().isBadRequest();

        replacements.put("TO", "bjoern@bjoernkw.com");
        replacements.put("FIRST_NAME", "John");

        restTestClient
                .post()
                .uri("http://localhost:" + port + "/api/v1/sendMail/test_channel")
                .contentType(MediaType.APPLICATION_JSON)
                .body(replacements)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.text").isEqualTo("Email has been sent.");

        restTestClient
                .post()
                .uri("http://localhost:" + port + "/api/v1/sendMail/test_channel_with_custom_template")
                .contentType(MediaType.APPLICATION_JSON)
                .body(replacements)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.text").isEqualTo("Email has been sent.");

        restTestClient
                .post()
                .uri("http://localhost:" + port + "/api/v1/sendMail/nonExistentChannel")
                .contentType(MediaType.APPLICATION_JSON)
                .body(replacements)
                .exchange()
                .expectStatus().isNotFound();
    }
}

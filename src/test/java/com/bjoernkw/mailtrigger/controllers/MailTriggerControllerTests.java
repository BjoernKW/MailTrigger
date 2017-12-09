package com.bjoernkw.mailtrigger.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTriggerControllerTests {

    @Autowired
    private MailTriggerController mailTriggerController;

    @Before
    public void setup() {
        standaloneSetup(mailTriggerController);
    }

    @Test
    public void testSendMail() {
        Map<String, String> replacements = new HashMap<>();

        given()
                .contentType("application/json;charset=UTF-8")
                .body(replacements)
                .when()
                .post("/api/v1/sendMail/test_channel")
                .then()
                .statusCode(400);

        replacements.put("TO", "bjoern@bjoernkw.com");
        replacements.put("FIRST_NAME", "John");

        given()
                .contentType("application/json;charset=UTF-8")
                .body(replacements)
                .when()
                .post("/api/v1/sendMail/test_channel")
                .then()
                .statusCode(200)
                .body("text", equalTo("Email was sent."));

        given()
                .contentType("application/json;charset=UTF-8")
                .body(replacements)
                .when()
                .post("/api/v1/sendMail/nonExistentChannel")
                .then()
                .statusCode(404);
    }
}

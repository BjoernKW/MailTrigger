package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class MailServiceTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailService mailService;

    @Test
    void sendMail() throws Error {
        URL templateUrl = MailTriggerApplication.class.getResource("test_channel.md");
        Map<String, String> replacements = new HashMap<>();
        replacements.put("TO", "bjoern@bjoernkw.com");
        replacements.put("FIRST_NAME", "John");

        try {
            mailService.send(templateUrl, replacements);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            fail();
        }
    }
}

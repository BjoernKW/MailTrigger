package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailerTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Mailer mailer;

    @Test
    public void sendMail() throws Error {
        URL templateUrl = MailTriggerApplication.class.getResource("test_channel.md");
        MailHeader mailHeader = new MailHeader();
        mailHeader.setTo("bjoern@bjoernkw.com");
        mailHeader.setFrom("bjoern@bjoernkw.com");

        Map<String, String> replacements = new HashMap<>();
        replacements.put("TO", "bjoern@bjoernkw.com");
        replacements.put("FIRST_NAME", "John");

        try {
            mailer.send(mailHeader, templateUrl, replacements);
        } catch (Exception e) {
            logger.error("{}", e);
            assertTrue(false);
        }
    }
}

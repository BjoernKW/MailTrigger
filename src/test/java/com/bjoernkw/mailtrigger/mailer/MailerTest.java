package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailerTest {

    @Autowired
    private Mailer mailer;

    @Test
    public void sendMail() {
        URL templateUrl = MailTriggerApplication.class.getResource("test_channel.md");
        MailHeader mailHeader = new MailHeader("bjoern@bjoernkw.com")
                .from("bjoern@bjoernkw.com");

        Map<String, String> replacements = new HashMap<>();
        replacements.put("FIRST_NAME", "John");

        mailer.send(mailHeader, templateUrl, replacements);
    }
}

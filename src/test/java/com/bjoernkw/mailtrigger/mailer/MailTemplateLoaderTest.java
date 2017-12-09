package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import static org.junit.Assert.assertTrue;

public class MailTemplateLoaderTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void loadMailTemplate() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();

        URL resource = MailTriggerApplication.class.getResource("test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        assertTrue(mailTemplate.getSubject() != null && mailTemplate.getSubject().length() > 0);
        assertTrue(mailTemplate.getTextAsString() != null && mailTemplate.getTextAsString().length() > 0);
    }
}

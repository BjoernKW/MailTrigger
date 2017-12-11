package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import static org.junit.Assert.*;

public class MailTemplateLoaderTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void loadMailTemplate() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();

        URL resource = MailTriggerApplication.class.getResource("test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        assertTrue(mailTemplate.getSubject() != null && mailTemplate.getSubject().length() > 0);
        assertTrue(mailTemplate.getBodyTextAsString() != null && mailTemplate.getBodyTextAsString().length() > 0);

        assertEquals("Test email sent by MailTrigger", mailTemplate.getSubject());

        assertTrue(mailTemplate.getFormat() != null && mailTemplate.getFormat().equals("html"));
        assertEquals(0, mailTemplate.getAttachmentLength());
    }

    @Test
    public void loadTextMailTemplate() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();

        URL resource = MailTriggerApplication.class.getResource("text_test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        assertTrue(mailTemplate.getSubject() != null && mailTemplate.getSubject().length() > 0);
        assertTrue(mailTemplate.getBodyTextAsString() != null && mailTemplate.getBodyTextAsString().length() > 0);

        assertEquals("Test email with text format sent by MailTrigger", mailTemplate.getSubject());

        assertTrue(mailTemplate.getFormat() != null && mailTemplate.getFormat().equals("text"));
    }

    @Test
    public void loadMailTemplateWithAttachment() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();

        URL resource = MailTriggerApplication.class.getResource("attachment_test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        assertTrue(mailTemplate.getSubject() != null && mailTemplate.getSubject().length() > 0);
        assertTrue(mailTemplate.getBodyTextAsString() != null && mailTemplate.getBodyTextAsString().length() > 0);

        assertEquals("Test email with text format sent by MailTrigger", mailTemplate.getSubject());
        assertNotEquals(0, mailTemplate.getAttachmentLength());
    }
}

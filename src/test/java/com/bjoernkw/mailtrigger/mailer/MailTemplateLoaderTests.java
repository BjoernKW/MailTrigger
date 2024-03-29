package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class MailTemplateLoaderTests {

    @Test
    void loadMailTemplate() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();

        URL resource = MailTriggerApplication.class.getResource("test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        assertTrue(mailTemplate.getSubject() != null && mailTemplate.getSubject().length() > 0);
        assertTrue(mailTemplate.getBodyTextAsString() != null
                && mailTemplate.getBodyTextAsString().length() > 0);

        assertEquals("Test email sent by MailTrigger", mailTemplate.getSubject());

        assertTrue(mailTemplate.getFormat() != null && mailTemplate.getFormat().equals("html"));
        assertEquals(0, mailTemplate.getAttachmentLength());
    }

    @Test
    void loadTextMailTemplate() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();

        URL resource = MailTriggerApplication.class.getResource("text_test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        assertTrue(mailTemplate.getSubject() != null && mailTemplate.getSubject().length() > 0);
        assertTrue(mailTemplate.getBodyTextAsString() != null
                && mailTemplate.getBodyTextAsString().length() > 0);

        assertEquals("Test email with text format sent by MailTrigger", mailTemplate.getSubject());

        assertTrue(mailTemplate.getFormat() != null && mailTemplate.getFormat().equals("text"));
    }

    @Test
    void loadMailTemplateWithAttachment() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();

        URL resource = MailTriggerApplication.class.getResource("attachment_test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        assertTrue(mailTemplate.getSubject() != null && mailTemplate.getSubject().length() > 0);
        assertTrue(mailTemplate.getBodyTextAsString() != null
                && mailTemplate.getBodyTextAsString().length() > 0);

        assertEquals("Test email with attachment sent by MailTrigger", mailTemplate.getSubject());
        assertNotEquals(0, mailTemplate.getAttachmentLength());
    }
}

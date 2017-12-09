package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import com.bjoernkw.mailtrigger.exceptions.ReplacementsMissingException;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MailTemplatePlaceholderProcessorTests {

    @Test
    public void replace() {
        MailTemplatePlaceholderProcessor mailTemplatePlaceholderProcessor = new MailTemplatePlaceholderProcessor();

        String textBegin = "Test";
        StringBuilder text = new StringBuilder(textBegin + "{{REPLACEMENT}}");
        String placeholder = "REPLACEMENT";
        String replacement = "Basic Test";

        mailTemplatePlaceholderProcessor.replace(text, placeholder, replacement);

        assertEquals(text.toString(), (textBegin + replacement));
    }

    @Test
    public void replaceWithNull() {
        MailTemplatePlaceholderProcessor mailTemplatePlaceholderProcessor = new MailTemplatePlaceholderProcessor();

        String textBegin = "Test";
        StringBuilder text = new StringBuilder(textBegin + "{{REPLACEMENT}}");
        String placeholder = "REPLACEMENT";

        mailTemplatePlaceholderProcessor.replace(text, placeholder, null);

        assertEquals(text.toString(), textBegin);
    }

    @Test
    public void replaceWithReplacementsHash() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();
        URL resource = MailTriggerApplication.class.getResource("test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        Map<String, String> replacements = new HashMap<>();
        replacements.put("FIRST_NAME", "John");

        MailTemplatePlaceholderProcessor mailTemplatePlaceholderProcessor = new MailTemplatePlaceholderProcessor();
        mailTemplatePlaceholderProcessor.replace(mailTemplate, replacements);
    }

    @Test(expected = ReplacementsMissingException.class)
    public void replaceWithMissingReplacements() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();
        URL resource = MailTriggerApplication.class.getResource("test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        Map<String, String> replacements = new HashMap<>();

        MailTemplatePlaceholderProcessor mailTemplatePlaceholderProcessor = new MailTemplatePlaceholderProcessor();
        mailTemplatePlaceholderProcessor.replace(mailTemplate, replacements);
    }
}

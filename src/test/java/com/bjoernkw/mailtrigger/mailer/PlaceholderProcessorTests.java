package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import com.bjoernkw.mailtrigger.exceptions.ReplacementsMissingException;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PlaceholderProcessorTests {

    @Test
    public void replace() {
        PlaceholderProcessor placeholderProcessor = new PlaceholderProcessor();

        String textBegin = "Test";
        StringBuilder text = new StringBuilder(textBegin + "{{REPLACEMENT}}");
        String placeholder = "REPLACEMENT";
        String replacement = "Basic Test";

        placeholderProcessor.replace(text, placeholder, replacement);

        assertEquals(text.toString(), (textBegin + replacement));
    }

    @Test
    public void replaceWithNull() {
        PlaceholderProcessor placeholderProcessor = new PlaceholderProcessor();

        String textBegin = "Test";
        StringBuilder text = new StringBuilder(textBegin + "{{REPLACEMENT}}");
        String placeholder = "REPLACEMENT";

        placeholderProcessor.replace(text, placeholder, null);

        assertEquals(text.toString(), textBegin);
    }

    @Test
    public void replaceWithReplacementsHash() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();
        URL resource = MailTriggerApplication.class.getResource("test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        Map<String, String> replacements = new HashMap<>();
        replacements.put("TO", "bjoern@bjoernkw.com");
        replacements.put("FIRST_NAME", "John");

        PlaceholderProcessor placeholderProcessor = new PlaceholderProcessor();
        placeholderProcessor.replace(mailTemplate, replacements);
    }

    @Test(expected = ReplacementsMissingException.class)
    public void replaceWithMissingReplacements() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();
        URL resource = MailTriggerApplication.class.getResource("test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        Map<String, String> replacements = new HashMap<>();

        PlaceholderProcessor placeholderProcessor = new PlaceholderProcessor();
        placeholderProcessor.replace(mailTemplate, replacements);
    }
}

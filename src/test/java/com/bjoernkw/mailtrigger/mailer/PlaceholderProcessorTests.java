package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import com.bjoernkw.mailtrigger.exceptions.ReplacementsMissingException;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlaceholderProcessorTests {

    @Test
    void replace() {
        PlaceholderProcessor placeholderProcessor = new PlaceholderProcessor();

        String beginning = "Test";
        String placeholder = "REPLACEMENT";
        String replacement = "Basic Test";

        String parsedText = placeholderProcessor.parseInputField(beginning + "${REPLACEMENT}", placeholder, replacement);

        assertEquals(beginning + replacement, parsedText);
    }

    @Test
    void replaceWithNull() {
        PlaceholderProcessor placeholderProcessor = new PlaceholderProcessor();

        String beginning = "Test";
        String placeholder = "REPLACEMENT";

        assertThrows(ReplacementsMissingException.class, () -> {
            placeholderProcessor.parseInputField(beginning + "${REPLACEMENT}", placeholder, null);
        });
    }

    @Test
    void replaceWithReplacementsHash() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();
        URL resource = MailTriggerApplication.class.getResource("test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        Map<String, String> replacements = new HashMap<>();
        replacements.put("TO", "bjoern@bjoernkw.com");
        replacements.put("FIRST_NAME", "John");

        assertDoesNotThrow(() -> {
            PlaceholderProcessor placeholderProcessor = new PlaceholderProcessor();
            placeholderProcessor.parseMailTemplate(mailTemplate, replacements);
        });
    }

    @Test
    void replaceWithMissingReplacements() {
        MailTemplateLoader mailTemplateLoader = new MailTemplateLoader();
        URL resource = MailTriggerApplication.class.getResource("test_channel.md");
        MailTemplate mailTemplate = mailTemplateLoader.load(resource);

        Map<String, String> replacements = new HashMap<>();

        PlaceholderProcessor placeholderProcessor = new PlaceholderProcessor();

        assertThrows(ReplacementsMissingException.class, () -> {
            placeholderProcessor.parseMailTemplate(mailTemplate, replacements);
        });
    }
}

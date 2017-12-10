package com.bjoernkw.mailtrigger.mailer;

import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Map;

@Service
public class Mailer {

    private final MailSender mailSender;
    private final MailTemplateLoader mailTemplateLoader;
    private final PlaceholderProcessor placeholderProcessor;

    public Mailer(
            MailSender mailSender,
            MailTemplateLoader mailTemplateLoader,
            PlaceholderProcessor placeholderProcessor
    ) {
        this.mailSender = mailSender;
        this.mailTemplateLoader = mailTemplateLoader;
        this.placeholderProcessor = placeholderProcessor;
    }

    public void send(URL templateURL, Map<String, String> replacements) {
        MailTemplate mailTemplate = mailTemplateLoader.load(templateURL);
        placeholderProcessor.parseMailTemplate(mailTemplate, replacements);
        mailSender.send(mailTemplate);
    }
}

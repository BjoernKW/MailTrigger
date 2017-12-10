package com.bjoernkw.mailtrigger.mailer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Map;

@Service
public class Mailer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailSender mailSender;

    @Autowired
    private MailTemplateLoader mailTemplateLoader;

    @Autowired
    private PlaceholderProcessor placeholderProcessor;

    /**
     * Loads the given template from a URL, replaces the template's placeHolders
     * and sends the email.
     *
     * @param mailHeader   Mail header information
     * @param templateURL  URL of the Markdown template for this email. Usually those templates reside
     *                     in the classpath next to the calling class. A common idiom for obtaining
     *                     the URL is <code>Caller.class.getResource({fileName})</code>
     * @param replacements Map of replacements where each key is a placeholder and each value is the replacement
     */
    public void send(MailHeader mailHeader, URL templateURL, Map<String, String> replacements) {
        MailTemplate mailTemplate = mailTemplateLoader.load(templateURL);
        if (mailHeader != null) {
            addHeaderInformationToMailTemplate(mailHeader, mailTemplate);
        }

        placeholderProcessor.parseMailTemplate(mailTemplate, replacements);
        mailSender.send(mailTemplate);
    }

    /**
     * Convenience method for {@link Mailer#send(MailHeader, URL, Map)} where mail header information comes
     * directly from the mailTemplate instead of being defined separately.
     *
     * @param templateURL  URL of the Markdown template for this email
     * @param replacements Map of replacements where the key is a placeholder and the value is the replacement
     */
    public void send(URL templateURL, Map<String, String> replacements) {
        send(null, templateURL, replacements);
    }

    private void addHeaderInformationToMailTemplate(MailHeader mailHeader, MailTemplate mailTemplate) {
        warnIfSpecifiedMoreThanOnce(mailHeader.getSubject(), mailTemplate.getSubject());
        if (mailTemplate.getSubject() == null) {
            mailTemplate.setSubject(mailHeader.getSubject());
        }

        warnIfSpecifiedMoreThanOnce(mailHeader.getFrom(), mailTemplate.getFrom());
        if (mailTemplate.getFrom() == null) {
            mailTemplate.setFrom(mailHeader.getFrom());
        }

        warnIfSpecifiedMoreThanOnce(mailHeader.getTo(), mailTemplate.getTo());
        if (mailTemplate.getTo() == null) {
            mailTemplate.setTo(mailHeader.getTo());
        }

        warnIfSpecifiedMoreThanOnce(mailHeader.getCc(), mailTemplate.getCc());
        if (mailTemplate.getCc() == null) {
            mailTemplate.setCc(mailHeader.getCc());
        }

        warnIfSpecifiedMoreThanOnce(mailHeader.getBcc(), mailTemplate.getBcc());
        if (mailTemplate.getBcc() == null) {
            mailTemplate.setBcc(mailHeader.getBcc());
        }
    }

    private void warnIfSpecifiedMoreThanOnce(String value1, String value2) {
        if (value1 != null && value2 != null) {
            logger.warn("Mail header values specified multiple times: {}, {}", value1, value2);
        }
    }
}

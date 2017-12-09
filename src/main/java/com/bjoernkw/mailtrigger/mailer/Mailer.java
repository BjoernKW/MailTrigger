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
    private MailTemplatePlaceholderProcessor mailTemplatePlaceholderProcessor;

    /**
     * Loads the given template from an URL, replaces the template's placeHolders
     * and sends the email.
     *
     * @param mailHeader   Meta information with at least a recipient
     * @param templateURL  URL of the markdown template for this email. Usually those templates reside
     *                     in the classpath next to the calling class and a common idiom to obtain
     *                     the URL would be <code>Caller.class.getResource({fileName})</code>
     * @param replacements Map of replacements where the key is a placeholder and the value is the replacement
     */
    public void send(MailHeader mailHeader, URL templateURL, Map<String, String> replacements) {
        MailTemplate mailTemplate = mailTemplateLoader.load(templateURL);
        if (mailHeader != null) {
            addHeaderInformationToMailTemplate(mailHeader, mailTemplate);
        }
        mailTemplatePlaceholderProcessor.replace(mailTemplate, replacements);

        mailSender.send(mailTemplate);
    }

    /**
     * Convenience method for {@link Mailer#send(MailHeader, URL, Map)} where mail header information comes
     * from the mailTemplate.
     *
     * @param templateURL  URL of the markdown template for this email
     * @param replacements Map of replacements where the key is a placeholder and the value is the replacement
     */
    public void send(URL templateURL, Map<String, String> replacements) {
        this.send(null, templateURL, replacements);
    }

    private void addHeaderInformationToMailTemplate(MailHeader mailHeader, MailTemplate mailTemplate) {
        warnIfSpecifiedMoreThanOnce(mailHeader.recipient, mailTemplate.getTo());
        if (mailTemplate.getTo() == null) {
            mailTemplate.setTo(mailHeader.recipient);
        }

        warnIfSpecifiedMoreThanOnce(mailHeader.cc, mailTemplate.getCc());
        if (mailTemplate.getCc() == null) {
            mailTemplate.setCc(mailHeader.cc);
        }

        warnIfSpecifiedMoreThanOnce(mailHeader.bcc, mailTemplate.getBcc());
        if (mailTemplate.getBcc() == null) {
            mailTemplate.setBcc(mailHeader.bcc);
        }

        warnIfSpecifiedMoreThanOnce(mailHeader.from, mailTemplate.getFrom());
        if (mailTemplate.getFrom() == null) {
            mailTemplate.setFrom(mailHeader.from);
        }

        warnIfSpecifiedMoreThanOnce(mailHeader.subject, mailTemplate.getSubject());
        if (mailTemplate.getSubject() == null) {
            mailTemplate.setSubject(mailHeader.subject);
        }
    }

    private void warnIfSpecifiedMoreThanOnce(String value1, String value2) {
        if (value1 != null && value2 != null) {
            logger.warn("Mail header values specified multiple times: {}, {}", value1, value2);
        }
    }
}

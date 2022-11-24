package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.exceptions.MailException;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

@Service
public class MailSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MailTriggerConfig mailTriggerConfig;

    private final MailRenderer mailRenderer;

    public MailSender(
            MailTriggerConfig mailTriggerConfig,
            MailRenderer mailRenderer
    ) {
        this.mailTriggerConfig = mailTriggerConfig;
        this.mailRenderer = mailRenderer;
    }

    @Async
    public void send(MailTemplate mail) {
        requireNonNull(mail);

        Transport transport = null;
        try {
            Session session = Session.getDefaultInstance(createSessionPropertiesFromConfig(mailTriggerConfig));
            MimeMessage message = createMessage(session, mail);
            logger.info("Mail subject: {}", mail.getSubject());
            logger.info("Mail sender: {}", mail.getFrom());

            StringBuilder recipients = new StringBuilder()
                    .append(mail.getTo())
                    .append(";").append(mail.getCc())
                    .append(";").append(mail.getBcc());
            logger.info("Mail recipients: {}", recipients);

            transport = session.getTransport("smtp");
            transport.connect(
                    mailTriggerConfig.getHost(),
                    mailTriggerConfig.getPort(),
                    mailTriggerConfig.getUsername(),
                    mailTriggerConfig.getPassword()
            );
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            logger.error("Error on sending mail: {}", e.getMessage());
        } finally {
            try {
                if (transport != null) {
                    transport.close();
                }
            } catch (MessagingException e) {
                logger.error("Problem while closing transport: {}", e.getMessage());
            }
        }

    }

    private MimeMessage createMessage(Session session, MailTemplate mailTemplate)
            throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailTemplate.getFrom()));

        try {
            addRecipients(message, mailTemplate.getTo(), Message.RecipientType.TO);
            addRecipients(message, mailTemplate.getCc(), Message.RecipientType.CC);
            addRecipients(message, mailTemplate.getBcc(), Message.RecipientType.BCC);

            message.setSubject(mailTemplate.getSubject());
            message.setContent(
                    createMultipart(
                            mailTemplate.getBodyTextAsString(),
                            mailTemplate.getAttachmentAsString(),
                            mailTemplate.getFormat()
                    )
            );
        } catch (Exception e) {
            throw new MailException();
        }

        return message;
    }

    private void addRecipients(MimeMessage message, String recipients, Message.RecipientType type)
            throws MessagingException {
        if (recipients != null) {
            String[] recipientArray = recipients.split(";");
            for (String recipient : recipientArray) {
                recipient = recipient.trim();
                message.addRecipient(type, new InternetAddress(recipient));
            }
        }
    }

    private Multipart createMultipart(
            String content,
            String attachment,
            String format
    ) throws MessagingException {
        Multipart multipart = new MimeMultipart();

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(mailRenderer.render(content, format), StandardCharsets.UTF_8.name(), format);
        multipart.addBodyPart(messageBodyPart);

        if (attachment != null) {
            MimeBodyPart fileBodyPart = new PreencodedMimeBodyPart("base64");
            fileBodyPart.setText(attachment);
            multipart.addBodyPart(fileBodyPart);
        }

        return multipart;
    }

    private Properties createSessionPropertiesFromConfig(MailTriggerConfig mailTransferConfig) {
        requireNonNull(mailTransferConfig);

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", mailTransferConfig.getHost());
        properties.setProperty("mail.smtp.auth", "true");

        return properties;
    }
}

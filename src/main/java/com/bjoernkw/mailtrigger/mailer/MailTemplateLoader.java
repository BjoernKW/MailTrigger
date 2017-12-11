package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.exceptions.MailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
class MailTemplateLoader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String bodyTextSeparator = "+++BODY_TEXT+++";
    private String attachmentSeparator = "+++ATTACHMENT+++";

    public MailTemplate load(URL url) {
        MailTemplate mailTemplate = new MailTemplate();
        BufferedReader reader = null;

        try (InputStream inputStream = url.openStream()) {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8.name()));

            boolean isReadingBodyText = false;
            boolean isReadingAttachment = false;

            String line;
            while ((line = reader.readLine()) != null) {
                isReadingBodyText = isReadingSection(isReadingBodyText, bodyTextSeparator, line);
                isReadingAttachment = isReadingSection(isReadingAttachment, attachmentSeparator, line);

                readTemplate(line, isReadingBodyText, isReadingAttachment, mailTemplate);
            }

            return mailTemplate;
        } catch (Exception e) {
            throw new MailException(e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                logger.error("{}", e);
            }
        }
    }

    private void readTemplate(
            String line,
            boolean isReadingBodyText,
            boolean isReadingAttachment,
            MailTemplate mailTemplate
    ) {
        if (line == null) {
            return;
        }

        String processedLine = line.trim();

        if (processedLine.startsWith(this.bodyTextSeparator)
                || processedLine.startsWith(this.attachmentSeparator)) {
            return;
        }

        if (isReadingBodyText && !isReadingAttachment) {
            if (mailTemplate.getBodyTextLength() > 0 && mailTemplate.getFormat().equals("html")) {
                processedLine = "<br>" + processedLine;
            }
            mailTemplate.appendTextToBody(processedLine);
        }
        if (isReadingAttachment && !isReadingBodyText) {
            mailTemplate.appendToAttachment(processedLine);
        }
        if (isNeitherReadingBodyNorAttachement(isReadingBodyText, isReadingAttachment)) {
            setMailProperty(mailTemplate, processedLine);
        }
    }

    private boolean isReadingSection(boolean isReadingSection, String separator, String line) {
        if (line != null) {
            String processedLine = line.trim();
            if (processedLine.startsWith(separator)) {
                return true;
            }
        }

        return isReadingSection;
    }

    private boolean isNeitherReadingBodyNorAttachement(boolean isReadingBodyText, boolean isReadingAttachment) {
        return !(isReadingBodyText || isReadingAttachment);
    }

    private void setMailProperty(MailTemplate mailTemplate, String line) {
        String propertyName = getPropertyName(line);
        String propertyValue = getPropertyValue(line);
        Field field = null;

        try {
            field = mailTemplate.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(mailTemplate, propertyValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("{}", e);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    private String getPropertyName(String line) {
        int indexOf = line.indexOf(':');
        if (indexOf > 0) {
            return line.substring(0, indexOf);
        }

        return "";
    }

    private String getPropertyValue(String line) {
        int indexOf = line.indexOf(':');
        if (indexOf > 0) {
            return line.substring(indexOf + 1).trim();
        }

        return "";
    }
}

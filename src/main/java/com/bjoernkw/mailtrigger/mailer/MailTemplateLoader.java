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

    private String separator = "+++";

    public MailTemplate load(URL url) {
        boolean readingText = false;
        MailTemplate mailTemplate = new MailTemplate();
        BufferedReader reader = null;

        try (InputStream inputStream = url.openStream()) {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8.name()));
            String line;
            while ((line = reader.readLine()) != null) {
                readingText = this.isReadingText(readingText, line);
                this.readTemplate(line, readingText, mailTemplate);
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

    private void readTemplate(String line, boolean readingText, MailTemplate mailTemplate) {
        if (line != null) {
            String processedLine = line.trim();

            if (processedLine.startsWith(this.separator))
                return;
            if (readingText) {
                if (mailTemplate.getTextLength() > 0) {
                    processedLine = "<br/>" + processedLine;
                }
                mailTemplate.appendText(processedLine);
            } else {
                this.setMailProperty(mailTemplate, processedLine);
            }
        }
    }

    private boolean isReadingText(boolean readingText, String line) {
        if (line != null) {
            String processedLine = line.trim();
            if (processedLine.startsWith(this.separator)) {
                return true;
            }
        }

        return readingText;
    }

    private void setMailProperty(MailTemplate mailTemplate, String line) {
        boolean accessible = false;
        String propertyName = this.getPropertyName(line);
        String propertyValue = this.getPropertyValue(line);
        Field field = null;

        try {
            field = mailTemplate.getClass().getDeclaredField(propertyName);
            accessible = field.canAccess(field);
            field.setAccessible(true);
            field.set(mailTemplate, propertyValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("{}", e);
        } finally {
            if (field != null) {
                field.setAccessible(accessible);
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

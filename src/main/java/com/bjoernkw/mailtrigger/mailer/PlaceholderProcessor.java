package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.exceptions.ReplacementsMissingException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

@Service
public class PlaceholderProcessor {

    private Pattern placeholderPattern;

    public PlaceholderProcessor() {
        placeholderPattern = Pattern.compile("\\{\\{.+}}");
    }

    public void replace(MailTemplate mailTemplate, Map<String, String> replacements) {
        requireNonNull(mailTemplate);
        requireNonNull(replacements);

        mailTemplate.setFrom(this.replace(mailTemplate.getFrom(), replacements));
        mailTemplate.setTo(this.replace(mailTemplate.getTo(), replacements));
        mailTemplate.setCc(this.replace(mailTemplate.getCc(), replacements));
        mailTemplate.setBcc(this.replace(mailTemplate.getBcc(), replacements));
        String replaceStr = this.replace(mailTemplate.getSubject(), replacements);
        mailTemplate.setSubject(replaceStr);

        String text = this.replace(mailTemplate.getTextAsString(), replacements);
        Matcher matcher = placeholderPattern.matcher(text);
        if (matcher.find()) {
            throw new ReplacementsMissingException();
        }

        mailTemplate.clearText();
        mailTemplate.appendText(text);
    }

    private String replace(String input, Map<String, String> replacements) {
        if (input != null && input.length() > 0) {
            StringBuilder text = new StringBuilder(input);
            for (Entry<String, String> entry : replacements.entrySet()) {
                this.replace(text, entry.getKey(), entry.getValue());
            }

            return text.toString();
        }

        return input;
    }

    protected void replace(StringBuilder text, String placeholder, String replacement) {
        if (text != null && placeholder != null) {
            String interpolatedPlaceholder = "{{" + placeholder + "}}";

            int begin = 0;
            while (begin < text.length()) {
                int indexOf = text.indexOf(interpolatedPlaceholder, begin);
                if (indexOf >= 0) {
                    int end = indexOf + interpolatedPlaceholder.length();
                    text.replace(indexOf, end, replacement != null ? replacement : "");
                    begin = end;
                } else {
                    begin = text.length();
                }
            }
        }
    }
}

package com.bjoernkw.mailtrigger.mailer;

import com.bjoernkw.mailtrigger.exceptions.ReplacementsMissingException;
import org.apache.commons.text.StrSubstitutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

@Service
public class PlaceholderProcessor {

    private Pattern placeholderPattern;

    public PlaceholderProcessor() {
        placeholderPattern = Pattern.compile("(\\$\\{.+})");
    }

    public void parseMailTemplate(MailTemplate mailTemplate, Map<String, String> replacements) {
        requireNonNull(mailTemplate);
        requireNonNull(replacements);

        mailTemplate.setFrom(parseInputField(mailTemplate.getFrom(), replacements));
        mailTemplate.setTo(parseInputField(mailTemplate.getTo(), replacements));
        mailTemplate.setCc(parseInputField(mailTemplate.getCc(), replacements));
        mailTemplate.setBcc(parseInputField(mailTemplate.getBcc(), replacements));
        mailTemplate.setSubject(parseInputField(mailTemplate.getSubject(), replacements));

        String text = parseInputField(mailTemplate.getBodyTextAsString(), replacements);

        mailTemplate.clearBodyText();
        mailTemplate.appendTextToBody(text);
    }

    protected String parseInputField(String input, String placeholder, String replacement) {
        Map<String, String> replacements = new HashMap<>();
        replacements.put(placeholder, replacement);

        return parseInputField(input, replacements);
    }

    private String parseInputField(String input, Map<String, String> replacements) {
        if (input != null && input.length() > 0) {
            StrSubstitutor substitutor = new StrSubstitutor(replacements);
            String output = substitutor.replace(input);

            checkForRemainingPlaceholders(output);

            return output;
        }

        return input;
    }

    private void checkForRemainingPlaceholders(String output) {
        List<String> remainingPlaceHolders = new ArrayList<>();
        Matcher matcher = placeholderPattern.matcher(output);
        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                if (i > 0) {
                    remainingPlaceHolders.add(matcher.group(i));
                }
            }
        }

        if (!remainingPlaceHolders.isEmpty()) {
            throw new ReplacementsMissingException(remainingPlaceHolders.toString());
        }
    }
}

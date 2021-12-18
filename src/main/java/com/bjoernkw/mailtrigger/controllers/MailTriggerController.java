package com.bjoernkw.mailtrigger.controllers;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import com.bjoernkw.mailtrigger.exceptions.ChannelNotFoundException;
import com.bjoernkw.mailtrigger.mailer.MailService;
import com.bjoernkw.mailtrigger.mailer.MailTriggerConfig;
import com.bjoernkw.mailtrigger.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class MailTriggerController {

    private final MailService mailService;

    private final MailTriggerConfig mailTriggerConfig;

    public MailTriggerController(
            MailService mailService,
            MailTriggerConfig mailTriggerConfig
    ) {
        this.mailService = mailService;
        this.mailTriggerConfig = mailTriggerConfig;
    }

    @GetMapping("/")
    public String start(Model model) {
        model.addAttribute("year", LocalDate.now().getYear());

        return "index";
    }

    @PostMapping(value = "/api/v1/sendMail/{channel}")
    @ResponseBody
    public Message sendMail(
            @PathVariable String channel,
            @RequestBody Map<String, String> replacements
    ) {
        // First, try to load the template from the directory set in the application configuration.
        URL resourceURL = getClass().getClassLoader().getResource(mailTriggerConfig.getMailTemplateDirectory() + "/" + channel + ".md");
        if (resourceURL == null) {
            // If no template with the requested channel name has been found in that directory, try to load the template
            // from the classpath instead.
            resourceURL = getClass().getClassLoader().getResource(channel + ".md");
        }
        if (resourceURL == null) {
            // Finally, attempt to load the template relative to the application's package name
            resourceURL = MailTriggerApplication.class.getResource(channel + ".md");
        }
        if (resourceURL == null) {
            throw new ChannelNotFoundException();
        }

        mailService.send(
                resourceURL,
                replacements
        );

        Message message = new Message();
        message.setText("Email has been sent.");

        return message;
    }
}

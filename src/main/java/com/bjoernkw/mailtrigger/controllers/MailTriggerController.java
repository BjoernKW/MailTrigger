package com.bjoernkw.mailtrigger.controllers;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import com.bjoernkw.mailtrigger.mailer.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class MailTriggerController {

    @Autowired
    private Mailer mailer;

    @RequestMapping("/")
    public String start() {
        return "index";
    }

    @RequestMapping("/sendMail/{channel}")
    @ResponseBody
    public String sendMail(
            @PathVariable String channel,
            @RequestBody Map<String, String> replacements
    ) {
        mailer.send(
                MailTriggerApplication.class.getResource(channel + ".md"),
                replacements
        );

        return "";
    }
}

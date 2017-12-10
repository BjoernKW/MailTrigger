package com.bjoernkw.mailtrigger.controllers;

import com.bjoernkw.mailtrigger.MailTriggerApplication;
import com.bjoernkw.mailtrigger.exceptions.ChannelNotFoundException;
import com.bjoernkw.mailtrigger.mailer.Mailer;
import com.bjoernkw.mailtrigger.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class MailTriggerController {

    private final Mailer mailer;

    public MailTriggerController(Mailer mailer) {
        this.mailer = mailer;
    }

    @RequestMapping("/")
    public String start(Model model) {
        model.addAttribute("year", LocalDate.now().getYear());

        return "index";
    }

    @RequestMapping(
            value = "/api/v1/sendMail/{channel}",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Message sendMail(
            @PathVariable String channel,
            @RequestBody Map<String, String> replacements
    ) {
        URL resourceURL = MailTriggerApplication.class.getResource(channel + ".md");
        if (resourceURL == null) {
            throw new ChannelNotFoundException();
        }

        mailer.send(
                resourceURL,
                replacements
        );

        Message message = new Message();
        message.setText("Email was sent.");

        return message;
    }
}

package com.bjoernkw.mailtrigger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error occurred while trying to send the email.")
public class MailException extends RuntimeException {

    public MailException() {
    }

    public MailException(String message) {
        super(message);
    }
}

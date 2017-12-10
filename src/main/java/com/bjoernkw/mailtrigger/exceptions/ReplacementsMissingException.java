package com.bjoernkw.mailtrigger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Required template replace is missing.")
public class ReplacementsMissingException extends RuntimeException {

    public ReplacementsMissingException(String message) {
        super(message);
    }
}

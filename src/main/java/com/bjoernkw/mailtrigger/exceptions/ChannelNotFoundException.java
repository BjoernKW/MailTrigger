package com.bjoernkw.mailtrigger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Channel not found.")
public class ChannelNotFoundException extends RuntimeException {

}

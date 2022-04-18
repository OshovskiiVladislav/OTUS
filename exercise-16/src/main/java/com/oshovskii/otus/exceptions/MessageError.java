package com.oshovskii.otus.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageError {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;

    public MessageError(AbstractException exception) {
        this.status = exception.getHttpStatus().value();
        this.message = exception.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}

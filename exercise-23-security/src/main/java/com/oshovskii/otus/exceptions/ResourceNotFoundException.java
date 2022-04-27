package com.oshovskii.otus.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String reason) {
        super(reason);
    }
}

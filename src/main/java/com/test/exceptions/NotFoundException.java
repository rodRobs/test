package com.test.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String detail) {
        super(detail);
    }

}

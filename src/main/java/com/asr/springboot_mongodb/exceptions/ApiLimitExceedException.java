package com.asr.springboot_mongodb.exceptions;

public class ApiLimitExceedException extends RuntimeException{
    private String message;

    public ApiLimitExceedException() {}

    public ApiLimitExceedException(String msg) {
        super(msg);
        this.message = msg;
    }
}

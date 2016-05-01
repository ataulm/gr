package com.ataulm.greatreads;

public class HttpException extends RuntimeException {

    public HttpException(int code) {
        super("Error performing request. HTTP status code: " + code);
    }

}

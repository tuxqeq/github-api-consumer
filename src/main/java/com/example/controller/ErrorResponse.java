package com.example.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @JsonProperty("status")
    public int getStatus() {
        return status;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
}
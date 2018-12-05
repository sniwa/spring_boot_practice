package com.example.introduction.entity;

import java.io.Serializable;

public class APIResponse implements Serializable  {
    private String message;

    private int statusCode;

    private Serializable response;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Serializable getResponse() {
        return response;
    }

    public void setResponse(Serializable response) {
        this.response = response;
    }
}

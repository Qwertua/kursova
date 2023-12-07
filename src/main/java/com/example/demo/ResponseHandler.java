package com.example.demo;

public interface ResponseHandler {
    void setNextHandler(ResponseHandler nextHandler);

    boolean handleResponse(int statusCode);
}
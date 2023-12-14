package com.example.demo.handler;

public interface ResponseHandler {
    void setNextHandler(ResponseHandler nextHandler);

    boolean handleResponse(int statusCode);
}
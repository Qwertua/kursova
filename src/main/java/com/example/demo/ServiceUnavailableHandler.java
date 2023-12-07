package com.example.demo;

public class ServiceUnavailableHandler implements ResponseHandler {
    private ResponseHandler nextHandler;

    @Override
    public void setNextHandler(ResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public boolean handleResponse(int statusCode) {
        if (statusCode == 503) {
            System.out.println("503 Service Unavailable Error");
            // Handle 503 error, e.g., display a message to the user
            return true; // Handled
        } else if (nextHandler != null) {
            return nextHandler.handleResponse(statusCode);
        }
        return false; // Not handled
    }
}
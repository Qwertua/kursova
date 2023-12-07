package com.example.demo;

public class NotFoundHandler implements ResponseHandler {
    private ResponseHandler nextHandler;

    @Override
    public void setNextHandler(ResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public boolean handleResponse(int statusCode) {
        if (statusCode == 404) {
            System.out.println("404 Not Found Error");
            // Handle 404 error, e.g., display a message to the user

            return true; // Handled
        } else if (nextHandler != null) {
            return nextHandler.handleResponse(statusCode);
        }
        return false; // Not handled
    }
}
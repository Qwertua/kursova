package com.example.demo;

public class BadGatewayHandler implements ResponseHandler {
    private ResponseHandler nextHandler;

    @Override
    public void setNextHandler(ResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public boolean handleResponse(int statusCode) {
        if (statusCode == 502) {
            System.out.println("502 Bad Gateway Error");
            // Handle 502 error, e.g., display a message to the user
            return true; // Handled
        } else if (nextHandler != null) {
            return nextHandler.handleResponse(statusCode);
        }
        return false; // Not handled
    }
}

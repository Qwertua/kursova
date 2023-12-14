package com.example.demo.handler;

public class ServiceUnavailableHandler extends BaseResponseHandler {
    @Override
    public boolean handleResponse(int statusCode) {
        if (statusCode == 503) {

            // Handle 503 error, e.g., display a message to the user
            return true; // Handled
        } else {
            return callNextHandler(statusCode);
        }
    }
}
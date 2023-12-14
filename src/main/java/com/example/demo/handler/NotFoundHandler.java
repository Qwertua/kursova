package com.example.demo.handler;
public class NotFoundHandler extends BaseResponseHandler {
    @Override
    public boolean handleResponse(int statusCode) {
        if (statusCode == 404) {

            // Handle 404 error, e.g., display a message to the user
            return true; // Handled
        } else {
            return callNextHandler(statusCode);
        }
    }
}
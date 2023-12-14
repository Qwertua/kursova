package com.example.demo.handler;
public class BadGatewayHandler extends BaseResponseHandler {
    @Override
    public boolean handleResponse(int statusCode) {
        if (statusCode == 502) {

            // Handle 502 error, e.g., display a message to the user
            return true; // Handled
        } else {
            return callNextHandler(statusCode);
        }
    }
}


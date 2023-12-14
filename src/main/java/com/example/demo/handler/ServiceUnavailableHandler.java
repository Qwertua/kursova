package com.example.demo.handler;

public class ServiceUnavailableHandler extends BaseResponseHandler {
    @Override
    public boolean handleResponse(int statusCode) {
        if (statusCode == 503) {

           /* Обробка помилки 503*/
            return true;
        } else {
            return callNextHandler(statusCode);
        }
    }
}
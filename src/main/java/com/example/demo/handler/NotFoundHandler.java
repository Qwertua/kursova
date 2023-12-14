package com.example.demo.handler;
public class NotFoundHandler extends BaseResponseHandler {
    @Override
    public boolean handleResponse(int statusCode) {
        if (statusCode == 404) {

          /*   Обробка помилки 404*/
            return true;
        } else {
            return callNextHandler(statusCode);
        }
    }
}
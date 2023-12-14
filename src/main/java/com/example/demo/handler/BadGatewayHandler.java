package com.example.demo.handler;
public class BadGatewayHandler extends BaseResponseHandler {
    @Override
    public boolean handleResponse(int statusCode) {
        if (statusCode == 502) {

          /*  Обробка помилки 502 */
            return true;
        } else {
            return callNextHandler(statusCode);
        }
    }
}


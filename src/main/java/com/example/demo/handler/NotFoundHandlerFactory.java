package com.example.demo.handler;

public class NotFoundHandlerFactory implements ErrorHandlerFactory {
    @Override
    public ResponseHandler createHandler() {
        return new NotFoundHandler();
    }
}

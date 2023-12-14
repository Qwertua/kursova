package com.example.demo.handler;

public class BadGatewayHandlerFactory implements ErrorHandlerFactory {
    @Override
    public ResponseHandler createHandler() {
        return new BadGatewayHandler();
    }
}
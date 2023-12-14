package com.example.demo.handler;

public class ServiceUnavailableHandlerFactory implements ErrorHandlerFactory {
    @Override
    public ResponseHandler createHandler() {
        return new ServiceUnavailableHandler();
    }
}

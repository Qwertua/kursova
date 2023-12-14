package com.example.demo.factory;

import com.example.demo.handler.ResponseHandler;
import com.example.demo.handler.ServiceUnavailableHandler;

public class ServiceUnavailableHandlerFactory implements ErrorHandlerFactory {
    @Override
    public ResponseHandler createHandler() {
        return new ServiceUnavailableHandler();
    }
}

package com.example.demo.factory;

import com.example.demo.handler.BadGatewayHandler;
import com.example.demo.handler.ResponseHandler;

public class BadGatewayHandlerFactory implements ErrorHandlerFactory {
    @Override
    public ResponseHandler createHandler() {
        return new BadGatewayHandler();
    }
}
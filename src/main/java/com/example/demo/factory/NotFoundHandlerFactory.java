package com.example.demo.factory;

import com.example.demo.handler.NotFoundHandler;
import com.example.demo.handler.ResponseHandler;

public class NotFoundHandlerFactory implements ErrorHandlerFactory {
    @Override
    public ResponseHandler createHandler() {
        return new NotFoundHandler();
    }
}

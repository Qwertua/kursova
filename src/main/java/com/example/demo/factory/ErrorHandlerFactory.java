package com.example.demo.factory;

import com.example.demo.handler.ResponseHandler;

public interface ErrorHandlerFactory {
    ResponseHandler createHandler();
}
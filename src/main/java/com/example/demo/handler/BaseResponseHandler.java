package com.example.demo.handler;

public abstract class BaseResponseHandler implements ResponseHandler {
    private ResponseHandler nextHandler;

    @Override
    public void setNextHandler(ResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected boolean callNextHandler(int statusCode) {
        if (nextHandler != null) {
            return nextHandler.handleResponse(statusCode);
        }
        return false;
    }
}
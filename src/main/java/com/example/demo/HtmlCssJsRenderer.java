package com.example.demo;

public class HtmlCssJsRenderer extends ContentRendererTemplate {

    @Override
    protected void loadHTML() {
        // Логіка для завантаження HTML
        System.out.println("HTML");
    }

    @Override
    protected void applyCSS() {
        // Логіка для застосування CSS
        System.out.println("CSS");
    }

    @Override
    protected void executeJavaScript() {
        // Логіка для виконання JavaScript
        System.out.println("Execute JS");
    }
}
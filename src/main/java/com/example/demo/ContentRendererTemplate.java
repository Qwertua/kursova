package com.example.demo;

public abstract class ContentRendererTemplate {
    // Цей метод визначає скелет алгоритму
    public final void renderContent() {
        loadHTML();
        applyCSS();
        executeJavaScript();
    }

    // Абстрактні методи, які будуть реалізовані в конкретних підкласах
    protected abstract void loadHTML();

    protected abstract void applyCSS();

    protected abstract void executeJavaScript();

}

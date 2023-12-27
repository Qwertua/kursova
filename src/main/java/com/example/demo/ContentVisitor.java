package com.example.demo;

/**
 * Інтерфейс Visitor для обробки різних типів веб-контенту.
 */
public interface ContentVisitor {

    /**
     * Відвідує та обробляє HTML контент.
     *
     * @param htmlContent HTML контент для обробки.
     */
    void visitHtmlContent(String htmlContent);

    /**
     * Відвідує та обробляє JavaScript контент.
     *
     * @param jsContent JavaScript контент для обробки.
     */
    void visitJsContent(String jsContent);
}
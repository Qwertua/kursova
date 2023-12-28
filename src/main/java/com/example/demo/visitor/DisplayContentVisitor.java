package com.example.demo.visitor;

import com.example.demo.visitor.ContentVisitor;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;

/**
 * Відвідувач для відображення HTML і JavaScript контенту.
 * Використовується для відображення контенту в WebEngine.
 */
public class DisplayContentVisitor implements ContentVisitor {
    private WebEngine engine;

    /**
     * Конструктор для DisplayContentVisitor.
     *
     * @param engine WebEngine для відображення контенту.
     */
    public DisplayContentVisitor(WebEngine engine) {
        this.engine = engine;
    }

    /**
     * Відображає HTML контент у WebEngine.
     *
     * @param htmlContent HTML контент для відображення.
     */
    @Override
    public void visitHtmlContent(String htmlContent) {
        String formattedHtml = "<html><body><pre>" + escapeHtml(htmlContent) + "</pre></body></html>";
        Platform.runLater(() -> engine.loadContent(formattedHtml));
    }

    /**
     * Відображає JavaScript контент у WebEngine.
     *
     * @param jsContent JavaScript контент для відображення.
     */
    @Override
    public void visitJsContent(String jsContent) {
        String formattedJs = "<html><body><pre>" + escapeHtml(jsContent) + "</pre></body></html>";
        Platform.runLater(() -> engine.loadContent(formattedJs));
    }

    /**
     * Допоміжний метод для екранування HTML.
     *
     * @param html Необроблений HTML контент.
     * @return Екранований HTML контент.
     */
    private String escapeHtml(String html) {
        return html
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}

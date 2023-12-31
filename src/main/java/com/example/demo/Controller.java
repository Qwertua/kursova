package com.example.demo;

import com.example.demo.factory.BadGatewayHandlerFactory;
import com.example.demo.factory.ErrorHandlerFactory;
import com.example.demo.factory.NotFoundHandlerFactory;
import com.example.demo.factory.ServiceUnavailableHandlerFactory;
import com.example.demo.loader.PageLoader;
import com.example.demo.loader.ProxyPageLoader;
import com.example.demo.loader.RealPageLoader;
import com.example.demo.visitor.ContentVisitor;
import com.example.demo.visitor.DisplayContentVisitor;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button forwardButton;
    @FXML
    private Button backButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private WebView webView;
    @FXML
    private TextField textField;
    @FXML
    private Button displayHtmlButton;
    @FXML
    private Button executeJsButton;
    @FXML
    private Button closeButton;


    private PageLoader pageLoader;
    private WebEngine engine;
    private WebHistory history;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webView.getEngine();
        RealPageLoader realPageLoader = new RealPageLoader(engine);
        List<ErrorHandlerFactory> errorHandlerFactories = new ArrayList<>();
        errorHandlerFactories.add(new NotFoundHandlerFactory());
        errorHandlerFactories.add(new BadGatewayHandlerFactory());
        errorHandlerFactories.add(new ServiceUnavailableHandlerFactory());

        pageLoader = new ProxyPageLoader(realPageLoader, progressBar, errorHandlerFactories);
        textField.setText("www.google.com");
        displayHtmlButton.setOnAction(event -> displayHtmlStructureOnPage());
        executeJsButton.setOnAction(event -> executeJavaScript());

        loadPage();
        closeButton.setDisable(true);
    }


    public void loadPage() {
        pageLoader.loadPage(textField.getText());

        pageLoader.getLoadWorkerStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                history = engine.getHistory();
                UpdateButtonStatus();

            }
        });
    }


    public void refreshPage() {
        pageLoader.reloadPage();
    }

    public void zoomIn() {
        webView.setZoom(webView.getZoom() + 0.25);
    }

    public void zoomOut() {
        webView.setZoom(webView.getZoom() - 0.25);
    }

    public void displayHistory() {
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        for (WebHistory.Entry entry : entries) {
            System.out.println(entry.getUrl() + " " + entry.getLastVisitedDate());
        }
    }

    public void goBack() {

        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        int index = history.getCurrentIndex();
        if (index > 0) {
            history.go(-1);
            textField.setText(entries.get(history.getCurrentIndex()).getUrl());
        }
        UpdateButtonStatus();

    }

    public void goForward() {

        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        int index = history.getCurrentIndex();
        if (index < entries.size() - 1) {
            history.go(1);
            textField.setText(entries.get(history.getCurrentIndex()).getUrl());
        }
        UpdateButtonStatus();

    }

    public void UpdateButtonStatus() {
        int currentIndex = history.getCurrentIndex();
        int totalEntries = history.getEntries().size();

        backButton.setDisable(currentIndex <= 0);
        forwardButton.setDisable(currentIndex >= totalEntries - 1);
    }

    /**
     * Відображає структуру HTML поточної веб-сторінки.
     * Отримує повний HTML поточної сторінки та відображає його в WebEngine.
     */
    public void displayHtmlStructureOnPage() {
        String htmlContent = (String) engine.executeScript("document.documentElement.outerHTML");
        ContentVisitor visitor = new DisplayContentVisitor(engine);
        visitor.visitHtmlContent(htmlContent);
        closeButton.setDisable(false);
    }

 /*   private String escapeHtml(String html) {
        return html
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }*/

    /**
     * Виконує JavaScript на поточній веб-сторінці та відображає вихідні дані.
     * Збирає та відображає зміст усіх тегів <script> на поточній сторінці.
     */
    public void executeJavaScript() {
        String script = """
            var scriptElements = document.getElementsByTagName('script');
            var jsContent = '';
            for (var i = 0; i < scriptElements.length; i++) {
                jsContent += scriptElements[i].outerHTML + '\\n\\n';
            }
            jsContent;
            """;
        String jsContent = (String) engine.executeScript(script);
        ContentVisitor visitor = new DisplayContentVisitor(engine);
        visitor.visitJsContent(jsContent);
        closeButton.setDisable(false);
    }
    public void closePageView() {
        closeButton.setDisable(true);
        // Додайте код для перезавантаження сторінки тут
        loadPage();


    }
}

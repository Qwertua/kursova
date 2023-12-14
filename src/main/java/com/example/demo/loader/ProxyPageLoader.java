package com.example.demo.loader;
import com.example.demo.factory.ErrorHandlerFactory;
import com.example.demo.handler.ResponseHandler;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Worker;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.WebEngine;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProxyPageLoader implements PageLoader {
    private RealPageLoader realPageLoader;
    private ProgressBar progressBar;
    private ResponseHandler errorHandlerChain;

    public ProxyPageLoader(RealPageLoader realPageLoader, ProgressBar progressBar, List<ErrorHandlerFactory> errorHandlerFactories) {
        this.realPageLoader = realPageLoader;
        this.progressBar = progressBar;
        this.errorHandlerChain = buildErrorHandlerChain(errorHandlerFactories);
    }

    private ResponseHandler buildErrorHandlerChain(List<ErrorHandlerFactory> errorHandlerFactories) {
        ResponseHandler firstHandler = null;
        ResponseHandler previousHandler = null;

        for (ErrorHandlerFactory factory : errorHandlerFactories) {
            ResponseHandler currentHandler = factory.createHandler();

            if (previousHandler != null) {
                previousHandler.setNextHandler(currentHandler);
            } else {
                firstHandler = currentHandler;
            }

            previousHandler = currentHandler;
        }

        return firstHandler;
    }

    private String generateErrorPageContent(int statusCode, String errorMessage) {
        return String.format("<html><body><h1>Error %d: %s</h1></body></html>", statusCode, errorMessage);
    }

    private void showErrorResponsePage(int statusCode, String errorMessage) {
        Platform.runLater(() -> {
            WebEngine errorEngine = realPageLoader.getEngine();

            String content = generateErrorPageContent(statusCode, errorMessage);
            errorEngine.loadContent(content);
        });
    }

    @Override
    public void loadPage(String url) {
        /*Встановлюємо видимою шкалу завантаження сторінки*/
        progressBar.setVisible(true);
        /*завантаження сторінки  за допомогою realPageLoader*/
        realPageLoader.loadPage(url);
        /*відстежуємо зміну стану за допомогою слухача подій*/
        realPageLoader.getLoadWorkerStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                /*коли стан завантаження успішний прибираємо шкалу завантаження сторінки та викликаємо метод handleresponse*/
                progressBar.setVisible(false);
                handleResponse(realPageLoader.getEngine().getLocation());
            }
        });
    }

    @Override
    public void reloadPage() {
        /*Встановлюємо видимою шкалу завантаження сторінки*/
        progressBar.setVisible(true);
        /*перезавантаження сторінки  за допомогою realPageLoader*/
        realPageLoader.reloadPage();
        /*відстежуємо зміну стану за допомогою слухача подій*/
        realPageLoader.getLoadWorkerStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                /*коли стан завантаження успішний прибираємо шкалу завантаження сторінки та викликаємо метод handleresponse*/
                progressBar.setVisible(false);
                handleResponse(realPageLoader.getEngine().getLocation());
            }
        });
    }

    @Override
    /*Повертає властивість стану завантаження з RealPageLoader.*/
    public ReadOnlyObjectProperty<Worker.State> getLoadWorkerStateProperty() {
        return realPageLoader.getLoadWorkerStateProperty();
    }

    private void handleResponse(String url) {
        try {
            if (url == null || url.trim().isEmpty()) {
                return;
            }

            WebEngine engine = realPageLoader.getEngine();
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            int statusCode = connection.getResponseCode();

            // Виберіть коди статусу, для яких потрібно відобразити сторінку помилок
            if (statusCode == 404 || statusCode == 502 || statusCode == 503) {
                showErrorResponsePage(statusCode, connection.getResponseMessage());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
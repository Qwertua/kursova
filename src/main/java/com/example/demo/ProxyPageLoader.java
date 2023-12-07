package com.example.demo;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.WebEngine;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProxyPageLoader implements PageLoader {
    private RealPageLoader realPageLoader;
    private ProgressBar progressBar;
    private List<ResponseHandler> responseHandlers;

    public ProxyPageLoader(RealPageLoader realPageLoader, ProgressBar progressBar) {
        this.realPageLoader = realPageLoader;
        this.progressBar = progressBar;
        this.responseHandlers = new ArrayList<>();
        initializeHandlers();
    }

    private void initializeHandlers() {
        // Створюємо обробники
        NotFoundHandler notFoundHandler = new NotFoundHandler();
        BadGatewayHandler badGatewayHandler = new BadGatewayHandler();
        ServiceUnavailableHandler serviceUnavailableHandler = new ServiceUnavailableHandler();

        // Встановлюємо посилання на наступний обробник у ланцюгу
        notFoundHandler.setNextHandler(badGatewayHandler);
        badGatewayHandler.setNextHandler(serviceUnavailableHandler);

        // Додаємо перший обробник у ланцюг
        responseHandlers.add(notFoundHandler);
    }

    @Override
    public void loadPage(String url) {
        progressBar.setVisible(true);
        realPageLoader.loadPage(url);

        realPageLoader.getLoadWorkerStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                progressBar.setVisible(false);
                handleResponse(realPageLoader.getEngine().getLocation());
            }
        });
    }

    @Override
    public void reloadPage() {
        progressBar.setVisible(true);
        realPageLoader.reloadPage();

        realPageLoader.getLoadWorkerStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                progressBar.setVisible(false);
                handleResponse(realPageLoader.getEngine().getLocation());
            }
        });
    }

    @Override
    public ReadOnlyObjectProperty<Worker.State> getLoadWorkerStateProperty() {
        return realPageLoader.getLoadWorkerStateProperty();
    }

    private void handleResponse(String url) {
        try {
            WebEngine engine = realPageLoader.getEngine();
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            int statusCode = connection.getResponseCode();

            for (ResponseHandler handler : responseHandlers) {
                if (handler.handleResponse(statusCode)) {
                    break;
                }
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
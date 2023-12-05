package com.example.demo;

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

    private PageLoader pageLoader;
    private WebEngine engine;
    private WebHistory history;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webView.getEngine();
        RealPageLoader realPageLoader = new RealPageLoader(engine);
        pageLoader = new ProxyPageLoader(realPageLoader, progressBar);
        textField.setText("www.google.com");

        loadPage();
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
}
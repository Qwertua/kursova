package com.example.demo;

import javafx.collections.ObservableList;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private WebView webView;
    @FXML
    private TextField textField;
    private WebEngine engine;
    private String homePage;
    private double webZoom;
    private WebHistory history ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homePage = "www.google.com";
        engine = webView.getEngine();
        textField.setText(homePage);

        webZoom = 1;
        backButton.setDisable(true);
        forwardButton.setDisable(true);



        loadPage();
    }

    public void loadPage() {
        engine.load("http://" + textField.getText());
        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Page has been loaded, now get the history
                history = engine.getHistory();
                System.out.println(history.getCurrentIndex());
                UpdateButtonStatus();
            }
        });
    }

    public void refreshPage() {
        engine.reload();
    }

    public void zoomIn() {
        webView.setZoom(webZoom);
        webZoom += 0.25;
    }

    public void zoomOut() {
        webView.setZoom(webZoom);
        webZoom -= 0.25;
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
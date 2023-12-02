package com.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;



public class Controller implements Initializable {
    @FXML
    private WebView webView;
    @FXML
    private TextField textField;
    private WebEngine engine;
    private String homePage;
    private double webZoom;
    private WebHistory history;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homePage = "www.google.com";
        engine = webView.getEngine();
        textField.setText(homePage);
        webZoom = 1;

        loadPage();



    }
    public void loadPage(){
        /*engine.load("http://www.google.com");*/
        engine.load("http://" + textField.getText());

    }
    public void refreshPage(){
        engine.reload();
    }
    public void zoomIn(){
        webView.setZoom(webZoom);
        webZoom += 0.25;

    }
    public void zoomOut(){
        webView.setZoom(webZoom);
        webZoom -= 0.25;

    }
    public void displayHistory(){
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        for(WebHistory.Entry entry : entries){
            System.out.println(entry);
        }

    }


}
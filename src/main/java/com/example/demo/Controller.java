package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;



public class Controller implements Initializable {
    @FXML
    private WebView webView;
    @FXML
    private TextField textField;
    private WebEngine engine;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webView.getEngine();
        loadPage();

    }
    public void loadPage(){

    }

}
package com.example.demo;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.control.ProgressBar;

public class ProxyPageLoader implements PageLoader {
    private RealPageLoader realPageLoader;
    private ProgressBar progressBar;

    public ProxyPageLoader(RealPageLoader realPageLoader, ProgressBar progressBar) {
        this.realPageLoader = realPageLoader;
        this.progressBar = progressBar;
    }

    @Override
    public void loadPage(String url) {
        progressBar.setVisible(true);
        realPageLoader.loadPage(url);

        realPageLoader.getLoadWorkerStateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                progressBar.setVisible(false);
            }
        });
    }

    @Override
    public void reloadPage() {
        progressBar.setVisible(true);
        realPageLoader.reloadPage();

        realPageLoader.getLoadWorkerStateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    progressBar.setVisible(false);
                }
            }
        });
    }

    @Override
    public ReadOnlyObjectProperty<Worker.State> getLoadWorkerStateProperty() {
        return realPageLoader.getLoadWorkerStateProperty();
    }
}
package com.example.demo.loader;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;

public class RealPageLoader implements PageLoader {
    private WebEngine engine;

    public RealPageLoader(WebEngine engine) {
        this.engine = engine;
    }

    @Override
    public void loadPage(String url) {
        engine.load("http://" + url);
    }

    @Override
    public void reloadPage() {
        engine.reload();
    }

    @Override
    public ReadOnlyObjectProperty<Worker.State> getLoadWorkerStateProperty() {
        return engine.getLoadWorker().stateProperty();
    }

    public WebEngine getEngine() {
        return engine;
    }
}
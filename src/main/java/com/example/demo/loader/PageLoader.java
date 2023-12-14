package com.example.demo.loader;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Worker;

public interface PageLoader {
    void loadPage(String url);
    void reloadPage();
    ReadOnlyObjectProperty<Worker.State> getLoadWorkerStateProperty();
}

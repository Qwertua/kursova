package com.example.demo.loader;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;

public class RealPageLoader implements PageLoader {
    private WebEngine engine;

    public RealPageLoader(WebEngine engine) {
        this.engine = engine;
    }
    /*Реалізація методу loadPage, який використовує внутрішні методи класу WebEngine
    для завантаження веб-сторінки за вказаним URL.
    */
    @Override
    public void loadPage(String url) {
        engine.load("http://" + url);
    }
    /*Реалізація методу reloadPage, який використовує внутрішні методи класу WebEngine
    для перезавантаження вже завантаженої сторінки.
    */
    @Override
    public void reloadPage() {
        engine.reload();
    }
    /*Реалізація методу getLoadWorkerStateProperty, який повертає
    властивість стану робочого потоку для відстеження стану завантаження сторінки.
    */

    @Override
    public ReadOnlyObjectProperty<Worker.State> getLoadWorkerStateProperty() {
        return engine.getLoadWorker().stateProperty();
    }
    /*отримання доступу до внутрішніх методів WebEngine*/
    public WebEngine getEngine() {
        return engine;
    }
}
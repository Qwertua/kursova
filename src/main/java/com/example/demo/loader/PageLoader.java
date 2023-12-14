package com.example.demo.loader;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Worker;

public interface PageLoader {
    /* Завантаження сторінки з вказаним URL*/
    void loadPage(String url);
    /* метод для перезавантаження сторінки*/
    void reloadPage();

    /*Цей метод , який відображає стан робочого потоку, використовуваного для завантаження сторінки.
    В нащому випадку дозволяє спостерігати за змінами стану робочого потоку для визначення
    завершення завантаження сторінки.
    */
    ReadOnlyObjectProperty<Worker.State> getLoadWorkerStateProperty();
}

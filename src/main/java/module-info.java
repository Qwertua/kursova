module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.web;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.loader;
    opens com.example.demo.loader to javafx.fxml;
    exports com.example.demo.handler;
    opens com.example.demo.handler to javafx.fxml;
}
module com.example.karlaconk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.karlaconk to javafx.fxml;
    exports com.example.karlaconk;
    exports com.example.karlaconk.modules;
    opens com.example.karlaconk.modules to javafx.fxml;
    exports com.example.karlaconk.controllers;
    opens com.example.karlaconk.controllers to javafx.fxml;
}
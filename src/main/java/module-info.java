module com.example.hhappstructuretest {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires jdk.compiler;
    requires java.desktop;

    opens com.zhaw.hhapp to javafx.fxml;
    exports com.zhaw.hhapp;
    exports com.zhaw.hhapp.model;
    opens com.zhaw.hhapp.model to javafx.fxml;
    exports com.zhaw.hhapp.controller;
    opens com.zhaw.hhapp.controller to javafx.fxml;
    exports com.zhaw.hhapp.service;
    opens com.zhaw.hhapp.service to javafx.fxml;
    exports com.zhaw.hhapp.manager;
    opens com.zhaw.hhapp.manager to javafx.fxml;
}
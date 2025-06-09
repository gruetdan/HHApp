module com.example.hhapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires jdk.compiler;
    requires java.desktop;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.web;
    requires spring.webmvc;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires java.sql;

    // Spring braucht Zugriff auf diese Packages:
    opens com.zhaw.hhapp to spring.core, spring.beans, spring.context, spring.boot, spring.boot.autoconfigure;
    opens com.zhaw.hhapp.model to spring.core, spring.beans, spring.context, javafx.fxml, org.hibernate.orm.deprecation;
    opens com.zhaw.hhapp.controller to javafx.fxml;
    opens com.zhaw.hhapp.service to javafx.fxml;
    opens com.zhaw.hhapp.manager to javafx.fxml;

    exports com.zhaw.hhapp;
    exports com.zhaw.hhapp.model;
    exports com.zhaw.hhapp.controller;
    exports com.zhaw.hhapp.service;
    exports com.zhaw.hhapp.manager;
    exports com.zhaw.hhapp.dataLoader;
    opens com.zhaw.hhapp.dataLoader to spring.beans, spring.boot, spring.boot.autoconfigure, spring.context, spring.core;

}
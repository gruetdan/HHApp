module com.example.hhappstructuretest {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires jdk.compiler;

    opens com.zhaw.hhapp to javafx.fxml;
    exports com.zhaw.hhapp;
}
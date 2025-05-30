package com.zhaw.hhapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import static javafx.application.Application.launch;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ExpensesView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Overview ExpensesList");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fehler beim Laden der FXML-Datei!");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}

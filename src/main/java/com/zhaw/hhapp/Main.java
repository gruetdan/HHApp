package com.zhaw.hhapp;

import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.controller.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The entry point of the Household Hub application.
 * <p>
 * This class launches the JavaFX application and loads the initial ExpensesView GUI.
 * </p>
 */
public class Main extends Application {
    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {

        // Load the ExpensesView.fxml file to set up the main scene.
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ExpensesView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Overview ExpensesList");
            stage.setScene(scene);
            WindowManager.setMainStage(stage); // Saves window in static variable to be reused
            stage.show();
        } catch (IOException e) {
            // Print stack trace for debugging and show a user-friendly error message.
            e.printStackTrace();
            System.out.println("Error loading FXML-file!");
        }

    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        // Fill static map expensesList with the existing data-files
        ExpensesManager.load();

        // Start FXML
        launch(args);
    }
}










package com.zhaw.hhapp.manager;

import com.zhaw.hhapp.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Manager class responsible for displaying the detail view of a single ExpenseList.
 * <p>
 * This class creates and shows a new JavaFX window (stage) for managing a specific ExpenseList.
 * It loads the corresponding FXML view and sets the window title to the given list name.
 * </p>
 */
public class ExpenseManager {

    /** The FXMLLoader used to load the ExpenseView FXML. */
    //Note: can be moved to constructor as a local variable if not used otherwise
     FXMLLoader fxmlLoader;

    /**
     * Opens a new window (stage) to manage the specified ExpenseList.
     *
     * @param expenseListId The key/name of the particular ExpenseList to be managed.
     */
    public ExpenseManager(String expenseListId) {

        fxmlLoader = new FXMLLoader(Main.class.getResource("/com/zhaw/hhapp/ExpenseView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the fxml file!");
        }
        Stage stage = new Stage();
        stage.setTitle(expenseListId);
        stage.setScene(scene);
        stage.show();

    }

}

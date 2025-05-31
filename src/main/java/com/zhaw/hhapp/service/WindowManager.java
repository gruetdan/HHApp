package com.zhaw.hhapp.service;

import com.zhaw.hhapp.controller.ExpenseController;
import com.zhaw.hhapp.controller.ExpensesController;
import com.zhaw.hhapp.model.Expense;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Ensures that we have one single main Window (ExpensesView).
 * <p>
 * When we close the view of one expense List (ExpenseView) then we get back to the single main Window.
 * </p>
 */

public class WindowManager {

    private static Stage mainStage;
    private static ExpensesController mainController;

    /**
     * Sets the main Stage
     *
     * @param stage Main Stage of ExpensesView.
     */
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * Returns main stage of ExpensesView
     *
     * @return mainStage Main Stage of ExpensesView
     */
    public static Stage getMainStage() {
        return mainStage;
    }

    /**
     * Sets the Controller for ExpensesView
     *
     * @param controller Controller of ExpensesView.
     */
    public static void setMainController(ExpensesController controller) {
        mainController = controller;
    }

    /**
     * Returns controller of ExpensesView
     *
     * @return mainController Controller of ExpensesView
     */
    public static ExpensesController getMainController() {
        return mainController;
    }

}

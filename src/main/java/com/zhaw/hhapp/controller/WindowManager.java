package com.zhaw.hhapp.controller;

import javafx.stage.Stage;

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
     * @param stage Main Stage of ExpensesView.
     */
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * Sets the Controller for ExpensesView
     * @param controller Controller of ExpensesView.
     */
    public static void setMainController(ExpensesController controller) {
        mainController = controller;
    }

    /**
     * Returns controller of ExpensesView
     * @return mainController Controller of ExpensesView
     */
    public static ExpensesController getMainController() {
        return mainController;
    }

}

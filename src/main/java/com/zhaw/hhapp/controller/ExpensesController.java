package com.zhaw.hhapp.controller;

import com.zhaw.hhapp.manager.ExpenseManager;
import com.zhaw.hhapp.service.ExpensesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Controller for the ExpensesView.fxml, handling user interactions for managing multiple expense lists.
 * <p>
 * Connects UI elements (TextField, Button, ListView) to the underlying logic via ExpensesService.
 * Users can create or edit expense lists, and view all existing lists.
 * </p>
 */

public class ExpensesController {

    /**
     * Button to add or edit an expense list.
     */
    @FXML
    private Button addExpenseListButton;

    /**
     * TextField for entering the name of a new or existing expense list.
     */
    @FXML
    private TextField expenseListTextField;

    /**
     * ListView to display the names of all existing expense lists.
     */
    @FXML
    private ListView<String> expensesListView;

    /**
     * Service instance to handle business logic for expense lists.
     */
    private ExpensesService expensesService = new ExpensesService();

    /**
     * Handles the event when the user clicks the 'Add/Edit Expense List' button.
     * <p>
     * Retrieves the name from the TextField, validates and creates the expense list via the service.
     * If successful, opens the expense manager window for the new list; otherwise, displays an error.
     * </p>
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    void addExpenseList(ActionEvent event) {
        String listName = expenseListTextField.getText();

        // Use the service to validate and create the new list
        boolean created = expensesService.addExpenseList(listName);

        if (created) {
            // If successful: Open the ExpenseManager window for this list
            new ExpenseManager(listName);
            // Clear the input field as feedback
            expenseListTextField.clear();
        } else {
            // If not successful: Display an error message (TODO: show in UI)
            System.out.println("Error: Invalid name or list already exists!");
        }
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * <p>
     * Ensures the folder for expense lists exists, and updates the ListView with all current lists
     * when the scene is ready.
     * </p>
     */
    @FXML
    public void initialize() {
        // Ensure the folder for storing expense lists exists
        expensesService.createExpenseListsFolder();
        // Add a listener to update the ListView when the scene becomes available
        expenseListTextField.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                expensesService.updateListView(expensesService.listTxtFiles(), expensesListView, addExpenseListButton);
            }
        });
    }

}
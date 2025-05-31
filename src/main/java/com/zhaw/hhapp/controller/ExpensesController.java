package com.zhaw.hhapp.controller;

import com.zhaw.hhapp.manager.ExpenseManager;
import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.model.ExpenseList;
import com.zhaw.hhapp.service.ExpenseService;
import com.zhaw.hhapp.service.ExpensesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

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
     * Button to trigger the sum display for the selected expense list.
     */
    @FXML
    private Button showSumButton;

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
     * Handles the "Add/Edit Expense List" button click.
     * <p>
     * This method allows the user to open an existing expense list or create a new one if it does not yet exist.
     * If the list already exists (either in memory or as a file), it is loaded/imported and the detail view is opened.
     * If it does not exist, a new empty list is created and the detail view is opened.
     * <p>
     * If the input field is empty or a list cannot be created (invalid or duplicate name), an info dialog is shown.
     * </p>
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    void addExpenseList(ActionEvent event) {
        // Get the name from the input field, remove ".txt" if present, and trim whitespace
        String listName = expenseListTextField.getText().replaceFirst("\\.txt$", "").trim();

        if (listName.isEmpty()) {
            showInfoDialog("Please enter a name for the expense list.");
            return;
        }

        // Check if the list already exists in memory
        ExpenseList loadedList = expensesService.getExpenseList(listName);

        if (loadedList == null) {
            // Not in memory: Does a corresponding file exist?
            java.io.File listFile = new java.io.File(ExpensesManager.getDirectoryPath(), listName + ".txt");
            if (listFile.exists()) {
                // File exists: Import the list and register it in the manager/model
                new ExpenseService().importExpensesAndAddToList(listName);
                System.out.println("Imported existing list: " + listName);
            } else {
                // Truly new: create the list via the service
                boolean created = expensesService.addExpenseList(listName);
                if (!created) {
                    showInfoDialog("Error: Invalid name or list already exists!");
                    return;
                }
                System.out.println("Created new list: " + listName);
            }
        } else {
            // List is already in memory
            System.out.println("List exists in memory: " + listName);
        }

        // Open the ExpenseManager window for this list (either existing or newly created)
        new ExpenseManager(listName);
        // Clear the input field to provide UI feedback
        expenseListTextField.clear();
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
        //expensesListView.getItems().clear(); // Vorherige Daten löschen
        loadAllExpenseLists();
        // Add a listener to update the ListView when the scene becomes available
        expenseListTextField.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                expensesService.updateListView(expensesService.listTxtFiles(), expensesListView, addExpenseListButton);
            }
        });
        ExpensesService.setMainController(this); // Speichere den Controller
    }

    /**
     * Handles the "Show sum" button click.
     * <p>
     * Calculates and displays the total sum of all expenses in the selected expense list.
     * Opens a dialog window with the result.
     * </p>
     */
    @FXML
    private void handleShowSum() {
        // Get the selected list name from the ListView
        String selectedListName = expensesListView.getSelectionModel().getSelectedItem();

        if (selectedListName == null) {
            showInfoDialog("Please select an expense list first.");
            return;
        }

        // Get the ExpenseList using the service
        ExpenseList expenseList = expensesService.getExpenseList(selectedListName);

        if (expenseList == null) {
            showInfoDialog("Selected list not found.");
            return;
        }

        // Calculate the sum of all expenses in the list
        double sum = expenseList.getExpenses().stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        // Show the sum in a dialog window
        showSumDialog(selectedListName, sum);
    }
    /**
     * Displays the sum dialog window for a specific expense list.
     *
     * @param listName The name of the expense list.
     * @param sum      The calculated sum to display.
     */
    public static void showSumDialog(String listName, double sum) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sum of expenses");
        alert.setHeaderText("List: " + listName);
        alert.setContentText(String.format("Total sum of expenses: %.2f CHF", sum));
        alert.showAndWait();
    }

    /**
     * Shows an information dialog to the user.
     *
     * @param message The message to display.
     */
    public static void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Loads all existing expense lists from file into the model at startup.
     * This ensures all lists are available for operations like summing, even if not yet edited.
     */
    private void loadAllExpenseLists() {
        expensesListView.getItems().clear(); // Vorherige Daten löschen
        // Hole alle Dateinamen mit .txt
        ArrayList<String> listNames = expensesService.listTxtFiles();

        for (String fileName : listNames) {
            // Entferne .txt für den internen Listennamen, falls nötig!
            String name = fileName.replaceFirst("\\.txt$", "");
            // Versuche, die Liste zu importieren und im Manager/Model zu registrieren:
            // Nutze deinen Import-Mechanismus (z.B. ExpenseService/ExpensesManager)
            // Beispiel:
            ExpenseList loadedList = new ExpenseService().importExpensesAndAddToList(name);
            // Jetzt ist sie im ExpensesManager gespeichert
        }
        // Jetzt die ListView explizit aktualisieren
        expensesListView.getItems().addAll(listNames);
        expensesListView.refresh(); // GUI zwingend neu rendern
    }


}
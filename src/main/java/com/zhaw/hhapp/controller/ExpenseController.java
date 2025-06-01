package com.zhaw.hhapp.controller;

import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.ExpenseList;
import com.zhaw.hhapp.service.ExpenseService;
import com.zhaw.hhapp.model.Expense;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Double.parseDouble;

/**
 * Controller for the ExpenseView.fxml.
 * <p>
 * Handles user interactions and delegates logic to {@link ExpenseService}.
 * Manages input fields, list view, and import/export operations for a single ExpenseList.
 * </p>
 */
public class ExpenseController {

    /**
     * Text fields for expense entry (amount, description, date, user).
     */
    @FXML
    private TextField amountField, descriptionField, dateField, userField;

    /**
     * Buttons for export, add, and import actions.
     */
    @FXML
    private Button exportToTXT, addExpenseButton, importFromTxt;

    /**
     * ListView for displaying all expenses in the current list.
     */
    @FXML
    private ListView<String> expenseListView;

    /**
     * Label for displaying error messages to the user.
     */
    @FXML
    private Label errorLabel;

    /**
     * Service class for business logic and persistence operations.
     */
    private ExpenseService expenseService = new ExpenseService();

    /**
     * Initializes the controller, sets default field values,
     * imports existing expenses if the list exists, and sets up export on window close.
     */
    @FXML
    public void initialize() {
        // debug:
        // System.out.println("[Controller] Initializing with windowTitle: " + getWindowTitle());
        resetFields();
        Platform.runLater(() -> {
            importExpenses();
            if (exportToTXT.getScene() != null) {
                Stage stage = (Stage) exportToTXT.getScene().getWindow();
                stage.setOnCloseRequest(event -> {
                            exportCurrentList();
                            //Go back to previously saved window/controller of ExpensesList
                            ExpensesController mainController = WindowManager.getMainController();
                            if (mainController != null) {
                                //System.out.println("Reinitializing MainController...");
                                mainController.initialize();
                            } else {
                                System.err.println("MainController not found!");
                            }
                        }
                );
            } else {
                System.err.println("[Controller] Scene not loaded.");
            }
        });
    }

    /**
     * Resets all input fields to default values.
     */
    private void resetFields() {
        amountField.setText("");
        descriptionField.setText("");
        dateField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        userField.setText(System.getProperty("user.name"));
    }

    /**
     * Handles the event when the "Add Expense" button is clicked.
     * Validates input, adds the expense to the list, and updates the view.
     */
    @FXML
    public void handleAddExpense() {
        try {
            double amount = parseDouble(amountField.getText());
            String description = descriptionField.getText();
            String date = dateField.getText();
            String user = userField.getText();
            Expense expense = new Expense(amount, description, date, user);

            // Validation logic in the service layer
            String validationError = expenseService.validateExpense(expense);
            if (validationError != null) {
                showErrorDialog(validationError);
                return;
            }

            String title = getWindowTitle();
            expenseService.addExpense(title, expense);
            ExpenseList updatedList = expenseService.getExpenseList(title);
            updateExpenseListView(updatedList, expenseListView);

            resetFields();

        } catch (NumberFormatException e) {
            showErrorDialog("Invalid amount!");
        }
    }

    /**
     * Updates the expenseListView with the given list of expenses.
     *
     * @param expenseList The ExpenseList to display.
     * @param listView    The ListView to update.
     */
    private void updateExpenseListView(ExpenseList expenseList, ListView<String> listView) {
        listView.getItems().clear();
        for (Expense expense : expenseList.getExpenses()) {
            listView.getItems().add(expense.toString());
        }
    }

    /**
     * Handles export action to save the current expense list as a TXT file.
     *
     * @param event Mouse event from the export button.
     */
    @FXML
    void exportToTxt(MouseEvent event) {
        String title = getWindowTitle();
        expenseService.exportExpensesToTxt(title);
    }

    /**
     * Imports existing expenses from file into the current list and updates the view.
     */
    private void importExpenses() {
        try {
            String title = getWindowTitle();
            updateExpenseListView(ExpensesManager.expensesList.getExpenseList(title), expenseListView);
            expenseListView.refresh();
        } catch (Exception e) {
            System.out.println("[Controller] No list yet: " + ExpensesManager.getDirectoryPath());
        }
    }

    /**
     * Returns the window title, which is used as the expense list identifier.
     *
     * @return The title of the current window or "Unknown list" if not set.
     */
    private String getWindowTitle() {
        if (exportToTXT.getScene() != null) {
            String title = ((Stage) exportToTXT.getScene().getWindow()).getTitle();
            return title.replaceFirst("\\.txt$", "");  // Suffix immer entfernen!
        } else {
            System.err.println("Error: Scene is not loaded yet.");
            return "Unknown list";
        }
    }

    /**
     * Shows an error dialog to the user.
     *
     * @param message The error message to display.
     */
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null); // No header, just message
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Exports the current expense list when the window is closed, if the list is not empty.
     */
    private void exportCurrentList() {
        String listName = getWindowTitle();
        ExpenseList list = expenseService.getExpenseList(listName);
        if (list != null && !list.getExpenses().isEmpty())  {
            try {
                expenseService.exportExpensesToTxt(listName);
            } catch (Exception e) {
                showErrorDialog("Export failed!");
            }
        }
    }

}

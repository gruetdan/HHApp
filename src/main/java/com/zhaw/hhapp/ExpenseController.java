package com.zhaw.hhapp;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import static java.lang.Double.parseDouble;

public class ExpenseController {

    /**
     * Nimmt Werte entgegen und gibt an Manager weiter
     */

    @FXML
    private TextField amountField, descriptionField, dateField, userField;

    @FXML
    private Button exportToTXT, addExpenseButton, importFromTxt;

    @FXML
    private ListView<String> expenseListView;

    @FXML private Label errorLabel; // Für User-Feedback

    private ExpenseService expenseService = new ExpenseService();

    @FXML
    public void initialize() {
        resetFields();
    }


    private void resetFields() {
        amountField.setText("0");
        descriptionField.setText("");
        dateField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        userField.setText(System.getProperty("user.name"));
        showError("");
    }

    @FXML
    public void handleAddExpense() {
        try {
            double amount = parseDouble(amountField.getText());
            String description = descriptionField.getText();
            String date = dateField.getText();
            String user = userField.getText();
            Expense expense = new Expense(amount, description, date, user);

            String title = getWindowTitle();

            expenseService.addExpense(title, expense);

            ExpenseList updatedList = expenseService.getExpenseList(title);
            updateExpenseListView(updatedList);

            // Felder zurücksetzen:
            amountField.clear();
            descriptionField.clear();
            dateField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            userField.setText(System.getProperty("user.name"));
            // Fehlerlabel, falls vorhanden, zurücksetzen

        } catch (NumberFormatException e) {
            showErrorDialog("Ungültiger Betrag!");
        }
    }


   private void updateExpenseListView(ExpenseList expenseList) {
       expenseListView.getItems().clear();
       Stage stage = (Stage) exportToTXT.getScene().getWindow();
       String title = stage.getTitle();
       for (Expense expense : expenseList.getExpenses()) {
           expenseListView.getItems().add(expense.toString());
       }
   }


    @FXML
    void exportToTxt(MouseEvent event) {
        String title = getWindowTitle();
        expenseService.exportExpensesToTxt(title);
    }

    @FXML
    void importExpenses(MouseEvent event) {
        try {
            String title = getWindowTitle();
            ExpenseList expenseList = expenseService.importExpensesAndAddToList(title);
            updateExpenseListView(expenseList);
            expenseListView.refresh();
        } catch (Exception e) {
            showErrorDialog("Fehler beim Import: Stimmt der Listenname mit der Datei überein?");
        }
    }

    private void showError(String message) {
        if (errorLabel != null) errorLabel.setText(message);
    }

    private String getWindowTitle() {
        return ((Stage) exportToTXT.getScene().getWindow()).getTitle();
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Oder AlertType.INFORMATION für normale Meldungen
        alert.setTitle("Fehler");
        alert.setHeaderText(null); // Kein Header, nur die Nachricht
        alert.setContentText(message);
        alert.showAndWait();
    }
}

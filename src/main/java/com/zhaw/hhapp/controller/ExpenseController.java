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

    @FXML
    private Label errorLabel; // Für User-Feedback

    private ExpenseService expenseService = new ExpenseService();

    @FXML
    public void initialize() {
        resetFields();

        // Szene-Listener hinzufügen
        Platform.runLater(() -> {
            try {
                importExpenses();
            } catch (Exception e) {
                System.out.println("Sie arbeiten an einer neuen Liste. Diese gab es in folgendem Ordner bis anhin noch nicht: " + ExpensesManager.getDirectoryPath());
            }
            if (exportToTXT.getScene() != null) {
                Stage stage = (Stage) exportToTXT.getScene().getWindow();
                stage.setOnCloseRequest(event -> exportAktuelleListe());
            } else {
                System.err.println("Fehler: Scene wurde nicht geladen.");


            }
        });
    }


    //todo: Prüfen ob Listen-Titel mit bestehender Liste übereinstimmt. Falls ja, dann direkt importieren


    private void resetFields() {
        amountField.setText("0");
        descriptionField.setText("");
        dateField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        userField.setText(System.getProperty("user.name"));
    }

    @FXML
    public void handleAddExpense() {
        try {
            double amount = parseDouble(amountField.getText());
            String description = descriptionField.getText();
            String date = dateField.getText();
            String user = userField.getText();
            Expense expense = new Expense(amount, description, date, user);

            // Validierung im Service!
            String validationError = expenseService.validateExpense(expense);
            if (validationError != null) {
                showErrorDialog(validationError);
                return;
            }

            String title = getWindowTitle();

            expenseService.addExpense(title, expense);

            ExpenseList updatedList = expenseService.getExpenseList(title);
            updateExpenseListView(updatedList, expenseListView, exportToTXT);

            // Felder zurücksetzen:
            resetFields();


        } catch (NumberFormatException e) {
            showErrorDialog("Ungültiger Betrag!");
        }
    }


    private void updateExpenseListView(ExpenseList expenseList, ListView<String> ListView, Button refButton) {
        ListView.getItems().clear();
        Stage stage = (Stage) refButton.getScene().getWindow();
        String title = stage.getTitle();
        for (Expense expense : expenseList.getExpenses()) {
            ListView.getItems().add(expense.toString());
        }
    }


    @FXML
    void exportToTxt(MouseEvent event) {
        String title = getWindowTitle();
        expenseService.exportExpensesToTxt(title);
    }

    /*@FXML
    void importExpenses(MouseEvent event) {
        try {
            String title = getWindowTitle();
            ExpenseList expenseList = expenseService.importExpensesAndAddToList(title);
            updateExpenseListView(expenseList, expenseListView, exportToTXT);
            expenseListView.refresh();
        } catch (Exception e) {
            showErrorDialog("Fehler beim Import: Stimmt der Listenname mit der Datei überein?");
        }
    }*/

    private void importExpenses() {
        try {
            String title = getWindowTitle();
            ExpenseList expenseList = expenseService.importExpensesAndAddToList(title);
            updateExpenseListView(expenseList, expenseListView, exportToTXT);
            expenseListView.refresh();
        } catch (Exception e) {
            //showErrorDialog("Fehler beim Import: Stimmt der Listenname mit der Datei überein?");
        }
    }

    private String getWindowTitle() {
        if (exportToTXT.getScene() != null) {
            return ((Stage) exportToTXT.getScene().getWindow()).getTitle();
        } else {
            System.err.println("Fehler: Scene ist noch nicht geladen.");
            return "Unbekannte Liste";
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Oder AlertType.INFORMATION für normale Meldungen
        alert.setTitle("Fehler");
        alert.setHeaderText(null); // Kein Header, nur die Nachricht
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void exportAktuelleListe() {
        String listName = getWindowTitle();
        ExpenseList list = expenseService.getExpenseList(listName);
        if (list != null && !list.getExpenses().isEmpty()) {
            try {
                expenseService.exportExpensesToTxt(listName);
            } catch (Exception e) {
                showErrorDialog("Export fehlgeschlagen!");
            }
        }
    }

}

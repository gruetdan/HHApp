package com.zhaw.hhapp;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Double.parseDouble;

public class ExpenseController {


    /**
     * Nimmt Werte entgegen und gibt an Manager weiter
     */

    @FXML
    private TextField amountField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField userField;

    @FXML
    private ListView<String> expenseList;

    @FXML
    private Button exportToTXT;

    @FXML
    private Button importFromTxt;

    @FXML
    public void initialize() {
        amountField.setText("0");
        descriptionField.setText("");
        dateField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        userField.setText(System.getProperty("user.name"));
    }

    @FXML
    public Expense addExpense() {
        System.out.println("AddExpenseButton clicked");
        double amount = parseDouble(amountField.getText());
        String description = descriptionField.getText();
        String date = dateField.getText();
        String user = userField.getText();
        //try {

            //String ExpenseListId;

            //expenseManager.addExpense(amount, description, date, user);
            //updateExpenseList();

            // Felder leeren bzw. neu initialisieren
            amountField.clear();
            descriptionField.clear();
            dateField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            userField.setText(System.getProperty("user.name"));

        /*} catch (NumberFormatException e) {
            amountField.setText("Ungültiger Betrag!");
        }*/
        return new Expense(amount, description, date, user);
    }

    public void exportToTxt(MouseEvent mouseEvent) {
    }

    public void importExpenses(MouseEvent mouseEvent) {
    }
}

   /* private void updateExpenseList() {

        expenseList.getItems().clear();
        for (Expense expense : expenseManager.getExpenses()) {
            expenseList.getItems().add(expense.toString());
        }
    }
    @FXML
    void exportToTxt(MouseEvent event) {
        ExpenseExport.exportExpenses(expenseManager.getExpenses());
    }


    @FXML
    void importExpenses(MouseEvent event) {
        for(Expense expense: new ExpenseImport().importExpenses()){
            expenseManager.addExpense(expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserName());
            updateExpenseList();
        };
    }

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }
 */


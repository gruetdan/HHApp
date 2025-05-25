package com.zhaw.hhapp;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private ListView<String> expenseListView;

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
        double amount = parseDouble(amountField.getText());
        String description = descriptionField.getText();
        String date = dateField.getText();
        String user = userField.getText();
        try {
            Stage stage = (Stage) exportToTXT.getScene().getWindow();
            String title = stage.getTitle();
            Expense expense = new Expense(amount, description, date, user);
            ExpenseList expenseList = ExpensesManager.getExpenseList(title);
            expenseList.addExpense(expense);
            ExpensesManager.overwriteExpenseListValue(title, expenseList);
            updateExpenseListView(expenseList);
            // Felder leeren bzw. neu initialisieren
            amountField.clear();
            descriptionField.clear();
            dateField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            userField.setText(System.getProperty("user.name"));

        } catch (NumberFormatException e) {
            amountField.setText("Ungültiger Betrag!");
        }
        return new Expense(amount, description, date, user);
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
    //void exportToTxt(MouseEvent event) {
    void exportToTxt() {
        Stage stage = (Stage) exportToTXT.getScene().getWindow();
        String title = stage.getTitle();
        ExpenseExport.exportExpenses(title, ExpensesManager.getExpenseList(title).getExpenses());
    }



//
//    @FXML
//    void importExpenses(MouseEvent event) {
    @FXML
    void importExpenses() {

        try {
            Stage stage = (Stage) exportToTXT.getScene().getWindow();
            String title = stage.getTitle();


            ExpenseImport expenseImport = new ExpenseImport();
            List<Expense> expenseListImport = expenseImport.importExpenses(title);

            ExpenseList expenseList = new ExpenseList();
            try {
                expenseList = ExpensesManager.getExpenseList(title);
            } catch (Exception e) {
                ExpensesManager.addExpenseList(title);
            }

            for (Expense expense : expenseListImport) {
                expenseList.addExpense(expense);
            }
            ExpensesManager.overwriteExpenseListValue(title, expenseList);
            updateExpenseListView(expenseList);
            expenseListView.refresh();
        } catch (Exception e) {
            System.out.println("An error importing expenses has occured.");
            System.out.println("Did you make sure, that the name of the ExpenseList (look at the title),");
            System.out.println("is identical to the .txt-File you want to import?");
        }
    }

}

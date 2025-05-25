package com.zhaw.hhapp;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;

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
    private Button addExpenseButton;

    @FXML
    public Button importFromTxt;

    @FXML
    private ExpenseService expenseService = new ExpenseService();

    @FXML
    public void initialize() {
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

            String title = ((Stage) exportToTXT.getScene().getWindow()).getTitle();

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
            amountField.setText("Ungültiger Betrag!");
        }
    }


   private void updateExpenseListView(ExpenseList expenseList) {
       expenseListView.getItems().clear();
      // Stage stage = (Stage) exportToTXT.getScene().getWindow();
       //String title = stage.getTitle();
       for (Expense expense : expenseList.getExpenses()) {
           expenseListView.getItems().add(expense.toString());
       }
   }


    @FXML
    void exportToTxt(MouseEvent event) {
        String title = ((Stage) exportToTXT.getScene().getWindow()).getTitle();
        expenseService.exportExpensesToTxt(title);
    }

    @FXML
    void importExpenses(MouseEvent event) {
        try {
            String title = ((Stage) exportToTXT.getScene().getWindow()).getTitle();
            ExpenseList expenseList = expenseService.importExpensesAndAddToList(title);
            updateExpenseListView(expenseList);
            expenseListView.refresh();
        } catch (Exception e) {
            System.out.println("An error importing expenses has occurred.");
            System.out.println("Did you make sure, that the name of the ExpenseList (look at the title),");
            System.out.println("is identical to the .txt-File you want to import?");
        }
    }
}

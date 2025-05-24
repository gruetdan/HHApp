package com.zhaw.hhapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ExpenseController {
    /**
     * Nimmt Werte entgegen und gibt an Manager weiter
     */
    static double counter = 1;
    @FXML
    private Button expenseButton;

    @FXML
    private Label welcomeText;

    @FXML
    void expenseButtonClick(ActionEvent event) {

        System.out.println("Button clicked - from UiExpenseHandler");
        Expense expense = new Expense("Expense",counter);
        //expenseList.addExpense(expense);
        expense.print();
    counter++;
    }
}
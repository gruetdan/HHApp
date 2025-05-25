package com.zhaw.hhapp;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ExpensesController {
    /**
     * Nimmt Werte entgegen und gibt an Manager weiter
     */

    @FXML
    private Button AddExpenseListButton;

    @FXML
    private TextField ExpenseListTextField;

    @FXML
    void addExpenseList(ActionEvent event) {
        new ExpenseManager(ExpenseListTextField.getText());
        ExpensesManager.addExpenseList(ExpenseListTextField.getText());

    }
}

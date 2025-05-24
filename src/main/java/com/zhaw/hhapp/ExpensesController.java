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
        System.out.println("AddExpenseListButton clicked");
        String expenseListID = ExpenseListTextField.getText(); // todo:
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/zhaw/hhapp/ExpenseView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("HH-App-Structure-test!");
        stage.setScene(scene);
        stage.show();
    }
}

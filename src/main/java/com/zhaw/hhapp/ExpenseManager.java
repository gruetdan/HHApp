package com.zhaw.hhapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExpenseManager {
    /**
     * Saves Expenses in a List given by ExpensesManager
     * @param expenseList Contains Expenses
     */
    private ExpenseList expenseList;
    private String expenseListId;
    private Expense expense;
    FXMLLoader fxmlLoader;

    public ExpenseManager(String expenseListId) {
        /*
        todo Structure:
        1) new ExpenseList (todo later: new or load)
        2) get User Inputs (in class ExpenseController)
        3) show the user what the ExpenseList looks like after each button click (in class ExpenseController)
        4) fill ExpenseList with user Inputs
        5) Save Key-Value (ID-ExpenseList) in ExpensesList (or update the List at each Button Click)
         */

        // Step 1)
        this.expenseList = new ExpenseList();
        //ExpensesManager.addExpenseList(expenseListId);

        //Step 2)

        fxmlLoader = new FXMLLoader(Main.class.getResource("/com/zhaw/hhapp/ExpenseView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        }catch (IOException e) {
                e.printStackTrace();
                System.out.println("Fehler beim Laden der FXML-Datei!");
            }
        Stage stage = new Stage();
        stage.setTitle(expenseListId);
        stage.setScene(scene);
        stage.show();
        System.out.println("show Scene hat geklappt");

        // Step 4)
        //Platform.runLater(() -> getUserInput());
        //getUserInput();
        //ExpensesManager.addExpenseList(expenseListId, getUserInput());
    }

/*
    public void getUserInput(){
        // Controller aus FXMLLoader holen
        System.out.println("getUserInput startet");
        ExpenseController controller = fxmlLoader.getController();
        if (controller != null) {
            expense = controller.addExpense();
            System.out.println(expense.toString()); // todo WHY DOES THIS NOT HAPPEN?
        } else {
            System.out.println("Fehler: Controller konnte nicht geladen werden!");
        }
    }
*/
    public ExpenseManager(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    public ExpenseList getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }


}

package com.zhaw.hhapp;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class ExpensesManager  {
    /**
     * Speichert unterschiedliche Expense-List
     * @param expenseLists Enthält unterschiedliche ExpenseList (z. B. Haushalt, Griechenland-Ferien, ...)
     */
    private ExpensesList expensesList;

    public ExpensesManager() {
        this.expensesList = new ExpensesList();
    }

    public ExpensesList getExpensesList() {
        return expensesList;
    }

    public void setExpensesList(ExpensesList expensesList) {
        this.expensesList = expensesList;
    }

    public void addExpenseList(String id) {
        expensesList.addExpenseList(id);
        System.out.println("ExpensesList was added - msg by ExpensesManager");
    }
}

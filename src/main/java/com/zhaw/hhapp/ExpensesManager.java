package com.zhaw.hhapp;

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
     * Saves different ExpenseList in ExpensesList
     * @param expenseLists Contains different ExpenseList (z. B. Haushalt, Griechenland-Ferien, ...)
     */
    public static ExpensesList expensesList = new ExpensesList();;

    public static ExpenseList getExpenseList(String id) {
        return expensesList.getExpenseList(id);
    }

    public static void addExpenseList(String id) {
        expensesList.addExpenseList(id);
        System.out.println("ExpensesList was added - msg by ExpensesManager");
    }
    public static void addExpenseList(String id, ExpenseList expense) {
        expensesList.addExpenseList(id, expense);
    }

    public static void overwriteexpenseListValue(String id, ExpenseList expense) {
        expensesList.put(id, expense);
    }
}

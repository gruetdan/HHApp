package com.zhaw.hhapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ExpenseManager extends ExpenseController {
    /**
     * Speichert einzelne Ausgaben in einer Liste
     * @param expenseList Enthält unterschiedliche Ausgaben
     */

    private ExpenseList expenseList;
    public ExpenseManager() {
        this.expenseList = new ExpenseList(); //sollte extern kreirt werden, Übersicht über Listen
    }

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

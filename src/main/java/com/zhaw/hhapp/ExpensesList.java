package com.zhaw.hhapp;

import java.util.HashMap;
import java.util.Map;

public class ExpensesList {
    private Map<String, ExpenseList> expensesList = new HashMap<>();
    public ExpensesList() {

    }

    public Map<String, ExpenseList> getExpensesList() {
        return expensesList;
    }

    public void setExpensesList(Map<String, ExpenseList> expensesList) {
        this.expensesList = expensesList;
    }

    public void addExpenseList(String expenseListID, ExpenseList expenseList) {
        expensesList.put(expenseListID, expenseList);
    }

    public void addExpenseList(String expenseListID) {
        expensesList.put(expenseListID, null);
    }
}

package com.zhaw.hhapp.model;

import java.util.HashMap;
import java.util.Map;

public class ExpensesList {
    private Map<String, ExpenseList> expensesList = new HashMap<>();
    public ExpensesList() {

    }
//
//    public Map<String, ExpenseList> getExpensesList() {
//        return expensesList;
//    }

    public ExpenseList getExpenseList(String expenseListId) {
        return expensesList.get(expenseListId);
    }
//
//    public void setExpensesList(Map<String, ExpenseList> expensesList) {
//        this.expensesList = expensesList;
//    }

    /**
     * Fügt eine neue Ausgabenliste unter der angegebenen ID hinzu oder überschreibt eine bestehende.
     * @param expenseListID Der Name/ID der Ausgabenliste.
     * @param expenseList Die hinzuzufügende Ausgabenliste.
     */
    public void addExpenseList(String expenseListID, ExpenseList expenseList) {
        expensesList.put(expenseListID, expenseList);
    }

    public void addExpenseList(String expenseListID) {
        expensesList.put(expenseListID, new ExpenseList());
    }

}

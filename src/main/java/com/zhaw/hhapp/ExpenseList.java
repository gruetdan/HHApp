package com.zhaw.hhapp;

import java.util.ArrayList;
import java.util.List;

public class ExpenseList {
    private List<Expense> expenses;
    public ExpenseList() {
        expenses = new ArrayList<>();
        System.out.println("ExpenseList was created");
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void printExpenses(){
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }
}

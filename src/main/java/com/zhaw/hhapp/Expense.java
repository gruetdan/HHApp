package com.zhaw.hhapp;

public class Expense {
    private String expenseText;
    private Double expenseAmount;

    public Expense(String expenseText, Double expenseAmount) {
        this.expenseText = expenseText;
        this.expenseAmount = expenseAmount;
        System.out.println("Expense was created: "+expenseText);
    }

    public String getExpenseText() {
        return expenseText;
    }

    public void setExpenseText(String expenseText) {
        this.expenseText = expenseText;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
    public void print() {
        System.out.println(expenseText+" "+expenseAmount);
    }
}

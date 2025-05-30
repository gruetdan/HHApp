package com.zhaw.hhapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class representing a single list of expenses.
 * <p>
 * Each ExpenseList object holds multiple {@link Expense} entries (e.g., for one household or trip).
 * Provides methods to add and retrieve expenses.
 * </p>
 */
public class ExpenseList {
    /**
     * Internal list of expenses for this ExpenseList.
     */
    private List<Expense> expenses;

    /**
     * Constructs a new, empty ExpenseList.
     */
    public ExpenseList() {
        expenses = new ArrayList<>();
    }

    /**
     * Returns the list of all expenses in this ExpenseList.
     *
     * @return List of Expense objects.
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Adds a new expense to the list.
     *
     * @param expense The Expense object to add.
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
}

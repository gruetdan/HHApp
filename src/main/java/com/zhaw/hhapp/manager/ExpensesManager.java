package com.zhaw.hhapp.manager;

//import static javafx.application.Application.launch;

import com.zhaw.hhapp.model.ExpensesList;
import com.zhaw.hhapp.model.ExpenseList;

public class ExpensesManager  {
    /**
     * Saves different ExpenseList in ExpensesList
     * expenseLists Contains different ExpenseList (z. B. Haushalt, Griechenland-Ferien, ...)
     */
    public static ExpensesList expensesList = new ExpensesList();

    public static ExpenseList getExpenseList(String id) {
        return expensesList.getExpenseList(id);
    }

    public static void addExpenseList(String id) {
        expensesList.addExpenseList(id);
    }
    public static void addExpenseList(String id, ExpenseList expense) {
        expensesList.addExpenseList(id, expense);
    }

    public static void overwriteExpenseListValue(String id, ExpenseList expense) {
        expensesList.addExpenseList(id, expense);
    }
}

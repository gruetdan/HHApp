package com.zhaw.hhapp.manager;

//import static javafx.application.Application.launch;

import com.zhaw.hhapp.model.ExpensesList;
import com.zhaw.hhapp.model.ExpenseList;

import java.io.File;

public class ExpensesManager  {
    /**
     * Saves different ExpenseList in ExpensesList
     * expenseLists Contains different ExpenseList (z. B. Haushalt, Griechenland-Ferien, ...)
     */
    public static ExpensesList expensesList = new ExpensesList();

    //public static String baseDir = System.getProperty("user.dir");
    public static String directoryPath = System.getProperty("user.dir")+ File.separator + "ExpenseLists";

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
    /*public static String getBaseDir() {
        return baseDir;
    }*/
    public static String getDirectoryPath() {
        return directoryPath;
    }

}

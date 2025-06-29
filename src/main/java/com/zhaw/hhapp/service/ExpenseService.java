package com.zhaw.hhapp.service;

import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.model.ExpenseList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for business logic related to managing, importing, exporting, and validating expenses.
 * <p>
 * This class separates application logic from the UI/controller, acting as an interface between the controllers and
 * the managers/models.
 * </p>
 */
public class ExpenseService {

    /**
     * Adds an expense to the specified expense list.
     *
     * @param listName The name of the expense list.
     * @param expense  The expense to add.
     */
    public void addExpense(String listName, Expense expense) {
        ExpenseList list = ExpensesManager.expensesList.getExpenseList(listName);
        list.addExpense(expense);
        ExpensesManager.expensesList.addExpenseList(listName, list);
    }

    /**
     * Returns the expense list for the given list name.
     *
     * @param listName The name of the expense list.
     * @return The ExpenseList object.
     */
    public ExpenseList getExpenseList(String listName) {
        return ExpensesManager.expensesList.getExpenseList(listName);
    }

    /**
     * Exports all expenses of a given list to a TXT file using the ExpenseExport utility.
     *
     * @param listName The name of the expense list to export.
     */
    public void exportExpensesToTxt(String listName) {
        ExpenseList expenseList = ExpensesManager.expensesList.getExpenseList(listName);

        // Check if the list exists
        if (expenseList != null) {
            ExpenseExport.exportExpenses(listName, expenseList.getExpenses());
        }
    }
    /**
     * Validates an Expense object for basic input constraints.
     *
     * @param expense The expense to validate.
     * @return Null if valid, otherwise an error message.
     */
    public String validateExpense(Expense expense) {
        double amount = expense.getAmount();
        if (amount <= 0.04 || Double.isInfinite(amount) || Double.isNaN(amount)) {
            return "Please enter a positive, valid amount greater than 0!";
        }
        if (expense.getDescription() == null || expense.getDescription().trim().isEmpty()) {
            return "Description cannot be empty!";
        }

        return null; // All good
    }

    /**
     * Imports expenses from a TXT/CSV file with the given name.
     * Each line is parsed into an Expense object.
     *
     * @param fileName The file to import from (should end with .txt).
     * @return A list of imported Expense objects.
     */
    public List<Expense> importExpenses(String fileName) {
        // Always ensure only one .txt extension
        fileName = fileName.replaceFirst("\\.txt$", "");
        fileName += ".txt";

        Path filePath = Paths.get(ExpensesManager.getDirectoryPath(), fileName);


        List<Expense> importedExpenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                importedExpenses.add(Expense.fromCsvString(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return importedExpenses;
    }

}
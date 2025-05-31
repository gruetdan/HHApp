package com.zhaw.hhapp.service;

import com.zhaw.hhapp.controller.ExpensesController;
import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.Expense;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for exporting expense lists to text files.
 * <p>
 * Provides static methods to serialize and write lists of {@link Expense} objects to disk in a CSV-like format.
 * Ensures that all files are saved with the '.txt' extension.
 * </p>
 */



public class ExpenseExport {

    /**
     * Exports a list of expenses to a file with the given name.
     * Always appends '.txt' to the file name if missing.
     * Each expense is serialized to a single line using {@link Expense#toCsvString()}.
     *
     * @param fileName The name of the file ('.txt' will be added if missing).
     * @param expenses The list of expenses to export.
     */

    public static void exportExpenses(String fileName, List<Expense> expenses) {
        // Ensure the file has the .txt extension
        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName += ".txt";
        }

        // Ensure there is at least one expense to export
        if (expenses.isEmpty()) {
            ExpensesController.showInfoDialog("Please enter Expense before export");
        }

        Path filePath = Paths.get(ExpensesManager.getDirectoryPath(), fileName);
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            for (Expense expense : expenses) {
                writer.write(expense.toCsvString() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

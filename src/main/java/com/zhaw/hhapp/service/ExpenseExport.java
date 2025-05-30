package com.zhaw.hhapp.service;

import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.Expense;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for exporting expense lists to text files.
 * <p>
 * Provides static methods to serialize and write lists of {@link Expense} objects to disk in a CSV-like format.
 * </p>
 */
public class ExpenseExport {

    /**
     * Exports a list of expenses to a file with the given name.
     * Each expense is serialized to a single line using {@link Expense#toCsvString()}.
     *
     * @param fileName The name of the file (should end with .txt).
     * @param expenses The list of expenses to export.
     */
    public static void exportExpenses(String fileName, List<Expense> expenses) {
        Path filePath = Paths.get(ExpensesManager.getDirectoryPath(),fileName);
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            for (Expense expense : expenses) {
                writer.write(expense.toCsvString() + System.lineSeparator());
            }

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

}

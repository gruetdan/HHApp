package com.zhaw.hhapp.service;


import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.Expense;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExpenseExport {

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

package com.zhaw.hhapp.service;


import com.zhaw.hhapp.model.Expense;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExpenseExport {

    public static void exportExpenses(String fileName, List<Expense> expenses) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Expense expense : expenses) {
                writer.write(expense.toCsvString() + System.lineSeparator());
            }

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

}

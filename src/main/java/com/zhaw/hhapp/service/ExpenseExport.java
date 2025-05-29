package com.zhaw.hhapp.service;


import com.zhaw.hhapp.model.Expense;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExpenseExport {

    public static void exportExpenses(String fileName, List<Expense> expenses) {
        try {
            File directory = new File("ExpenseLists");

            // Überprüfe, ob der Ordner existiert, falls nicht, erstelle ihn
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // Erstelle die Datei im ExpenseLists-Ordner
            File file = new File(directory, fileName);

            try (FileWriter writer = new FileWriter(file)) {
                for (Expense expense : expenses) {
                    writer.write(expense.toString() + System.lineSeparator());
                }
                System.out.println("Export erfolgreich!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

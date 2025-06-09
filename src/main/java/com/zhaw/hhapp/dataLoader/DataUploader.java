package com.zhaw.hhapp.dataLoader;

import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.repository.ExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Class to upload the data in .txt-files in folder ExpenseLists to h2-console
 */
@Component
public class DataUploader implements CommandLineRunner {
    private final ExpenseRepository expenseRepository;

    public DataUploader(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Import if, no data in DB yet.
        if (expenseRepository.count() != 0) {
            System.out.println("ℹ️ Bestehende Daten gefunden – Datei-Import wird übersprungen.");
            return;
        }
        // Look for all .txt-files in the folder "ExpenseLists".
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:ExpenseLists/*.txt");

        if (resources == null || resources.length == 0) {
            System.out.println("ℹ️ Keine .txt-Dateien im Ordner ExpenseLists gefunden.");
            return;
        }

        int totalCount = 0;
        // For each file found:
        for (Resource resource : resources) {
            String fileName = resource.getFilename();
            // Remove ending ".txt"
            String fileSource = fileName;
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileSource = fileName.substring(0, dotIndex);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    // Skip empty row (or header)
                    if (line.trim().isEmpty() || line.toLowerCase().contains("amount|description")) {
                        continue;
                    }
                    // Discompose row in four parts by the Pipe-Sign.
                    String[] parts = line.split("\\|", 4);
                    if (parts.length < 4) {
                        System.err.println("Format of the entries in file " + fileName + ": " + line+" not valid.");
                        continue;
                    }
                    try {
                        double amount = Double.parseDouble(parts[0].trim());
                        String description = parts[1].trim();
                        String date = parts[2].trim();
                        String userName = parts[3].trim();

                        Expense expense = new Expense(amount, description, date, userName);
                        // Set the new field "source" as filename (without Ending):
                        expense.setSource(fileSource);
                        //System.out.println("Datei: " + fileName + " -> source: " + fileSource);
                        expenseRepository.save(expense);
                        totalCount++;
                    } catch (NumberFormatException e) {
                        System.err.println("Error at parsing number in file " + fileName + ": " + line);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error during loading of file " + fileName + ": " + e.getMessage());
            }
        }
        System.out.println("✅ " + totalCount + " Expenses imported from .txt file to ExpenseLists.");
    }
}

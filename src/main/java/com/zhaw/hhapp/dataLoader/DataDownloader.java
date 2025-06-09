package com.zhaw.hhapp.dataLoader;

import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.repository.ExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to get the Expenses in the h2-consol and save them in .txt-Files locally
 */

@Component
public class DataDownloader implements CommandLineRunner {

    private final ExpenseRepository expenseRepository;

    public DataDownloader(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Get all the expenses from the h2-console
        List<Expense> expenses = expenseRepository.findAll();
        if (expenses.isEmpty()) {
            System.out.println("No data in DB found.");
            return;
        }

        // Group the expenses by the field "source"
        // If the source is null, set "default"
        Map<String, List<Expense>> expensesBySource = expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getSource() != null ? expense.getSource() : "default"));
        // Make sure, that the folder ExpenseLists exists (relative path to work directory)
        Path expenseListDir = Paths.get("ExpenseLists");
        if (!Files.exists(expenseListDir)) {
            Files.createDirectories(expenseListDir);
        }

        // For each group write the data in a file
        for (Map.Entry<String, List<Expense>> entry : expensesBySource.entrySet()) {
            String source = entry.getKey();
            List<Expense> expenseGroup = entry.getValue();

            // Create file name: source + ".txt"
            String fileName = source + ".txt";
            Path filePath = expenseListDir.resolve(fileName);

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {

                for (Expense expense : expenseGroup) {
                    // Create rows by format "AMOUNT|DESCRIPTION|DATE|USER"
                    String line = String.format("%.2f|%s|%s|%s",
                            expense.getAmount(),
                            expense.getDescription(),
                            expense.getDate(),
                            expense.getUserName());
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Exported " + expenseGroup.size() + " Return in file " + filePath);
            } catch (Exception e) {
                System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
            }
        }
    }
}
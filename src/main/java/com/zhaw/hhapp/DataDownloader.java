package com.zhaw.hhapp;

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

@Component
public class DataDownloader implements CommandLineRunner {

    private final ExpenseRepository expenseRepository;

    public DataDownloader(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Hole alle Expenses aus der Datenbank
        List<Expense> expenses = expenseRepository.findAll();
        if (expenses.isEmpty()) {
            System.out.println("Keine Daten in der Datenbank gefunden.");
            return;
        }

        // Gruppiere die Expenses anhand des Feldes "source"
        // Falls source null ist, wird hier "default" als Schlüssel verwendet.
        Map<String, List<Expense>> expensesBySource = expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getSource() != null ? expense.getSource() : "default"));

        // Sicherstellen, dass der Ordner ExpenseLists existiert (relativer Pfad zum Arbeitsverzeichnis)
        Path expenseListDir = Paths.get("ExpenseLists");
        if (!Files.exists(expenseListDir)) {
            Files.createDirectories(expenseListDir);
        }

        // Für jede Gruppe werden die Daten in eine Datei geschrieben
        for (Map.Entry<String, List<Expense>> entry : expensesBySource.entrySet()) {
            String source = entry.getKey();
            List<Expense> expenseGroup = entry.getValue();

            // Erstelle den Dateinamen: source + ".txt"
            String fileName = source + ".txt";
            Path filePath = expenseListDir.resolve(fileName);

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                // Optional: Schreibe eine Header-Zeile, falls gewünscht (hier auskommentiert)
                // writer.write("AMOUNT|DESCRIPTION|DATE|USER");
                // writer.newLine();

                for (Expense expense : expenseGroup) {
                    // Erstelle die Zeile im Format "AMOUNT|DESCRIPTION|DATE|USER"
                    String line = String.format("%.2f|%s|%s|%s",
                            expense.getAmount(),
                            expense.getDescription(),
                            expense.getDate(),
                            expense.getUserName());
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Exportiert " + expenseGroup.size() + " Ausgaben in Datei " + filePath);
            } catch (Exception e) {
                System.err.println("Fehler beim Schreiben der Datei " + filePath + ": " + e.getMessage());
            }
        }
    }
}
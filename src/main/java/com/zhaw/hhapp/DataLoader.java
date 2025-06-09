package com.zhaw.hhapp;

import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.repository.ExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataLoader implements CommandLineRunner {
    private final ExpenseRepository expenseRepository;

    public DataLoader(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Import erfolgt nur, wenn in der DB noch keine Daten vorhanden sind.
        if (expenseRepository.count() != 0) {
            System.out.println("ℹ️ Bestehende Daten gefunden – Datei-Import wird übersprungen.");
            return;
        }

        // Suche alle .txt-Dateien im Ordner "ExpenseLists" (auf gleicher Ebene wie das Projektverzeichnis).
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:ExpenseLists/*.txt");

        if (resources == null || resources.length == 0) {
            System.out.println("ℹ️ Keine .txt-Dateien im Ordner ExpenseLists gefunden.");
            return;
        }

        int totalCount = 0;
        // Für jede gefundene Datei:
        for (Resource resource : resources) {
            String fileName = resource.getFilename();
            // Entferne die Endung ".txt" (nur, wenn vorhanden)
            String fileSource = fileName;
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileSource = fileName.substring(0, dotIndex);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    // Leere Zeilen oder potenzielle Header überspringen.
                    if (line.trim().isEmpty() || line.toLowerCase().contains("amount|description")) {
                        continue;
                    }
                    // Zerlegen der Zeile in vier Teile anhand des Pipe-Zeichens.
                    String[] parts = line.split("\\|", 4);
                    if (parts.length < 4) {
                        System.err.println("Ungültiges Format in Datei " + fileName + ": " + line);
                        continue;
                    }
                    try {
                        double amount = Double.parseDouble(parts[0].trim());
                        String description = parts[1].trim();
                        String date = parts[2].trim();
                        String userName = parts[3].trim();

                        Expense expense = new Expense(amount, description, date, userName);
                        // Setze das zusätzliche Feld "source" auf den Dateinamen ohne Endung:
                        expense.setSource(fileSource);
                        //System.out.println("Datei: " + fileName + " -> source: " + fileSource);
                        expenseRepository.save(expense);
                        totalCount++;
                    } catch (NumberFormatException e) {
                        System.err.println("Fehler beim Parsen der Zahl in Datei " + fileName + ": " + line);
                    }
                }
            } catch (Exception e) {
                System.err.println("Fehler beim Laden der Datei " + fileName + ": " + e.getMessage());
            }
        }
        System.out.println("✅ " + totalCount + " Ausgaben aus den .txt-Dateien in ExpenseLists importiert.");
    }
}

package com.zhaw.hhapp.service;

//Geschäftslogik, die nicht zur UI gehört, also alles rund um das Verwalten, Importieren, Exportieren und ggf. Validieren von Ausgaben

import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.model.ExpenseList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExpenseService {

    // Fügt eine Ausgabe zur gewünschten Liste hinzu
    public void addExpense(String listName, Expense expense) {
        ExpenseList list = ExpensesManager.getExpenseList(listName);
        list.addExpense(expense);
        ExpensesManager.overwriteExpenseListValue(listName, list);
    }

    // Gibt die Ausgabenliste zurück (zur Anzeige/Weiterverarbeitung)
    public ExpenseList getExpenseList(String listName) {
        return ExpensesManager.getExpenseList(listName);
    }

    // Importiert Ausgaben für eine Liste
    /*public List<Expense> importExpenses(String listName) {
        ExpenseImport expenseImport = new ExpenseImport();
        return expenseImport.importExpenses(listName);
    }*/

    // Exportiert Ausgaben einer Liste
    public void exportExpenses(String listName, List<Expense> expenses) {
        ExpenseExport.exportExpenses(listName, expenses);

    }



    public void exportExpensesToTxt(String listName) {
        ExpenseList expenseList = ExpensesManager.getExpenseList(listName);

        // prüfen, ob liste existiert
        if (expenseList != null) {
            ExpenseExport.exportExpenses(listName, expenseList.getExpenses());
        }
    }
    public String validateExpense(Expense expense) {
        double amount = expense.getAmount();
        if (amount <= 0 || Double.isInfinite(amount) || Double.isNaN(amount)) {
            return "Bitte einen positiven, gültigen Betrag größer als 0 eingeben!";
        }
        if (expense.getDescription() == null || expense.getDescription().trim().isEmpty()) {
            return "Beschreibung darf nicht leer sein!";
        }
        // ... weitere Checks
        return null; // Alles ok
    }

    private void updateExpenseListView(ExpenseList expenseList, Button exportToTXT, ListView<String> expenseListView) {
        expenseListView.getItems().clear();
        Stage stage = (Stage) exportToTXT.getScene().getWindow();
        String title = stage.getTitle();
        for (Expense expense : expenseList.getExpenses()) {
            expenseListView.getItems().add(expense.toString());
        }
    }

    /*private List<Expense> importedExpenses;

    public ExpenseImport() {
        importedExpenses = new ArrayList<>();
    }*/

    public List<Expense> importExpensesNew(String fileName) {
        Path filePath = Paths.get(ExpensesManager.getDirectoryPath(),fileName);
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

    public ExpenseList importExpensesAndAddToList(String listName) {
        //Importiere die Ausgaben
        //ExpenseImport expenseImport = new ExpenseImport();
        List<Expense> importedExpenses = importExpensesNew(listName);

        // Hole oder erstelle die Ausgabenliste
        ExpenseList expenseList = ExpensesManager.getExpenseList(listName);
        if (expenseList == null) {
            expenseList = new ExpenseList();
            ExpensesManager.addExpenseList(listName, expenseList);
        }

        // Füge alle importierten Ausgaben hinzu
        for (Expense expense : importedExpenses) {
            expenseList.addExpense(expense);
        }

        // Speichere die Liste wieder zurück
        ExpensesManager.overwriteExpenseListValue(listName, expenseList);

        //Gib die aktualisierte Liste zurück (z.B. für die Anzeige)
        return expenseList;
    }

}


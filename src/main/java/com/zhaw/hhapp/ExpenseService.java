package com.zhaw.hhapp;

//Geschäftslogik, die nicht zur UI gehört, also alles rund um das Verwalten, Importieren, Exportieren und ggf. Validieren von Ausgaben

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
    public List<Expense> importExpenses(String listName) {
        ExpenseImport expenseImport = new ExpenseImport();
        return expenseImport.importExpenses(listName);
    }

    // Exportiert Ausgaben einer Liste
    public void exportExpenses(String listName, List<Expense> expenses) {
        ExpenseExport.exportExpenses(listName, expenses);
    }

    public ExpenseList importExpensesAndAddToList(String listName) {
        //Importiere die Ausgaben
        ExpenseImport expenseImport = new ExpenseImport();
        List<Expense> importedExpenses = expenseImport.importExpenses(listName);

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
            return "Bitte einen positiven, gültigen Betrag grösser als 0 eingeben!";
        }
        if (expense.getDescription() == null || expense.getDescription().trim().isEmpty()) {
            return "Beschreibung darf nicht leer sein!";
        }
        //
        return null; // Alles ok
    }

}


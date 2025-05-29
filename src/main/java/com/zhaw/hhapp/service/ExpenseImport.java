package com.zhaw.hhapp.service;

import com.zhaw.hhapp.model.Expense;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseImport {
    private List<Expense> importedExpenses;

    public ExpenseImport() {
        importedExpenses = new ArrayList<>();
    }


    public static List<Expense> importExpenses(String fileName) {
        List<Expense> importedExpenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                importedExpenses.add(Expense.fromCsvString(line));
            }

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
        return importedExpenses;
    }

}

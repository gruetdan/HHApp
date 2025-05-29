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

    public List<Expense> importExpenses(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = reader.readLine())!=null){
                String[] parts = line.split(",");
                importedExpenses.add(new Expense(Double.parseDouble(parts[0]),parts[1],parts[2],parts[3]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Import erfolgreich");

    return importedExpenses;
    }
}

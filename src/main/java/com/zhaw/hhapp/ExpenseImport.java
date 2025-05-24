package com.zhaw.hhapp;

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

    public List<Expense> importExpenses() {
        /*
        try (BufferedReader reader = new BufferedReader(new FileReader(new ExpenseManager().getFilename()))){
            String line;
            while ((line = reader.readLine())!=null){
                String[] parts = line.split(",");
                /*System.out.println(parts[0]);
                System.out.println(parts[1]);
                System.out.println(parts[2]);
                System.out.println(parts[3]);
                importedExpenses.add(new Expense(Double.parseDouble(parts[0]),parts[1],parts[2],parts[3]));
                //System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Import erfolgreich");

         */
    return importedExpenses;
    }
}

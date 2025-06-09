package com.zhaw.hhapp;

import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.repository.ExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ExpenseRepository expenseRepository;

    public DataLoader(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) {
        // Add test data only if DB is empty
        if (expenseRepository.count() == 0) {
            expenseRepository.save(new Expense( 4.50,"Coffee", "2025-06-08", "Anouk"));
            expenseRepository.save(new Expense( 23.90,"Books", "2025-06-07", "Daniel"));
            expenseRepository.save(new Expense( 55.20,"Groceries", "2025-06-06",  "Anouk"));

            System.out.println("✅ Example expenses inserted.");
        } else {
            System.out.println("ℹ️ Existing data found – skipping test insert.");
        }
    }
}

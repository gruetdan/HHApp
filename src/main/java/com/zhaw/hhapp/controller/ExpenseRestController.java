package com.zhaw.hhapp.controller;

import com.zhaw.hhapp.dataLoader.DataDownloader;
import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller to handle HTTP API calls for expenses.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {

    private final ExpenseRepository expenseRepository;
    private final DataDownloader dataDownloader; // Inject DataDownloader

    @Autowired
    public ExpenseRestController(ExpenseRepository expenseRepository, DataDownloader dataDownloader) {
        this.expenseRepository = expenseRepository;
        this.dataDownloader = dataDownloader;
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense newExpense) {
        return expenseRepository.findById(id)
                .map(expense -> {
                    expense.setAmount(newExpense.getAmount());
                    expense.setDescription(newExpense.getDescription());
                    expense.setDate(newExpense.getDate());
                    expense.setUserName(newExpense.getUserName());
                    expense.setSource(newExpense.getSource());
                    return expenseRepository.save(expense);
                })
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }

    // New endpoint to call DataDownloader logic
    @GetMapping("/save")
    public ResponseEntity<String> saveData() throws Exception {
        // Call the DataDownloader (adjust the method name as appropriate)
        dataDownloader.run(new String[]{});
        return ResponseEntity.ok("Data saved successfully.");
    }
}

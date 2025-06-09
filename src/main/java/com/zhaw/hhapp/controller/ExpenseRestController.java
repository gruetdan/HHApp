package com.zhaw.hhapp.controller;

import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller to handle HTTP API calls for expenses.
 * This is separate from the JavaFX ExpenseController.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseRestController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
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
                    return expenseRepository.save(expense);
                })
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }

}


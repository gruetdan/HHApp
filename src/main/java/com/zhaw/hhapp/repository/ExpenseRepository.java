package com.zhaw.hhapp.repository;

import com.zhaw.hhapp.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // You can define custom queries here if needed later
}

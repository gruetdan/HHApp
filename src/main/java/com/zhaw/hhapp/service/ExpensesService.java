package com.zhaw.hhapp.service;

import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.model.ExpenseList;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for business logic related to expenses lists.
 * <p>
 * This class separates the application logic from the UI controller,
 * providing methods for creating lists, listing files, and folder management.
 * </p>
 */
public class ExpensesService {

    /**
     * Retrieves the ExpenseList object with the given name.
     *
     * @param listName The name/identifier of the expense list.
     * @return The ExpenseList object, or null if not found.
     */
    public ExpenseList getExpenseList(String listName) {
        return ExpensesManager.expensesList.getExpenseList(listName);
    }


    public StringBuilder getMessage(ExpenseList expenseList, double sum) {
        Map<String, Double> sumPerUser = calculateSumPerUser(expenseList);
        // formatted result message
        StringBuilder message = new StringBuilder();
        message.append(String.format("Total sum of expenses: %.2f CHF\n", sum));

        if (sumPerUser.size() > 1) {
            message.append("\nBy user:\n");
            for (Map.Entry<String, Double> entry : sumPerUser.entrySet()) {
                message.append(String.format("- %s: %.2f CHF\n", entry.getKey(), entry.getValue()));
            }
        }
        return message;
    }

    public Map<String, Double> calculateSumPerUser(ExpenseList expenseList) {
        Map<String, Double> sumPerUser = new HashMap<>();
        for (Expense expense : expenseList.getExpenses()) {
            String user = expense.getUserName();
            sumPerUser.put(user, sumPerUser.getOrDefault(user, 0.0) + expense.getAmount());
        }
        return sumPerUser;
    }

}

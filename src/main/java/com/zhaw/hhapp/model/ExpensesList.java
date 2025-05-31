package com.zhaw.hhapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Model class representing a collection of multiple expense lists.
 * <p>
 * Each expense list (e.g., for a household or a specific event) is stored by a unique identifier (String).
 * Internally, a HashMap is used for fast lookup and management.
 * </p>
 *
 * <ul>
 *     <li>Provides methods to add, overwrite, and retrieve individual expense lists.</li>
 *     <li>Acts as the underlying data structure for {@link com.zhaw.hhapp.manager.ExpensesManager}.</li>
 * </ul>
 */
public class ExpensesList {
    /**
     * Internal map holding all ExpenseLists, each identified by a unique String key.
     */
    private Map<String, ExpenseList> expensesList;

    /**
     * Constructs a new, empty ExpensesList.
     */
    public ExpensesList() {
        this.expensesList = new HashMap<>();
    }

    //todo: Folgende Funktion ist auch in ExpensesManager vorhanden.
    /**
     * Retrieves an ExpenseList by its identifier.
     *
     * @param expenseListId The name/ID of the expense list to retrieve.
     * @return The corresponding ExpenseList, or null if it does not exist.
     */
    public ExpenseList getExpenseList(String expenseListId) {
        expenseListId = expenseListId.replaceFirst("\\.txt$", "");
        return expensesList.get(expenseListId);
    }

    /**
     * Adds a new expense list or overwrites an existing one with the given ID.
     * Existence checks are handled externally.
     *
     * @param expenseListID The identifier for the expense list.
     * @param expenseList   The ExpenseList object to add or overwrite.
     */
    public void addExpenseList(String expenseListID, ExpenseList expenseList) {
        expenseListID = expenseListID.replaceFirst("\\.txt$", "");
        expensesList.put(expenseListID, expenseList);
    }

    /**
     * Adds a new, empty ExpenseList with the given ID.
     * If a list with the same ID exists, it will be overwritten!
     * Existence checks are handled externally.
     * @param expenseListID The identifier for the new expense list.
     */
    public void addExpenseList(String expenseListID) {
        expenseListID = expenseListID.replaceFirst("\\.txt$", "");
        expensesList.put(expenseListID, new ExpenseList());
    }
    // Method to retrieve all keys (expense list IDs)
    public Set<String> keySet() {
        return expensesList.keySet();
    }
}

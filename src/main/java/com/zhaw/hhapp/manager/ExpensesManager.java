package com.zhaw.hhapp.manager;

import com.zhaw.hhapp.model.ExpensesList;
import com.zhaw.hhapp.model.ExpenseList;
import java.io.File;

/**
 * Manager class for handling the collection of all expense lists.
 * <p>
 * Provides static methods to add, retrieve, and overwrite individual expense lists
 * within a global {@link ExpensesList} instance. Also stores the path to the directory
 * where expense lists are persisted.
 * </p>
 *
 * <ul>
 *     <li>Acts as a "gateway" between service/business logic and the underlying model.</li>
 *     <li>Handles global access to all expense lists (could be improved for scalability).</li>
 * </ul>
 */
public class ExpensesManager {
    /**
     * A static instance holding all expense lists.
     * Each entry in {@code expensesList} represents a separate ExpenseList (e.g., household, vacation, etc.).
     */
    public static ExpensesList expensesList = new ExpensesList();

    /**
     * Directory path for storing expense list files.
     * By default, it is set to "ExpenseLists" in the user's current working directory.
     */
    public static String directoryPath = System.getProperty("user.dir") + File.separator + "ExpenseLists";

    public static String getDirectoryPath() {
        return directoryPath;
    }

}


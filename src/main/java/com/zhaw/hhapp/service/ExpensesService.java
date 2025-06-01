package com.zhaw.hhapp.service;

import com.zhaw.hhapp.controller.ExpenseController;
import com.zhaw.hhapp.controller.ExpensesController;
import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.ExpenseList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;

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

}

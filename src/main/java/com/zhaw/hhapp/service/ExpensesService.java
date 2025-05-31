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
     * Creates a new expense list with the given name.
     * Returns true if the list was successfully created, or false if a list with the same name exists,
     * or the name is invalid (null or empty).
     *
     * @param name Name of the new expense list.
     * @return true if created successfully, false otherwise.
     */

   /* public boolean addExpenseList(String name) {
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Check if the list already exists
        if (ExpensesManager.expensesList.getExpenseList(name) != null) {
            return false;
        }
        // Create new list
        ExpensesManager.expensesList.addExpenseList(name);
        return true;
    }*/

    /**
     * Lists all expense list files in the ExpenseLists directory.
     * Only files ending with '.txt' are considered.
     * Returns list names without the '.txt' extension for display.
     *
     * @return ArrayList of expense list names (String without .txt extension).
     */
    //todo: When application is started, update automatically static ExpensesManager.expensesList (and use it!)
    public ArrayList<String> listTxtFiles() {
        /*
        * Create file-variable (pointer) to the folder which contains the existing expense lists.
        * or create folder if not available yet.
        * And create file-list with all the existing expense lists.
        */
        File directory = new File(ExpensesManager.getDirectoryPath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File[] files = directory.listFiles();

        // Save the file names in an Array List of Strings
        ArrayList<String> fileList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                // Only consider files ending with .txt
                if (file.isFile() && fileName.toLowerCase().endsWith(".txt")) {
                    // Strip the .txt extension for display
                    String nameWithoutExtension = fileName.replaceFirst("\\.txt$", "");
                    fileList.add(nameWithoutExtension);
                }
            }
        }
        return fileList;
    }

    /**
     * Populates the ListView in the start window with existing expense lists.
     * <p>
     * Note: UI code like this usually belongs in the controller, not the service.
     * This method should be refactored and moved to the controller in the future.
     * </p>
     *
     * @param list      The list of existing expense list names.
     * @param listView  The ListView UI component to populate.
     * @param refButton A reference button from which to get the scene and stage (should be avoided in services).
     */
    // TODO: Refactor this method (and related UI logic) to the ExpensesController.
    public void updateListView(Set<String> list, ListView<String> listView, Button refButton) {
        listView.getItems().clear();

        // Get the current stage from the reference button
        Stage stage = new Stage();
        stage = (Stage) refButton.getScene().getWindow();

        for (String i : list) {
            listView.getItems().add(i.toString());
        }
    }

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

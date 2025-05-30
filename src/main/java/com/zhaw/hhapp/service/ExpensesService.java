package com.zhaw.hhapp.service;

import com.zhaw.hhapp.manager.ExpensesManager;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Service class for business logic related to expense lists.
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
    public boolean addExpenseList(String name) {
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Check if the list already exists
        if (ExpensesManager.getExpenseList(name) != null) {
            return false;
        }
        // Create new list
        ExpensesManager.addExpenseList(name);
        return true;
    }

    /**
     * Lists all files in the ExpenseLists directory.
     * Only returns file names (not directories).
     *
     * @return ArrayList of file names in the ExpenseLists directory.
     */
    //ToDo: Add file extension consistency for expense lists Ensure all expense lists are always saved and read with
    // the .txt file extension. Refactor saving logic to append .txt if missing. Refactor file listing/reading to
    // only consider files ending with .txt. (Optional) Provide migration for already existing lists without extension.
    public ArrayList<String> listTxtFiles() {

        File directory = new File(ExpensesManager.getDirectoryPath());
        // Ensure the directory exists, create if not
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File[] files = new File(ExpensesManager.getDirectoryPath()).listFiles();
        ArrayList<String> fileList = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();

                    fileList.add(fileName);
                    System.out.println(fileName);
                } else { // @Daniel: brauchen wir diese else statements? ich glaube nicht, entfernen?
                    System.out.println(file.getName() + " - Type: Directory");
                }
            }
        } else {// @Daniel: brauchen wir diese else statements? ich glaube nicht, entfernen?
            System.out.println("Subfolder does not exist or is empty.");
        }
        return fileList;
    }


    /**
     * Creates the 'ExpenseLists' subfolder for expenses, if it does not already exist.
     * This ensures the application has a dedicated folder for storing expense lists.
     */
    public void createExpenseListsFolder() {
        Path path = Paths.get(ExpensesManager.getDirectoryPath());
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Subfolder 'ExpenseLists' has been created.");
            } catch (Exception e) {
                System.err.println("Error creating subfolder: " + e.getMessage());
            }
        } else {
            System.out.println("Subfolder 'ExpenseLists' already exists.");
        }
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
    public void updateListView(ArrayList<String> list, ListView<String> listView, Button refButton) {
        listView.getItems().clear();

        // Get the current stage from the reference button
        Stage stage = new Stage();
        stage = (Stage) refButton.getScene().getWindow();

        for (String i : list) {
            listView.getItems().add(i.toString());
        }
    }

}

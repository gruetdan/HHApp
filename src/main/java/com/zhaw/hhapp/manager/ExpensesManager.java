package com.zhaw.hhapp.manager;

import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.model.ExpensesList;
import com.zhaw.hhapp.model.ExpenseList;
import com.zhaw.hhapp.service.ExpenseService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    public static void load(){
        /*
         * Create file-variable (pointer) to the folder which contains the existing expense lists.
         * or create folder if not available yet.
         * And create file-list with all the existing expense lists.
         */
        File directory = new File(getDirectoryPath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File[] files = directory.listFiles();

        // Save the file names in an Array List of Strings
        //ArrayList<String> fileList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                // Only consider files ending with .txt
                if (file.isFile() && fileName.toLowerCase().endsWith(".txt")) {
                    // Strip the .txt extension for display
                    String nameWithoutExtension = fileName.replaceFirst("\\.txt$", "");
                    //fileList.add(nameWithoutExtension);
                    expensesList.addExpenseList(nameWithoutExtension,importExpensesAndAddToList(nameWithoutExtension));//new ExpenseList().importExpensesFromFile(fileName));//
                }
            }
        }
    }


    private static ExpenseList importExpensesAndAddToList(String listName) {
        // Ensure the list name has the .txt extension for import
        String fileName = listName;
        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName += ".txt";
        }
        // Import the expenses from file
        List<Expense> importedExpenses = new ExpenseService().importExpenses(fileName);

        // Remove .txt for the internal list name (so you don't store the name with extension in memory)
        String listKey = listName.replaceFirst("\\.txt$", "");

        // Get or create the expense list - make sure to update and not to overwrite the existing expensesList
        ExpenseList expenseList = expensesList.getExpenseList(listKey);
        if (expenseList == null) {
            expenseList = new ExpenseList();
            expensesList.addExpenseList(listKey, expenseList);
        } else {

            expenseList.getExpenses().clear();
        }

        // Add all imported expenses (now always to a cleared list)
        for (Expense expense : importedExpenses) {
            expenseList.addExpense(expense);
        }

        // Return the updated list (for display, etc.)
        return expenseList;
    }

    public static void removeExpenseList(String listName){
        // Ensure the list name has the .txt extension for import
        String fileName = listName;
        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName += ".txt";
        }

        Path filePath = Paths.get(directoryPath, fileName);
        try {
            Files.delete(filePath);
            System.out.println("File "+fileName+" deleted successfully.");
        } catch (IOException e) {
            System.out.println("Failed to delete the file: " + fileName);
            System.out.println(e.getMessage());
        }


    }
}


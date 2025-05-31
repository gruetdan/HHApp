package com.zhaw.hhapp.manager;

import com.zhaw.hhapp.model.ExpensesList;
import com.zhaw.hhapp.model.ExpenseList;
import com.zhaw.hhapp.service.ExpenseService;

import java.io.File;
import java.util.ArrayList;

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
        listTxtFiles();

        for (String fileName : expensesList.keySet()) {
            // Delete file extension .txt (if necessary) for representation
            String name = fileName.replaceFirst("\\.txt$", "");
            // Update the static ExpensesList (todo: rethink elegance, rethink utility - is it needed?)
            //todo: importExpensesAndAddToList in expensesService or directly here?
            expensesList.addExpenseList(name,new ExpenseService().importExpensesAndAddToList(name));
        }
    }
    private static void listTxtFiles() {
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
                    expensesList.addExpenseList(nameWithoutExtension);
                }
            }
        }
        //return fileList;
    }
}


package service;


import com.zhaw.hhapp.manager.ExpensesManager;
import com.zhaw.hhapp.model.ExpenseList;
import com.zhaw.hhapp.model.ExpensesList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ExpensesManagerTest {

    // Temporary directory created before each test.
    private Path tempDir;

    @BeforeEach
    public void setup() throws IOException {
        // Create a temporary directory for testing
        tempDir = Files.createTempDirectory("testExpenseLists");

        // Redirect the manager to use our temporary directory
        ExpensesManager.directoryPath = tempDir.toString();

        // Reset the static expensesList so that tests are independent.
        ExpensesManager.expensesList = new ExpensesList();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the temporary files and directory recursively
        Files.walk(tempDir)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void testGetDirectoryPath() {
        String dir = ExpensesManager.getDirectoryPath();
        assertEquals(tempDir.toString(), dir, "getDirectoryPath should return the temporary directory path.");
    }

    @Test
    public void testLoadEmptyDirectory() {
        // When the directory is empty, load() should not add any expense lists.
        ExpensesManager.load();
        // Assuming that if no file is present, getExpenseList returns null for any key.
        assertNull(ExpensesManager.expensesList.getExpenseList("dummy"),
                "No expense list should be added when directory has no .txt files.");
    }

    @Test
    public void testLoadWithTxtFile() throws IOException {
        // Create a dummy .txt file in the temp directory.
        Path filePath = tempDir.resolve("testList.txt");
        // Write empty content to simulate an empty expense list.
        Files.write(filePath, new byte[0]);

        // Calling load() should detect the file and add an expense list with key "testList"
        ExpensesManager.load();
        ExpenseList list = ExpensesManager.expensesList.getExpenseList("testList");
        assertNotNull(list, "Expense list with key 'testList' should be created after load().");

        // Assuming that ExpenseService.importExpensesNew(fileName) returns an empty list on empty files,
        // the list of expenses should be empty.
        assertEquals(0, list.getExpenses().size(), "Imported expense list should have 0 expenses for an empty file.");
    }

    @Test
    public void testRemoveExpenseListFileExists() throws IOException {
        // Create a dummy .txt file that should be removed.
        Path filePath = tempDir.resolve("toRemove.txt");
        Files.write(filePath, "dummy content".getBytes());
        assertTrue(Files.exists(filePath), "The file 'toRemove.txt' should exist before removal.");

        // Call removeExpenseList with the list name (without '.txt' extension)
        ExpensesManager.removeExpenseList("toRemove");
        // The file should be deleted.
        assertFalse(Files.exists(filePath), "The file 'toRemove.txt' should be deleted by removeExpenseList().");
    }

    @Test
    public void testRemoveExpenseListFileNotExists() {
        // Calling removeExpenseList on a file that does not exist should not throw an exception.
        assertDoesNotThrow(() -> ExpensesManager.removeExpenseList("nonexistent"),
                "removeExpenseList() should not throw an exception if the file does not exist.");
    }
}

package model;


import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.model.ExpenseList;
import com.zhaw.hhapp.model.ExpensesList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExpensesListTest {

    private ExpensesList expensesList;
    private ExpenseList listA;
    private ExpenseList listB;

    @BeforeEach
    void setUp() {
        expensesList = new ExpensesList();
        listA = new ExpenseList();
        listA.addExpense(new Expense(10.0, "Kaffee", "01.01.2024", "Anna"));
        listB = new ExpenseList();
        listB.addExpense(new Expense(20.0, "Buch", "02.01.2024", "Ben"));
    }

    @Test
    void testAddAndGetExpenseList() {
        expensesList.addExpenseList("Haushalt", listA);
        ExpenseList retrieved = expensesList.getExpenseList("Haushalt");

        assertNotNull(retrieved);
        assertEquals(1, retrieved.size());
        assertEquals("Kaffee", retrieved.getExpenses().get(0).getDescription());
    }

    @Test
    void testAddExpenseListOverwritesExisting() {
        expensesList.addExpenseList("Reise", listA);
        expensesList.addExpenseList("Reise", listB);  // overwrite

        ExpenseList retrieved = expensesList.getExpenseList("Reise");
        assertNotNull(retrieved);
        assertEquals("Buch", retrieved.getExpenses().get(0).getDescription());
    }

    @Test
    void testAddExpenseListWithTxtSuffix() {
        expensesList.addExpenseList("Ferien.txt", listA);
        ExpenseList retrieved = expensesList.getExpenseList("Ferien");

        assertNotNull(retrieved);
        assertEquals("Kaffee", retrieved.getExpenses().get(0).getDescription());
    }

    @Test
    void testAddEmptyExpenseListById() {
        expensesList.addExpenseList("Projekt");
        ExpenseList retrieved = expensesList.getExpenseList("Projekt");

        assertNotNull(retrieved);
        assertTrue(retrieved.getExpenses().isEmpty());
    }

    @Test
    void testKeySet() {
        expensesList.addExpenseList("Liste1", listA);
        expensesList.addExpenseList("Liste2", listB);

        Set<String> keys = expensesList.keySet();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("Liste1"));
        assertTrue(keys.contains("Liste2"));
    }

    @Test
    void testDeleteExpensesList() {
        expensesList.addExpenseList("Archiv", listA);
        assertNotNull(expensesList.getExpenseList("Archiv"));

        expensesList.deleteExpensesList("Archiv");
        assertNull(expensesList.getExpenseList("Archiv"));
    }

    @Test
    void testDeleteWithTxtSuffix() {
        expensesList.addExpenseList("MeineListe.txt", listA);
        assertNotNull(expensesList.getExpenseList("MeineListe"));

        expensesList.deleteExpensesList("MeineListe");  // delete expects exact key
        assertNull(expensesList.getExpenseList("MeineListe"));  // funktioniert NICHT ohne Anpassung im Code
    }
}


package model;


import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.model.ExpenseList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseListTest {

    private ExpenseList expenseList;
    private Expense e1;
    private Expense e2;

    @BeforeEach
    void setUp() {
        expenseList = new ExpenseList();
        e1 = new Expense(10.0, "Kaffee", "01.01.2024", "Alice");
        e2 = new Expense(20.0, "Buch", "02.01.2024", "Bob");
    }

    @Test
    void testAddExpense() {
        expenseList.addExpense(e1);
        expenseList.addExpense(e2);

        List<Expense> expenses = expenseList.getExpenses();
        assertEquals(2, expenses.size());
        assertTrue(expenses.contains(e1));
        assertTrue(expenses.contains(e2));
    }

    @Test
    void testRemoveExpense() {
        expenseList.addExpense(e1);
        expenseList.addExpense(e2);

        expenseList.removeExpense(e1);
        List<Expense> expenses = expenseList.getExpenses();

        assertEquals(1, expenses.size());
        assertFalse(expenses.contains(e1));
        assertTrue(expenses.contains(e2));
    }

    @Test
    void testGetExpensesReturnsCorrectList() {
        expenseList.addExpense(e1);
        List<Expense> result = expenseList.getExpenses();

        assertEquals(1, result.size());
        assertEquals(e1, result.get(0));
    }

    @Test
    void testIterator() {
        expenseList.addExpense(e1);
        expenseList.addExpense(e2);

        Iterator<Expense> it = expenseList.iterator();

        assertTrue(it.hasNext());
        assertEquals(e1, it.next());
        assertTrue(it.hasNext());
        assertEquals(e2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testSize() {
        assertEquals(0, expenseList.size());
        expenseList.addExpense(e1);
        assertEquals(1, expenseList.size());
        expenseList.addExpense(e2);
        assertEquals(2, expenseList.size());
        expenseList.removeExpense(e1);
        assertEquals(1, expenseList.size());
    }
}


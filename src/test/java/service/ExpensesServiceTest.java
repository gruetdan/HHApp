package service;

import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.model.ExpenseList;
import com.zhaw.hhapp.service.ExpensesService;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the ExpensesService class.
 * <p>
 * These tests verify that the calculation of user-specific expense totals behaves correctly
 * for multiple users, single users, and empty lists.
 */
class ExpensesServiceTest {

    /**
     * Tests whether the sum per user is calculated correctly
     * when the list contains expenses from two users.
     */
    @Test
    void testCalculateSumPerUser_twoUsers() {
        // Arrange
        ExpenseList list = new ExpenseList();
        list.addExpense(new Expense(10.0, "Lunch", "03.06.2025", "Anouk"));
        list.addExpense(new Expense(25.0, "Gas", "03.06.2025", "Daniel"));
        list.addExpense(new Expense(5.0, "Coffee", "03.06.2025", "Anouk"));

        ExpensesService service = new ExpensesService();

        // Act
        Map<String, Double> result = service.calculateSumPerUser(list);

        // Assert
        assertEquals(2, result.size());
        assertEquals(15.0, result.get("Anouk"), 0.001);
        assertEquals(25.0, result.get("Daniel"), 0.001);
    }

    /**
     * Tests whether the method correctly returns an empty result
     * when the input list contains no expenses.
     */
    @Test
    void testCalculateSumPerUser_emptyList() {
        ExpenseList list = new ExpenseList();
        ExpensesService service = new ExpensesService();
        Map<String, Double> result = service.calculateSumPerUser(list);
        assertTrue(result.isEmpty());

    }

    /**
     * Tests whether the sum per user is calculated correctly
     * when all expenses belong to a single user.
     */
    @Test
    void testCalculateSumPerUser_singleUser() {
        ExpenseList list = new ExpenseList();
        ExpensesService service = new ExpensesService();
        list.addExpense(new Expense(10.0, "Lunch", "03.06.2025", "Anouk"));
        list.addExpense(new Expense(5.0, "Coffee", "03.06.2025", "Anouk"));

        Map<String, Double> result = service.calculateSumPerUser(list);
        assertEquals(1, result.size());
        assertEquals(15.0, result.get("Anouk"), 0.001);
    }

}

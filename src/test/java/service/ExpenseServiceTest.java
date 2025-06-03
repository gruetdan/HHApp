package service;

import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.service.ExpenseService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the {@link ExpenseService} class, focusing on the {@code validateExpense} method.
 * <p>
 * These tests verify that invalid expense inputs such as zero, negative, NaN, or infinite amounts,
 * as well as missing descriptions, are correctly identified and reported with appropriate error messages.
 */
class ExpenseServiceTest {

    ExpenseService service = new ExpenseService();


    /**
     * Tests that a valid expense passes validation (returns null).
     */
    @Test
    void testValidateExpense_valid() {
        Expense expense = new Expense(10.0, "Lunch", "2025-06-03", "Anouk");
        String result = service.validateExpense(expense);
        assertNull(result); // valid case → returns null
    }

    /**
     * Tests that an amount under 0.01 CHF is considered invalid.
     */
    @Test
    void testValidateExpense_minAmount() {
        Expense expense = new Expense(0.01, "Lunch", "2025-06-03", "Anouk");
        String result = service.validateExpense(expense);
        assertEquals("Please enter a positive, valid amount greater than 0!", result);
    }

    /**
     * Tests that a negative amount is considered invalid.
     */
    @Test
    void testValidateExpense_negativeAmount() {
        Expense expense = new Expense(-5.0, "Lunch", "2025-06-03", "Anouk");
        String result = service.validateExpense(expense);
        assertEquals("Please enter a positive, valid amount greater than 0!", result);
    }

    /**
     * Tests that a NaN amount is considered invalid.
     */
    @Test
    void testValidateExpense_nanAmount() {
        Expense expense = new Expense(Double.NaN, "Lunch", "2025-06-03", "Anouk");
        String result = service.validateExpense(expense);
        assertEquals("Please enter a positive, valid amount greater than 0!", result);
    }

    /**
     * Tests that an infinite amount is considered invalid.
     */
    @Test
    void testValidateExpense_infiniteAmount() {
        Expense expense = new Expense(Double.POSITIVE_INFINITY, "Lunch", "2025-06-03", "Anouk");
        String result = service.validateExpense(expense);
        assertEquals("Please enter a positive, valid amount greater than 0!", result);
    }


    /**
     * Tests that an empty (whitespace-only) description is considered invalid.
     */
    @Test
    void testValidateExpense_emptyDescription() {
        Expense expense = new Expense(10.0, "   ", "2025-06-03", "Anouk");
        String result = service.validateExpense(expense);
        assertEquals("Description cannot be empty!", result);
    }

    /**
     * Tests that a null description is considered invalid.
     */
    @Test
    void testValidateExpense_nullDescription() {
        Expense expense = new Expense(10.0, null, "2025-06-03", "Anouk");
        String result = service.validateExpense(expense);
        assertEquals("Description cannot be empty!", result);
    }
}

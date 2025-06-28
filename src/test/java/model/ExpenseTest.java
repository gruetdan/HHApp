package model;

import com.zhaw.hhapp.model.Expense;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void testConstructorWithAllFields() {
        Expense e = new Expense(20.5, "Brot", "01.01.2024", "Alice");

        assertEquals(20.5, e.getAmount());
        assertEquals("Brot", e.getDescription());
        assertEquals("01.01.2024", e.getDate());
        assertEquals("Alice", e.getUserName());
    }

    @Test
    void testConstructorUsesCurrentDateWhenEmpty() {
        Expense e = new Expense(10.0, "Milch", "", "Bob");

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        assertEquals(today, e.getDate());
        assertEquals("Bob", e.getUserName());
    }

    @Test
    void testConstructorUsesSystemUserWhenUserEmpty() {
        Expense e = new Expense(5.0, "Kaffee", "02.02.2023", "");

        assertEquals(System.getProperty("user.name"), e.getUserName());
        assertEquals("02.02.2023", e.getDate());
    }

    @Test
    void testToStringMethod() {
        Expense e = new Expense(7.5, "Buch", "15.03.2023", "Charlie");
        String expected = "7.50,Buch,15.03.2023,Charlie";

        assertEquals(expected, e.toString());
    }

    @Test
    void testToCsvStringMethod() {
        Expense e = new Expense(12.3, "Kino", "12.05.2023", "Dana");
        String expected = "12.30|Kino|12.05.2023|Dana";

        assertEquals(expected, e.toCsvString());
    }

    @Test
    void testFromCsvString() {
        String csv = "9.99|Pizza|11.06.2024|Eve";
        Expense e = Expense.fromCsvString(csv);

        assertEquals(9.99, e.getAmount());
        assertEquals("Pizza", e.getDescription());
        assertEquals("11.06.2024", e.getDate());
        assertEquals("Eve", e.getUserName());
    }

    @Test
    void testFromCsvStringThrowsExceptionOnInvalidInput() {
        String invalidCsv = "not-enough|fields";
        assertThrows(IllegalArgumentException.class, () -> Expense.fromCsvString(invalidCsv));
    }

    @Test
    void testSettersAndGetters() {
        Expense e = new Expense();
        e.setAmount(99.9);
        e.setDescription("Laptop");
        e.setDate("10.10.2024");
        e.setUserName("Frank");
        e.setId(123L);
        e.setSource("HH2024");

        assertEquals(99.9, e.getAmount());
        assertEquals("Laptop", e.getDescription());
        assertEquals("10.10.2024", e.getDate());
        assertEquals("Frank", e.getUserName());
        assertEquals(123L, e.getId());
        assertEquals("HH2024", e.getSource());
    }
}

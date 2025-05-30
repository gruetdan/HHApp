package com.zhaw.hhapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Model class representing a single expense entry.
 * <p>
 * Each Expense contains an amount, description, date, and user name.
 * Provides methods for CSV serialization and deserialization.
 * </p>
 */
public class Expense {
    /** The amount of the expense. */
    private double amount;

    /** The description of the expense (e.g., what was bought). */
    private String description;

    /** The date of the expense, formatted as dd.MM.yyyy. */
    private String date;

    /** The username of the person who created the expense. */
    private String userName;

    /**
     * Constructs a new Expense object.
     * If date is empty, the current date is used.
     * If userName is empty, the system username is used.
     *
     * @param amount      The amount of the expense.
     * @param description The description of the expense.
     * @param date        The date of the expense (format: dd.MM.yyyy), or empty for today.
     * @param userName    The name of the user, or empty for system username.
     */
    public Expense(double amount, String description, String date, String userName) {
        this.amount = amount;
        this.description = description;
        if (userName.length() > 0) {
            this.userName = userName;
        } else {
            this.userName = System.getProperty("user.name");
        }
        if (date.length() > 0) {

            this.date = date;
        } else {
            this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
    }

    /**
     * Returns the amount of the expense.
     *
     * @return The amount as double.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the description of the expense.
     *
     * @return The description as String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the username who created the expense.
     *
     * @return The username as String.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the date of the expense (format: dd.MM.yyyy).
     *
     * @return The date as String.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns a comma-separated String representation of the expense.
     *
     * @return Comma-separated expense data.
     */
    @Override
    public String toString() {
        return String.format("%.2f,%s,%s,%s", amount, description, date, userName);
    }

    /**
     * Returns a pipe-separated CSV String of the expense for file export.
     *
     * @return Pipe-separated CSV string.
     */
    public String toCsvString() {
        return String.format("%.2f|%s|%s|%s", amount, description, date, userName);
    }

    /**
     * Creates an Expense object from a CSV string.
     * The expected format is: amount|description|date|userName
     *
     * @param line The CSV line to parse.
     * @return An Expense object.
     * @throws IllegalArgumentException If the format is invalid.
     */
    public static Expense fromCsvString(String line) {
        String[] parts = line.split("\\|", 4); // Das Pipe-Zeichen muss als Regex escaped werden
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid CSV format for Expense: " + line);
        }
        double amount = Double.parseDouble(parts[0]);
        String description = parts[1];
        String date = parts[2];
        String userName = parts[3];
        return new Expense(amount, description, date, userName);
    }
}

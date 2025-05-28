package com.zhaw.hhapp;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Controller für die Verwaltung der Ausgabenlisten (UI-Eingaben).
 * Gibt Aufgaben an den Service weiter und kümmert sich um die Benutzeroberfläche.
 */
public class ExpensesController {
    /**
     * Nimmt Werte entgegen und gibt an Manager weiter
     */

    @FXML
    private Button AddExpenseListButton;

    @FXML
    private TextField ExpenseListTextField;

    // Instanz der Service-Klasse für die Logik
    private ExpensesService expensesService = new ExpensesService();

    @FXML
    void addExpenseList(ActionEvent event) {
        String listName = ExpenseListTextField.getText();

        // Service fragt, ob Anlegen geklappt hat (inkl. Validierung)
        boolean created = expensesService.addExpenseList(listName);

        if (created) {
            // Falls erfolgreich: Fenster für neue Liste öffnen
            new ExpenseManager(listName);
            // (Optional: UI-Feedback, z. B. Textfeld leeren oder Erfolgsmeldung)
            ExpenseListTextField.clear();
        } else {
            // Falls nicht erfolgreich: Fehler anzeigen
            // (z. B. weil Name leer oder schon vergeben)
            // → Optional: messageLabel.setText("Name ungültig oder bereits vergeben!");
            System.out.println("Fehler: Name ungültig oder Liste existiert bereits!");
        }
    }
}

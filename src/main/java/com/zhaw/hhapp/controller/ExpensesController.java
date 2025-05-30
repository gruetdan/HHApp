package com.zhaw.hhapp.controller;

import com.zhaw.hhapp.manager.ExpenseManager;
import com.zhaw.hhapp.service.ExpensesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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

    @FXML
    private ListView<String> ExpensesListView;

    // Instanz der Service-Klasse für Zugang zum Funktionen-Pool
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

    @FXML
    public void initialize() {
        expensesService.createExpenseListsFolder();
        // Event hinzufügen, um sicherzustellen, dass die Szene existiert
        ExpenseListTextField.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                expensesService.updateListView(expensesService.listTxtFiles(), ExpensesListView, AddExpenseListButton);
            }
        });
    }

}
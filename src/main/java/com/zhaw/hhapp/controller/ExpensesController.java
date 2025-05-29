package com.zhaw.hhapp.controller;

import com.zhaw.hhapp.manager.ExpenseManager;
import com.zhaw.hhapp.model.Expense;
import com.zhaw.hhapp.model.ExpenseList;
import com.zhaw.hhapp.service.ExpensesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Controller für die Verwaltung der Ausgabenlisten (UI-Eingaben).
 * Gibt Aufgaben an den Service weiter und kümmert sich um die Benutzeroberfläche.
 */
public class ExpensesController {
    /**
     * Nimmt Werte entgegen und gibt an Manager weiter
     */

    ExpensesService expensesService = new ExpensesService();

    @FXML
    private Button AddExpenseListButton;

    @FXML
    private TextField ExpenseListTextField;

    @FXML
    private ListView<String> ExpensesListView;

    // Instanz der Service-Klasse für die Logik
    private ExpensesService expensesService2 = new ExpensesService();

    @FXML
    void addExpenseList(ActionEvent event) {
        String listName = ExpenseListTextField.getText();

        // Service fragt, ob Anlegen geklappt hat (inkl. Validierung)
        boolean created = expensesService2.addExpenseList(listName);

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
        // Event hinzufügen, um sicherzustellen, dass die Szene existiert
        ExpenseListTextField.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                updateExpensesListView(expensesService2.listTxtFiles(), ExpensesListView);
            }
        });
    }

    //todo: Funktion auslegern zusammen mit gleicher Funktion in ExpenseController
    //Liste im Start-Fenster mit bestehenden Listen befüllen
    private void updateExpensesListView(ArrayList<String> expensesList, ListView<String> expensesListView) {
        expensesListView.getItems().clear();
        Stage stage = (Stage) AddExpenseListButton.getScene().getWindow();
        //String title = stage.getTitle();
        for (String expenseListName : expensesList) {
            expensesListView.getItems().add(expenseListName.toString());
        }
    }
}

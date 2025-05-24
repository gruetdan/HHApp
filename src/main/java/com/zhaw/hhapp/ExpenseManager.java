package com.zhaw.hhapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExpenseManager {
    /**
     * Speichert einzelne Ausgaben in einer Liste
     * @param expenseList Enthält unterschiedliche Ausgaben
     */

    private ExpenseList expenseList;
    private String expenseListId;
    private Expense expense;
    FXMLLoader fxmlLoader;

    public ExpenseManager(String expenseListId) {
        this.expenseList = new ExpenseList(); //sollte extern kreirt werden, Übersicht über Listen
        ExpensesManager.addExpenseList(expenseListId);
        fxmlLoader = new FXMLLoader(Main.class.getResource("/com/zhaw/hhapp/ExpenseView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        }catch (IOException e) {
                e.printStackTrace();
                System.out.println("Fehler beim Laden der FXML-Datei!");
            }
        Stage stage = new Stage();
        stage.setTitle(expenseListId);
        stage.setScene(scene);
        stage.show();
        System.out.println("show Scene hat geklappt");

        Platform.runLater(() -> getUserInput());
    }

    public void getUserInput(){
        // Controller aus FXMLLoader holen
        System.out.println("getUserInput startet");
        ExpenseController controller = fxmlLoader.getController();
        if (controller != null) {
            expense = controller.addExpense();
            System.out.println(expense.toString());
        } else {
            System.out.println("Fehler: Controller konnte nicht geladen werden!");
        }
    }

    public ExpenseManager(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    public ExpenseList getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }


}

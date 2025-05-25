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
     * Sets up the second level scene (Scene for particular ExpenseList)
     * @param expenseListId ist key/name of particular ExpenseList
     */
    private ExpenseList expenseList;
    private String expenseListId;
    private Expense expense;
    FXMLLoader fxmlLoader;

    public ExpenseManager(String expenseListId) {

        fxmlLoader = new FXMLLoader(Main.class.getResource("/com/zhaw/hhapp/ExpenseView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        }catch (IOException e) {
                e.printStackTrace();
                System.out.println("Fehler beim Laden der FXML-Datei!");
            }
        Stage stage = new Stage();
        stage.setTitle(expenseListId);
        stage.setScene(scene);
        stage.show();

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

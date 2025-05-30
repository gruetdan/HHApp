package com.zhaw.hhapp.manager;

import com.zhaw.hhapp.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExpenseManager {

    FXMLLoader fxmlLoader;
    /**
     * Sets up the second level scene (Scene for particular ExpenseList)
     *
     * @param expenseListId ist key/name of particular ExpenseList
     */
    public ExpenseManager(String expenseListId) {

        fxmlLoader = new FXMLLoader(Main.class.getResource("/com/zhaw/hhapp/ExpenseView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fehler beim Laden der FXML-Datei!");
        }
        Stage stage = new Stage();
        stage.setTitle(expenseListId);
        stage.setScene(scene);
        stage.show();

    }

}

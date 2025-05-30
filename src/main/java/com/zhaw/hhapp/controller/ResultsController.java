package com.zhaw.hhapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultsController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label sumLabel;

    public void setData(String listName, double sum) {
        titleLabel.setText("Summe der Ausgaben in \"" + listName + "\"");
        sumLabel.setText(String.format("%.2f CHF", sum));
    }
}

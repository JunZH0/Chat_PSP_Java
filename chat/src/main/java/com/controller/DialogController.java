package com.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DialogController {

    @FXML
    private TextField nameField;

    @FXML
    private Button okButton;

    private String userName;

    public String getUserName() {
        return userName;
    }

    @FXML
    private void handleOkButton() {
        userName = nameField.getText();
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}

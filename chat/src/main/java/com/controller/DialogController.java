package com.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DialogController {

    @FXML
    private TextField nameField;

    @FXML
    private Button connectButton;

    private Stage dialogStage;
    private String userName;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public String getUserName() {
        return userName;
    }

    @FXML
    private void handleConnect() {
        String name = nameField.getText();
        if (name != null && !name.trim().isEmpty()) {
            userName = name;
            dialogStage.close();
        }
    }

}

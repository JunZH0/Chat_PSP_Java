package com.controller;


import com.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

    private User user;

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @FXML
    private Button closeButton;

    public void setUser(User user) {
        this.user = user;
    }

    public void initialize() {
        // Initialize chat UI
        chatArea.appendText("Welcome to the chat!\n");
        messageField.requestFocus();
    }

    @FXML
    private void handleSendButton() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            user.sendMessage(message);
            messageField.clear();
        }
    }

    @FXML
    private void handleCloseButton() {
        user.close();
        closeButton.getScene().getWindow().hide();
    }
}

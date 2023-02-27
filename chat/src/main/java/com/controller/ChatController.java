package com.controller;


import com.user.ThreadUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.PrintWriter;
import java.net.Socket;

public class ChatController {
    @FXML
    private TextArea chatArea;

    @FXML
    private TextField inputField;

    @FXML
    private Button sendButton;

    @FXML
    private Button closeButton;

    private PrintWriter out;
    private String username;

    public void init(Socket socket, String username) {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            this.username = username;

            // Start a new thread to receive messages from the server
            ThreadUser threadUser = new ThreadUser(socket);
            new Thread(threadUser).start();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    @FXML
    void onSendButtonClicked() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            // Check if the message is a whisper by looking for the syntax "/w recipient message"
            if (message.startsWith("/w ")) {
                int spaceIndex = message.indexOf(' ', 3);
                if (spaceIndex != -1) {
                    String recipient = message.substring(3, spaceIndex);
                    String whisperMessage = message.substring(spaceIndex + 1);
                    out.println("(whisper to " + recipient + ") " + username + " : " + whisperMessage);
                } else {
                    chatArea.appendText("Invalid whisper syntax. Usage: /w recipient message\n");
                }
            } else {
                out.println(username + " : " + message);
            }
            inputField.clear();
        }
    }

    @FXML
    void onCloseButtonClicked() {
        out.println("cerrar sesión");
        sendButton.setDisable(true);
        closeButton.setDisable(true);
        inputField.setDisable(true);
    }

    public void appendMessage(String message) {
        chatArea.appendText(message + "\n");
    }
}

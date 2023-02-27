package com.main;

import com.controller.ChatController;
import com.controller.DialogController;
import com.user.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load dialog FXML file
        FXMLLoader dialogLoader = new FXMLLoader(getClass().getResource("Dialog.fxml"));
        Parent dialogRoot = dialogLoader.load();
        DialogController dialogController = dialogLoader.getController();

        // Create dialog stage
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(dialogRoot));
        dialogStage.showAndWait();

        // Retrieve user name from dialog controller
        String userName = dialogController.getUserName();

        // Load chat window FXML file
        FXMLLoader chatLoader = new FXMLLoader(getClass().getResource("Chat.fxml"));
        Parent chatRoot = chatLoader.load();
        ChatController chatController = chatLoader.getController();

        // Create chat stage
        Stage chatStage = new Stage();
        chatStage.setScene(new Scene(chatRoot));
        chatStage.show();

        // Pass user name to User class
        User user = new User(userName);

        // Set chat controller and initialize chat
        chatController.setUser(user);
        chatController.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.main;
import com.user.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("Dialog.fxml"));

        // Get references to the name field and connect button
        TextField nameField = (TextField) root.lookup("#nameField");
        Button connectButton = (Button) root.lookup("#connectButton");

        // Set the action for the connect button
        connectButton.setOnAction(event -> {
            // Get the user's name from the name field
            String name = nameField.getText();

            // Pass the user's name to the User class
            User.main(new String[]{name});

            // Close the UI
            primaryStage.close();
        });

        // Create the scene and set the root node
        Scene scene = new Scene(root);

        // Set the scene on the stage and show it
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

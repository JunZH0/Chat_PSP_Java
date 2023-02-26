import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class UserGUI extends Application {
    private TextField nameField;
    private TextArea chatArea;
    private TextField messageField;
    private Button sendButton;
    private Button logoutButton;

    private String username;
    private Socket socket;
    private PrintWriter out;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Label nameLabel = new Label("Introduce tu nombre:");
        nameField = new TextField();
        chatArea = new TextArea();
        chatArea.setEditable(false);
        messageField = new TextField();
        sendButton = new Button("Enviar");
        logoutButton = new Button("Cerrar sesión");

        VBox northBox = new VBox();
        northBox.getChildren().addAll(nameLabel, nameField);
        root.setTop(northBox);

        root.setCenter(chatArea);

        VBox southBox = new VBox();
        southBox.getChildren().addAll(messageField, sendButton, logoutButton);
        root.setBottom(southBox);

        sendButton.setOnAction(event -> sendMessage());

        messageField.setOnAction(event -> sendMessage());

        logoutButton.setOnAction(event -> logout());

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat App");
        primaryStage.show();
    }

    private void sendMessage() {
        String message = messageField.getText();
        messageField.setText("");
        if (message.equals("cerrar sesión")) {
            logout();
        } else {
            out.println(username + ": " + message);
        }
    }

    private void logout() {
        try {
            out.println("cerrar sesión");
            socket.close();
            Platform.exit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            String serverAddress = "localhost";
            int serverPort = 5000;
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            username = nameField.getText();

            ThreadUser threadUser = new ThreadUser(socket, chatArea);
            new Thread(threadUser).start();

            out.println(username + ": se ha conectado al chat");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class ThreadUser implements Runnable {
    private Socket socket;
    private TextArea chatArea;

    public ThreadUser(Socket socket, TextArea chatArea) {
        this.socket = socket;
        this.chatArea = chatArea;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }
                Platform.runLater(() -> chatArea.appendText(message + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}